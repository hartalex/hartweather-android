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

import java.util.*;

/**
 *
 */
public class OpenWeather {
    public final Coord coord;
    public final List<Weather> weather;
    public final Main main;
    public final Wind wind;
    public final Clouds clouds;
    public final Rain rain;
    public final Snow snow;
    public final long dt;
    public final Sys sys;
    public final int id;
    public final String name;
    public final int cod;


    public OpenWeather(Coord coord, List<Weather> weather, Main main, Wind wind,
                       Clouds clouds, Rain rain, Snow snow, long dt,
                       Sys sys, int id, String name, int cod)
    {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.snow = snow;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;

    }

    @Override
    public String toString()
    {
        return "coord:" + coord + ", weather:" + weather + ", main:" + main + ", wind:" + wind + ", clouds:" + clouds + ", rain:" + rain + ", snow:" + snow + ", dt:" + dt + ", sys:" + sys + ", id:" + id + ", name:" + name + ", cod:" + cod;
    }
}
