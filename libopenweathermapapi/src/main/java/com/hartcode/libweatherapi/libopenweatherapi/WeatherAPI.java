package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.libweatherapi.libopenweatherapi.data.OpenWeather;

import org.apache.logging.log4j.*;
import java.io.*;
import retrofit2.*;
import retrofit2.http.*;



public class WeatherAPI implements IWeatherAPI {

    private static final Logger logger = LogManager.getLogger(WeatherAPI.class);
    private static final String url = "http://api.openweathermap.org";
    String apiKey = null;
    IOpenWeatherMapAPIService weatherMapAPIService = null;
    String units;

    public WeatherAPI(String apiKey, Unit unit)
    {
        if (apiKey == null)
        {
            throw new IllegalArgumentException("apiKey cannot be null.");
        }
        if (unit == null)
        {
            throw new IllegalArgumentException("unit cannot be null.");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.weatherMapAPIService = retrofit.create(IOpenWeatherMapAPIService.class);
        this.apiKey = apiKey;
        switch(unit)
        {
            case Celcius:
                this.units = "metric";
                break;
            case Fahrenheit:
            default:
                this.units = "imperial";
                break;
        }
    }



    @Override
    public Weather getWeatherByCity(int cityId) {
        Weather retval = null;
        try {
            Call<OpenWeather> openWeatherCall =
                weatherMapAPIService.getWeatherByCity(cityId, this.apiKey, this.units);
            OpenWeather ow = openWeatherCall.execute().body();
            if (ow.weather != null && ow.weather.size() > 0)
            {
                retval = new Weather(ow.weather.get(0).id,ow.weather.get(0).main, ow.weather.get(0).description, ow.weather.get(0).icon, ow.main.temp,ow.main.pressure, ow.main.humidity, ow.main.temp_min, ow.main.temp_max);
            }
            logger.debug(ow);
        } catch (IOException e) {
            logger.error("Error in getWeatherByCity.",e);
        }
        return retval;
    }

    @Override
    @GET("weather")
    public Weather getWeatherByZipCode(int zipCode) {
        Weather retval = null;
        try {
            Call<OpenWeather> openWeatherCall =
                    weatherMapAPIService.getWeatherByZipCode(zipCode, this.apiKey, this.units);
            OpenWeather ow = openWeatherCall.execute().body();
            if (ow.weather != null && ow.weather.size() > 0)
            {
                retval = new Weather(ow.weather.get(0).id,ow.weather.get(0).main, ow.weather.get(0).description, ow.weather.get(0).icon, ow.main.temp,ow.main.pressure, ow.main.humidity, ow.main.temp_min, ow.main.temp_max);
            }
            logger.debug(ow);
        } catch (IOException e) {
            logger.error("Error in getWeatherByZipCode.", e);
        }
        return retval;
    }
}
