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

package com.hartcode.hartweather.libhartweather.network;

import com.hartcode.hartweather.libweatherapi.*;

import java.util.*;

/**
 *
 */
public class NetworkResponse {
    public final List<Weather> weatherList;
    public final NetworkRequest networkRequest;
    public int error;

    public NetworkResponse(NetworkRequest networkRequest, Weather weather)
    {

        this.networkRequest = networkRequest;

        if (weather != null) {
            List<Weather> weatherList = new ArrayList<>();
            weatherList.add(weather);
            this.weatherList = weatherList;
            this.error = weather.error;
        }else
        {
            this.weatherList = null;
        }

    }

    public NetworkResponse(NetworkRequest networkRequest, List<Weather> weatherList)
    {
        this.networkRequest = networkRequest;
        this.weatherList = weatherList;
        if (this.weatherList != null && this.weatherList.size() > 0)
        {
            this.error = this.weatherList.get(0).error;
        }
    }
}
