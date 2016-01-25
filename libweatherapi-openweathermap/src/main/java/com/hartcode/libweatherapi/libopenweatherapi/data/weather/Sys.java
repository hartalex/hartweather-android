/**
*
*    HartWeather - A Simple Weather Android App
*    Copyright (C) 2016  Alex Hart
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see 
*    <https://github.com/hartcode/hartweather-android/blob/master/LICENSE>.
*
*/

package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Sys
{
    public final int type;
    public final int id;
    public final double message;
    public final String country;
    public final long sunrise;
    public final long sunset;

    public Sys(int type, int id, double message, String country,
               long sunrise, long sunset)
    {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    @Override
    public String toString()
    {
        return "type:" + type + ", id:" + id + ", message:" + message + ", country:" + country + ", sunrise:" + sunrise + ", sunset:" + sunset;
    }
}
