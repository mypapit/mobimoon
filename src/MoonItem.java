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
 * MoonItem.java 
 * Display moon image subclassed from ImageItem
 */
 
import javax.microedition.lcdui.*;
import java.io.IOException;
import javax.microedition.lcdui.game.Sprite;
public class MoonItem extends ImageItem 
{
	private String imgMoon[] = {"/b1.png","/b2.png","/b3.png","/b4.png",
								"/b5.png","/b6.png","/b7.png","/b8.png"};
	
	/*private String strPhase[] = {"Bulan Baru",
								"Mengambang Suku",
								"Mengambang Separuh",
								"Mengambang",
								"Bulan Penuh",
								"Menurun",
								"Menurun Separuh",
								"Menurun Suku"};
	*/
	
	
	private String strPhase[] = {"New Moon",
								"Waxing Cresent",
								"First Quarter",
								"Waxing Gibbous",
								"Full Moon",
								"Waning Gibbous",
								"Third Quarter",
								"Waning Cresent"};
								
	private boolean isNorthern = false;
								
							


	public MoonItem(String label, int layout, String altText) 
	{
		super(label, null, layout, altText);
		
	}

	public MoonItem() 
	{
		super(null, null, Item.LAYOUT_CENTER, null);
		
	}
	
	public void setNorthern(boolean isNorthern)
	{
		this.isNorthern = isNorthern;
	}
	
	public void setPhase(int phase)
	{
		try {
				if (isNorthern) {
					Image img = Image.createImage(imgMoon[phase]);
					this.setImage(Image.createImage(img,0,0,img.getWidth(),img.getHeight(),Sprite.TRANS_ROT180));
					
				} else {
				this.setImage(Image.createImage(imgMoon[phase]));
				}
		} catch (IOException ioe) {
			this.setImage(null);
		}
		
		this.setLabel(strPhase[phase]);
	}
	
	

}
