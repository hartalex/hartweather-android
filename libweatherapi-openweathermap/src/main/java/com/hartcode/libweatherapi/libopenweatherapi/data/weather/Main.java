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
public class Main
{
    public final double temp;
    public final double pressure;
    public final double humidity;
    public final double temp_min;
    public final double temp_max;
    public final double sea_level;
    public final double grnd_level;

    public Main(double temp, double pressure, double humidity,
                double temp_min, double temp_max, double sea_level,
                double grnd_level)
    {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
    }
    @Override
    public String toString()
    {
        return "temp:" + temp + ", pressure:" + pressure + ", humidity:" + humidity + ", temp_min:" + temp_min + ", temp_max:" + temp_max + ", sea_level:" + sea_level + ", grnd_level:" + grnd_level;
    }
}
