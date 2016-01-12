package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.libweatherapi.libopenweatherapi.data.OpenWeather;

import retrofit2.*;
import retrofit2.http.*;

/**
 *
 */
public interface IOpenWeatherMapAPIService {

    @GET("data/2.5/weather")
    Call<OpenWeather> getWeatherByZipCode(@Query("zip") int zipCode, @Query("appid") String api, @Query("units") String units);

    @GET("data/2.5/weather")
    Call<OpenWeather> getWeatherByCity(@Query("id") int cityId, @Query("appid") String api, @Query("units") String units);
}
