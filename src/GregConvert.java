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
 * MobiMoon 2.0 <info@mypapit.net>
 * Copyright 2007 Mohammad Hafiz bin Ismail. All rights reserved.
 * 
 * GregConvert.java 
 * Convert Hijri date to Gregorian
 */
 
public class GregConvert {
	private int day,month,year;
	
	private String strMonth[] = {
					"NIL",
					"Jan","Feb","Mac","Apr","May","Jun",
					"Jul","Aug","Sep","Oct","Nov","Dec"};
	
	public GregConvert(int date,int month, int year) {
		double jd,l,n,i,k,j;
		int d,m,y;
		d = date;
		m = month;
		y = year;
		
		jd = (int)((11*y+3)/30)+354*y+30*m- (int)((m-1)/2)+d+1948440-385;
		if (jd> 2299160 ) {
			l=jd+68569;
			n=(int)((4*l)/146097);
			l=l-(int) ((146097*n+3)/4);
			i= (int)((4000*(l+1))/1461001);
			l=l-(int)((1461*i)/4)+31;
			j=(int) ((80*l)/2447);
			d=(int) l-(int)((2447*j)/80);
			l=(int)(j/11);
			m=(int) (j+2-12*l);
			y=(int) (100*(n-49)+i+l);
			
		
		} else {
			j=jd+1402;
			k=(int)((j-1)/1461);
			l=j-1461*k;
			n=(int)((l-1)/365)-(int) (l/1461);
			i=l-365*n+30;
			j=(int)((80*i)/2447);
			d=(int) i-(int)((2447*j)/80);
			i=(int)(j/11);
			m=(int) (j+2-12*i);
			y=(int) (4*k+n+i-4716);
		}

	
		
		this.day = d;
		this.month = m;
		this.year = y;
		
	
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
	
	public String toString(){
		return new String(day +" " + strMonth[month] + " " + year);
	
	}
	

}
