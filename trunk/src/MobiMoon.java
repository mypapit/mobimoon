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
 * MobiMoon 1.0 <info@mypapit.net>
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
	private Display display;
	private Command cmdExit,cmdAbout,cmdBack,cmdReset;
	private Form form;
	private AboutForm aboutForm;
	private DateFieldExtra datefield;
	private MoonItem moonitem;
	private StringItem hijritext;
	private String day[] = {"NIL",
							"Sun",
							"Mon",
							"Tue",
							"Wed",
							"Thu",
							"Fri",
							"Sat",
							};
	/*
	private String month[] = {
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
							"R.al-awwal",
							"R.al-thani ",
							"J.al-awwal ",
							"J.al-thani ",
							"Rajab",
							"Shaaban",
							"Ramadan",
							"Shawwal",
							"D.al-Qi'dah",
							"D.al-Hijjah"
							
						};
							
	

	public MobiMoon()
	{
	cmdExit = new Command("Exit",Command.EXIT,2);
	cmdAbout = new Command("About",Command.HELP,4);
	cmdBack = new Command("Back",Command.BACK,3);
	cmdReset = new Command("Reset",Command.SCREEN,1);
	
	
	hijritext = new StringItem(null,"");
	
	datefield = new DateFieldExtra(null,DateFieldExtra.DATE);
	datefield.setLayout(datefield.LAYOUT_CENTER | datefield.LAYOUT_NEWLINE_AFTER);
	moonitem = new MoonItem();
	
	form = new Form("MobiMoon");
	form.addCommand(cmdExit);
	form.addCommand(cmdAbout);
	form.addCommand(cmdReset);
	form.setCommandListener(this);
	form.append(moonitem);
	form.append(datefield);
	form.append(hijritext);
	form.setItemStateListener(this);
	display = Display.getDisplay(this);
	this.updateDate();
		
	}
	
	public void startApp()
	{
		this.showMain();
		
	}
	
	private void showMain(){
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
		
			aboutForm = new AboutForm("About","MobiMoon 1.0","/b5.png");
			aboutForm.addCommand(cmdBack);
			aboutForm.setHyperlink("http://java.mobilepit.com", (MIDlet) this);
			aboutForm.setCopyright("Mohammad Hafiz","2007");
			aboutForm.setCommandListener(this);
			aboutForm.append(
					"Display Moon Phases and Hijri date. The displayed date might subject to +/- 1 day error margin.\n\nThis software is licensed under the GNU General Public License version 2.");
					
 		 //This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version"
					 
			display.setCurrent(aboutForm);
		} else if ( c == cmdBack) {
			aboutForm = null;
			this.showMain();
		} else if ( c == cmdReset) {
			datefield.setDate(new Date());
			this.updateDate();
		}	
	
	}
	
	public void itemStateChanged(Item item)
	{
		if (item == datefield)
		{
			updateDate();
		}
	}
	
	private void updateDate()
	{
	
		datefield.computeValue();
		moonitem.setPhase(datefield.getPhase());
		hijritext.setText(day[datefield.getDay()] + ", " + datefield.getHdate() + " " + month[datefield.getHmonth()] + " " + datefield.getHyear());
		//hijritext.setText(datefield.getDay() + ", " + datefield.getHdate() + " " + datefield.getHmonth() + " " + datefield.getHyear());
		
		//form.append ("Day of the month " + datefield.getGdate());		
		
	}

}
