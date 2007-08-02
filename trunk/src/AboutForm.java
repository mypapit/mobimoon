/*
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as published by
 *  the Free Software Foundation
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
 * AboutForm.java 
 * Practical, easy to use AboutForm.
 */
 
 


import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.io.IOException;
import javax.microedition.io.ConnectionNotFoundException;

public class AboutForm extends Form implements ItemCommandListener
{
	private ImageItem logo;
	private Command cmdBrowse;
	private StringItem hyperlink;
	private MIDlet midlet;
	
	public AboutForm (String title,String label)
	{
		super(title);
		logo = new ImageItem(label,null, Item.LAYOUT_CENTER | Item.LAYOUT_NEWLINE_AFTER, "logo");
		this.append(logo);
	}
	
	public AboutForm (String title, String label, Image image )
	{
		super(title);
		logo = new ImageItem(label,image,Item.LAYOUT_CENTER | Item.LAYOUT_NEWLINE_AFTER, "logo");
		
		this.append(logo);
			
	}
	
	public AboutForm (String title, String label, String resource )
	{
		super(title);
		Image img;
		
		try {
			img = Image.createImage(resource);
		} catch (IOException ioex) {
			img = null;
		}
		
		logo = new ImageItem(label,img,Item.LAYOUT_CENTER | Item.LAYOUT_NEWLINE_AFTER, "logo");
		
		this.append(logo);
		
	}
	
	public void setLayout(int layout){
		logo.setLayout(layout);
	}
	
	public void setCopyright(String owner, String year)
	{
		StringItem notice = new StringItem(null,"Copr. " + year +" " +  owner + ".\n\n", StringItem.PLAIN);
		notice.setLayout(Item.LAYOUT_CENTER | Item.LAYOUT_NEWLINE_AFTER);
		this.append( notice);
		
	}
	
	public void setHyperlink(String link, MIDlet midlet)
	{
	  hyperlink = new StringItem(null, link, Item.HYPERLINK);
	  this.append (hyperlink); 
	 hyperlink.setLayout(Item.LAYOUT_CENTER | Item.LAYOUT_NEWLINE_AFTER);
	 cmdBrowse = new Command("Browse",Command.SCREEN,10);
	 hyperlink.setDefaultCommand(cmdBrowse);
	 hyperlink.setItemCommandListener(this);
	 this.midlet = midlet;
	}
	
	public void commandAction(Command c, Item i)
	{
		if (c == cmdBrowse) {
			try {
			 	midlet.platformRequest(hyperlink.getText());
			} catch (ConnectionNotFoundException cnf){ 
								
			} catch (Exception ex) {
			
			}
			
			
		
		}
	
	}

}
