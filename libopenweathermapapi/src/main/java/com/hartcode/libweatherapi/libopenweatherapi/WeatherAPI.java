package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;

import java.io.IOException;
import java.util.List;

import retrofit2.*;
import retrofit2.http.*;



public class WeatherAPI implements IWeatherAPI {

    String apiKey = null;
    IOpenWeatherMapAPIService weatherMapAPIService;

    public WeatherAPI(String apiKey)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.weatherMapAPIService = retrofit.create(IOpenWeatherMapAPIService.class);
        this.apiKey = apiKey;
    }



    @Override
    public Weather getWeatherByCity(int cityId) {
        Weather retval = null;
        Call<OpenWeather> openWeatherCall = null;
        openWeatherCall = weatherMapAPIService.getWeatherByCity(cityId, apiKey);
        try {
            OpenWeather ow = openWeatherCall.execute().body();
            retval = new Weather(0,ow.name,"","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retval;
    }

    @Override
    @GET("weather")
    public Weather getWeatherByZipCode(int zipCode) {
        Weather retval = null;
        Call<OpenWeather> openWeatherCall = null;
        openWeatherCall = weatherMapAPIService.getWeatherByZipCode(zipCode, apiKey);
        try {
            OpenWeather ow = openWeatherCall.execute().body();
            retval = new Weather(0,ow.name,"","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retval;
    }
}
