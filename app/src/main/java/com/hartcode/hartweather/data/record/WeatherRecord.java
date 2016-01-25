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

package com.hartcode.hartweather.data.record;

import android.support.annotation.*;

import com.hartcode.hartweather.libweatherapi.*;
import com.orm.*;
import com.orm.dsl.*;

/**
 *
 */

public class WeatherRecord extends SugarRecord {
    @Unique
    public long id;
    public int cityId;
    public double lat;
    public double lon;
    public String cityName;
    public String main;
    public String description;
    public String icon;
    public double temp;
    public double pressure;
    public double humidity;
    public double temp_min;
    public double temp_max;
    public long lastUpdate;

    public WeatherRecord()
    {

    }

    public WeatherRecord(long id, int cityId, double lat, double lon, String cityName, String main, String description, String icon, double temp, double pressure, double humidity, double temp_min, double temp_max, long lastUpdate)
    {
        this.id = id;
        this.cityId = cityId;
        this.lat = lat;
        this.lon = lon;
        this.cityName = cityName;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.lastUpdate = lastUpdate;
    }

    public WeatherRecord(@NonNull Weather weather)
    {
        this.updateFromWeather(weather);
    }

    public void updateFromWeather(@NonNull Weather weather)
    {
        this.id = weather.id;
        this.cityId = weather.cityId;
        this.lat = weather.lat;
        this.lon = weather.lon;
        this.cityName = weather.cityName;
        this.main = weather.main;
        this.description = weather.description;
        this.icon = weather.icon;
        this.temp = weather.temp;
        this.pressure = weather.pressure;
        this.humidity = weather.humidity;
        this.temp_max = weather.temp_max;
        this.temp_min = weather.temp_min;
        this.lastUpdate = weather.lastUpdate;
    }

    public void updateFromWeatherRecord(@NonNull WeatherRecord weather)
    {
        this.id = weather.id;
        this.cityId = weather.cityId;
        this.lat = weather.lat;
        this.lon = weather.lon;
        this.cityName = weather.cityName;
        this.main = weather.main;
        this.description = weather.description;
        this.icon = weather.icon;
        this.temp = weather.temp;
        this.pressure = weather.pressure;
        this.humidity = weather.humidity;
        this.temp_max = weather.temp_max;
        this.temp_min = weather.temp_min;
        this.lastUpdate = weather.lastUpdate;
    }

    public Weather toWeather()
    {
        return new Weather(this.id, this.cityId, this.lat, this.lon, this.cityName,this.main,this.description,this.icon,this.temp, this.pressure,this.humidity, this.temp_min, this.temp_max, this.lastUpdate, 200);
    }



}
