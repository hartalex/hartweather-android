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

import java.lang.*;
/**
 *
 */
public class NetworkRequest
{
    public final static int DEFAULT_CITY_ID = -1;
    public final int cityId;
    public final String cityName;

    public NetworkRequest(int cityId)
    {
        this.cityId = cityId;
        this.cityName = null;
    }

    public NetworkRequest(String cityName)
    {
        this.cityId = DEFAULT_CITY_ID;
        this.cityName = cityName;
    }

    @Override public boolean equals(Object obj) {
        boolean retval = false;
        if ( this == obj )
        {
            retval = true;
        } else {
            if ((obj instanceof NetworkRequest)) {
                NetworkRequest rhs = (NetworkRequest) obj;
                retval = (this.cityName == rhs.cityName ||
                        (this.cityName != null && this.cityName.equals(rhs.cityName))) &&
                        this.cityId == rhs.cityId;
            }
        }
        return retval;
    }

}
