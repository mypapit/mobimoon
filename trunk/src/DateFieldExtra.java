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
 * DateFieldExtra.java 
 * DateField Item class with extra feature :)
 */
 
 

import javax.microedition.lcdui.DateField;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

public class DateFieldExtra extends DateField
{
	private Calendar calendar;
	private HijriConvert hc;
	private int d,m,y;
	private int phase;
	
	public DateFieldExtra(String label, int mode){
		super(label, mode);
		this.setDate(new Date());
	}
	public DateFieldExtra(String label, int mode, TimeZone tz){
		super(label,mode,tz);
		this.setDate(new Date());
	}


	public void computeValue()
	{	
		
		calendar = Calendar.getInstance();
		calendar.setTime(this.getDate());
		
		d = calendar.get(calendar.DATE);
		m = calendar.get(calendar.MONTH)+1;
		y = calendar.get(calendar.YEAR);
		
		this.moonPhase(y,m-1,d);
		
		
		hc = new HijriConvert(d,m,y);
		
		
	}
	

	public int getDay()
	{
		return calendar.get(calendar.DAY_OF_WEEK);
	}

	public int getGdate()
	{
		return d;
	}
	
	public int getGmonth()
	{
		return m;
	}

	public int getGyear()
	{
		return y;
	}
	
	public int getHdate()
	{
		return hc.getDay();
	}

	public int getHmonth()
	{
		return hc.getMonth();
	}

	public int getHyear()
	{
		return hc.getYear();
	}

	private void moonPhase(int y, int m, int d)
	{
		/*
		  calculates the moon phase (0-7), accurate to 1 segment.
		  0 = > new moon.
		  4 => full moon.
		  */
	
		double c,e;
		double jd;
		int b;
	
		if (m < 3) {
			y--;
			m += 12;
		}
		++m;
		c =  365.25*y;
		e =  30.6*m;
		jd = c+e+d-694039.09;  /* jd is total days elapsed */
		jd /= 29.53;           /* divide by the moon cycle (29.53 days) */
		b = (int) jd;		   /* int(jd) -> b, take integer part of jd */
    	jd -= b;		   /* subtract integer part to leave fractional part of original jd */
    	b = (int) (jd*8 + 0.5);	   /* scale fraction from 0-8 and round by adding 0.5 */
    	b = b & 7;		   /* 0 and 8 are the same so turn 8 into 0 */
    	
		this.phase =b;
	}
	
	public int getPhase(){
		return this.phase;
	}

}
