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

package com.hartcode.hartweather.data;

import android.os.*;
import android.support.annotation.*;
import java.util.*;

/**
 *
 */
public class WeatherDataChangeHandler extends Handler {

    private final List<IWeatherChangeDataListener> weatherChangeDataListeners;

    public WeatherDataChangeHandler(@NonNull List<IWeatherChangeDataListener> weatherChangeDataListeners)
    {
        super();
        this.weatherChangeDataListeners = weatherChangeDataListeners;
    }

    public void handleMessage(@NonNull Message msg) {
        for (IWeatherChangeDataListener weatherChangeDataListener : this.weatherChangeDataListeners)
        {
            weatherChangeDataListener.weatherDataChange();
        }
    }

}
