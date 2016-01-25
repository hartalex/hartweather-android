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

package com.hartcode.hartweather.libweatherapi;

import java.io.*;
import java.util.*;

/**
 *
 * This interface will be the mobile apps window into the weather api.
 * This way we can swap in and out other API's as necessary.
 */
public interface IWeatherAPI {
    Weather getWeatherByCity(int cityId) throws IOException;
    Weather getWeatherByLatLon(double lat, double lon) throws IOException;
    List<Weather> findCityByNameOrZip(String question) throws IOException, IllegalArgumentException;
    void setUnits(Unit unit);
}
