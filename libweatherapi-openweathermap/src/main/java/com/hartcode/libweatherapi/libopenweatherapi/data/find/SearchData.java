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

package com.hartcode.libweatherapi.libopenweatherapi.data.find;

import com.hartcode.libweatherapi.libopenweatherapi.data.weather.*;
import java.util.*;

/**
 *
 */
public class SearchData
{
    public final String message;
    public final int cod;
    public final int count;
    public final List<OpenWeather> list;

    public SearchData(String message, int cod, int count, List<OpenWeather> list) {
        this.message = message;
        this.cod = cod;
        this.count = count;
        this.list = list;
    }

    @Override
    public String toString()
    {
        return "message:" + message + ", cod:" + cod + ", count:" + count+ ", list:" + list;
    }

}
