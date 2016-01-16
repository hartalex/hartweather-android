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
