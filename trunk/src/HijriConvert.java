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
 * HijriConvert.java 
 * For converting Gregorian to Hijri dates
 */
 
public class HijriConvert {
	private int day,month,year,phase;
	
	
	private int intPart(double value)
	{
		/*if (value < -0.001){
	 		return Math.ceil(value -0.001);
		}
		return Math.floor(value +0.001);
		*/
		return (int) value;

	}
	public HijriConvert(int d, int m, int y) {
	
		double l,n,jd,j;
		
	
		/*if ((y>1582)||((y==1582)&&(m>10))||((y==1582)&&(m==10)&&(d>14))) 
		{
			jd=intPart((1461*(y+4800+intPart((m-14)/12)))/4)+intPart((367*(m-2-12*(intPart((m-14)/12))))/12)-
			intPart( (3* (intPart(  (y+4900+    intPart( (m-14)/12)     )/100)    )   ) /4)+d-32075;
		} else	{
			jd = 367*y-intPart((7*(y+5001+intPart((m-9)/7)))/4)+intPart((275*m)/9)+d+1729777;
		}
		

		jd=(1461*(y+4800+(m-14)/12))/4+367*(m-2-12*(((m-14)/12)))/12-
		 (3* ( (y+4900+    (m-14)/12     )/100    )   ) /4+d-32075;
		*/
		
		jd = (1461 * (y + 4800 + (m - 14) / 12)) / 4 +
		(367 * (m - 2 - 12 * ((m - 14) / 12))) / 12 -
		(3 * ((y + 4900 + (m - 14) / 12) / 100 )) / 4 +
		d - 32075;
		//arg.JD.value=jd
		//arg.wd.value=weekDay(jd%7)
		/*l=jd-1948440+10632;
		n=intPart((l-1)/10631);
		l=l-10631*n+354;
		j=(intPart((10985-l)/5316))*(intPart((50*l)/17719))+(intPart(l/5670))*(intPart((43*l)/15238));
		l=l-(intPart((30-j)/15))*(intPart((17719*j)/50))-(intPart(j/16))*(intPart((15238*j)/43))+29;
		this.month=(int) intPart((24*l)/709);
		this.day=(int) (l- intPart((709*this.month)/24));
		this.year=(int) (30*n+j-30);
		*/
		
		l=jd-1948440+10632;
		n = (int) ((l- 1) / 10631);
		l = l- 10631 * n + 354;
		j = ((int) ((10985 - l) / 5316)) *
		((int) (50 * l/ 17719)) +
		((int) (l / 5670)) *
		((int) (43 * l / 15238));
		
		l = l- ((int) ((30 - j) / 15)) *
		((int) ((17719 * j) / 50)) -
		((int) (j / 16)) *
		((int) ((15238 * j) / 43)) + 29;
		this.month = (int) (24 * l / 709);
		this.day = (int) (   l - (int) (709 * this.month / 24)   );
		this.year = (int) (30 * n + j - 30);
		
	}
	
	
	
	
	
	public int getDay()
	{
		return day;
	}

	public int getMonth()
	{
		return month;
	}

	public int getYear()
	{
		return year;
	}
	
	public void calibrate(int value)
	{
		day = day + value;
		if (day > 30) {
			day = 1;
			month = month +1;
		} else if (day < 1) {
			day = 29;
			month = month -1;
		}
	}
	

}
