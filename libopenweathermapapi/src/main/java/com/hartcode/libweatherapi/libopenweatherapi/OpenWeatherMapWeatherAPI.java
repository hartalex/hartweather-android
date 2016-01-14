package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.libweatherapi.libopenweatherapi.data.find.*;
import com.hartcode.libweatherapi.libopenweatherapi.data.weather.*;
import org.apache.logging.log4j.*;
import java.io.*;
import retrofit2.*;



public class OpenWeatherMapWeatherAPI implements IWeatherAPI {

    private static final Logger logger = LogManager.getLogger(OpenWeatherMapWeatherAPI.class);
    private static final String url = "http://api.openweathermap.org";
    String apiKey = null;
    IRetrofitOpenWeatherMapAPIService weatherMapAPIService = null;
    String units;

    public OpenWeatherMapWeatherAPI(String apiKey, Unit unit)
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
        this.weatherMapAPIService = retrofit.create(IRetrofitOpenWeatherMapAPIService.class);
        this.apiKey = apiKey;
        this.setUnits(unit);
    }

    public void setUnits(Unit unit)
    {
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
            if (ow != null && ow.weather != null && ow.weather.size() > 0)
            {
                retval = new Weather(0, ow.id, ow.coord.lat, ow.coord.lon, ow.name,ow.weather.get(0).main, ow.weather.get(0).description, ow.weather.get(0).icon, ow.main.temp,ow.main.pressure, ow.main.humidity, ow.main.temp_min, ow.main.temp_max, ow.dt);
            }
            logger.debug(ow);
        } catch (IOException e) {
            logger.error("Error in getWeatherByCity.",e);
        }
        return retval;
    }

    @Override
    public Weather getWeatherByLatLon(float lat, float lon) {
        Weather retval = null;
        try {
            Call<OpenWeather> openWeatherCall =
                    weatherMapAPIService.getWeatherByLatLon(lat, lon, this.apiKey, this.units);
            OpenWeather ow = openWeatherCall.execute().body();
            if (ow != null && ow.weather != null && ow.weather.size() > 0)
            {
                retval = new Weather(0, ow.id, ow.coord.lat, ow.coord.lon, ow.name,ow.weather.get(0).main, ow.weather.get(0).description, ow.weather.get(0).icon, ow.main.temp,ow.main.pressure, ow.main.humidity, ow.main.temp_min, ow.main.temp_max, ow.dt);
            }
            logger.debug(ow);
        } catch (IOException e) {
            logger.error("Error in getWeatherByZipCode.", e);
        }
        return retval;
    }

    @Override
    public Weather findCityByNameOrZip(String question) {
        Weather retval = null;
        try {
            Call<SearchData> openWeatherCall =
                    weatherMapAPIService.findCityByNameOrZip(question, this.apiKey, this.units, "like");
            SearchData search = openWeatherCall.execute().body();
            logger.debug(search);
            if (search != null && search.message != null && search.list != null && search.list.size() > 0)
            {
                OpenWeather ow = search.list.get(0);
                retval = new Weather(0, ow.id, ow.coord.lat, ow.coord.lon, ow.name,ow.weather.get(0).main, ow.weather.get(0).description, ow.weather.get(0).icon, ow.main.temp,ow.main.pressure, ow.main.humidity, ow.main.temp_min, ow.main.temp_max, ow.dt);
                logger.debug(retval);
            }

        } catch (IOException e) {
            logger.error("Error in findCityByNameOrZip.", e);
        }
        return retval;
    }
}
