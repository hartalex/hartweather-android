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

package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.libweatherapi.libopenweatherapi.data.find.*;
import com.hartcode.libweatherapi.libopenweatherapi.data.weather.*;
import retrofit2.*;
import retrofit2.http.*;

/**
 *
 */
public interface IRetrofitOpenWeatherMapAPIService {

    @GET("data/2.5/weather")
    Call<OpenWeather> getWeatherByLatLon(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String api, @Query("units") String units);

    @GET("data/2.5/weather")
    Call<OpenWeather> getWeatherByCity(@Query("id") int cityId, @Query("appid") String api, @Query("units") String units);

    @GET("data/2.5/find")
    Call<SearchData> findCityByNameOrZip(@Query("q") String question, @Query("appid") String api, @Query("units") String units,@Query("type") String type);
}
