/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as 
 *  published by the Free Software Foundation
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * MobiMoon 2.0 <info@mypapit.net>
 * Copyright 2007 Mohammad Hafiz bin Ismail. All rights reserved.
 * 
 * MobiMoon.java 
 * This is the main MIDlet class file
 */
 

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
import java.util.Date;
import java.io.*;



public class MobiMoon extends MIDlet implements CommandListener,ItemStateListener
{
	public Display display;
	private Command cmdExit,cmdAbout,cmdBack,cmdReset, cmdImportant,cmdSettings;
	public Form form;
	private Form importantForm;
	private AboutForm aboutForm;
	public DateFieldExtra datefield;
	public MoonItem moonitem;
	//private SettingsForm settingsForm;
	private StringItem hijritext;
	private Settings rsettings;
	
	private String day[] = {"NIL",
							"Sun",
							"Mon",
							"Tue",
							"Wed",
							"Thu",
							"Fri",
							"Sat",
							};
	
/*  private String month[] = {
							"NIL",
							"Muharram",
							"Safar",
							"R.Awal",
							"R.Akhir",
							"J.Awal",
							"J.Akhir",
							"Rejab",
							"Shaaban",
							"Ramadan",
							"Syawal",
							"Z.Kaedah",
							"Z.Hijjah"
							
						};
	*/
						
								
	private String month[] = {
							"NIL",
							"Muharram",
							"Safar",
							"Rabi al-awwal",
							"Rabi al-thani ",
							"Jum. al-awwal ",
							"Jum. al-thani ",
							"Rajab",
							"Shaaban",
							"Ramadan",
							"Shawwal",
							"Dhu al-Qi'dah",
							"Dhu al-Hijjah"
							
						};
	

	public MobiMoon()
	{
	cmdExit = new Command("Exit",Command.EXIT,2);
	cmdAbout = new Command("About",Command.HELP,10);
	cmdBack = new Command("Back",Command.BACK,3);
	cmdReset = new Command("Reset",Command.SCREEN,1);
	cmdImportant = new Command("Important Dates",Command.SCREEN,4);
	cmdSettings = new Command("Settings",Command.SCREEN,5);
	
	
	hijritext = new StringItem(null,"");
	
	datefield = new DateFieldExtra(null,DateFieldExtra.DATE);
	datefield.setLayout(datefield.LAYOUT_CENTER | datefield.LAYOUT_NEWLINE_AFTER);
	moonitem = new MoonItem();
	
	form = new Form("MobiMoon");
	form.addCommand(cmdExit);
	form.addCommand(cmdAbout);
	form.addCommand(cmdReset);
	form.addCommand(cmdImportant);
	form.addCommand(cmdSettings);
	form.setCommandListener(this);
	form.append(moonitem);
	form.append(datefield);
	form.append(hijritext);
	form.setItemStateListener(this);
	display = Display.getDisplay(this);
	//settingsForm = new SettingsForm(this);
	rsettings = new Settings();
	
	//rsettings.getSettings();
	
	moonitem.setNorthern(rsettings.isNorthern());
	datefield.calibrate(rsettings.getDaysCalib());
	
	rsettings.cleanUp();
	rsettings = null;
		
	this.updateDate();
		
	}
	
	public void startApp()
	{
		display.setCurrent(form);
		
	}
	
	
	public void pauseApp()
	{
	
	}
	
	public void destroyApp(boolean flag)
	{
		notifyDestroyed();	
	}
	
	public void commandAction(Command c, Displayable d)
	{
		if (c == cmdExit) {
			destroyApp(true);
		} else if (c== cmdAbout) {
		
			aboutForm = new AboutForm("About","MobiMoon 2.3","/b5.png");
			aboutForm.addCommand(cmdBack);
			aboutForm.setHyperlink("http://java.mobilepit.com", (MIDlet) this);
			aboutForm.setCopyright("Mohammad Hafiz","2007");
			aboutForm.setCommandListener(this);
			aboutForm.append(
					"Display Moon Phases and Hijri date. The displayed date might subject to +/- 1 day error margin.\n\nThis program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License version 2.0");
					
 		 //This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version"
					 
			display.setCurrent(aboutForm);
		} else if ( c == cmdBack) {
			aboutForm = null;
			importantForm = null;
			display.setCurrent(form);
		} else if ( c == cmdReset) {
			datefield.setDate(new Date());
			this.updateDate();
		} else if ( c == cmdImportant) {
			this.listImportantDates();
		} else if ( c == cmdSettings) {
			//settingsForm = new SettingsForm(this);
			display.setCurrent(new SettingsForm(this));
		}
	
	}
	
	public void itemStateChanged(Item item)
	{
		if (item == datefield)
		{
			updateDate();
		}
	}
	
	public void updateDate()
	{
	
		
		datefield.computeValue();
		moonitem.setPhase(datefield.getPhase());
		//datefield.calibrate( rsettings.getDaysCalib() );
		hijritext.setText(day[datefield.getDay()] + ", " + datefield.getHdate() + " " + month[datefield.getHmonth()] + " " + datefield.getHyear());
		//hijritext.setText(datefield.getDay() + ", " + datefield.getHdate() + " " + datefield.getHmonth() + " " + datefield.getHyear());
/*
		int d = datefield.getHdate();
		int m = datefield.getHmonth();
		int y = datefield.getHyear();
		
		GregConvert g = new GregConvert(15,6,1428);
		
		String s = g.getDay() + "d " + g.getMonth() + "m " + g.getYear() + " ";
		form.append(s);
*/
		//form.append ("Day of the month " + datefield.getGdate());		
		
	}
	
	
	public void listImportantDates() 
	{
		GregConvert g;
		importantForm = new Form("Important date");
		importantForm.addCommand(cmdBack);
		importantForm.setCommandListener(this);
		
		StringItem si = new StringItem("label","text");
		
		
		int month = datefield.getHmonth();
		int	year = datefield.getHyear();
		
		for (int i=0;i<4;i++) {
				switch ((month+i)%13) {
				//1,2,3,7,8,9,12
					case 1:
						si = new StringItem(new GregConvert(1,1,year).toString(),"Awwal Muharram");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
						
						si = new StringItem(new GregConvert(10,1,year).toString(),"Day of Ashura");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
					case 2:
						si = new StringItem(new GregConvert(27,2,year).toString(),"Hijrah to Madinah");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
		
					case 3:
						si = new StringItem(new GregConvert(12,3,year).toString(),"Birth of Prophet Muhammad (PBUH)");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
					
					case 7:
						si = new StringItem(new GregConvert(27,7,year).toString(),"Israk Mikraj");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
					
					case 8:
						si = new StringItem(new GregConvert(15,8,year).toString(),"Nisfu Shabaan");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
		
					case 9:
						si = new StringItem(new GregConvert(1,9,year).toString(),"Awal Ramadan");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
						
						si = new StringItem(new GregConvert(21,9,year).toString(),"Nuzul Quran");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
						
						si = new StringItem(new GregConvert(27,9,year).toString(),"Lailatul Qadar");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
		
					case 10:
						si = new StringItem(new GregConvert(1,10,year).toString(),"Eidul-Fitri");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
		
					case 12:
						si = new StringItem(new GregConvert(10,12,year).toString(),"Eidul-Adha");
						si.setLayout(Item.LAYOUT_NEWLINE_AFTER);
						importantForm.append(si);
					break;
		
						
				}
		}
				
		g = null;
		display.setCurrent(importantForm);
		
	
	}
	

	

}
