package com.hartcode.hartweather.data.record;
import com.hartcode.hartweather.libweatherapi.*;
import com.orm.*;
import com.orm.dsl.Unique;

/**
 *
 */

public class WeatherRecord extends SugarRecord {
    @Unique
    public long id;
    public int cityId;
    public float lat;
    public float lon;
    public String cityName;
    public String main;
    public String description;
    public String icon;
    public float temp;
    public float pressure;
    public float humidity;
    public float temp_min;
    public float temp_max;
    public long lastUpdate;

    public WeatherRecord()
    {

    }

    public WeatherRecord(long id, int cityId, float lat, float lon, String cityName, String main, String description, String icon, float temp, float pressure, float humidity, float temp_min, float temp_max, long lastUpdate)
    {
        this.id = id;
        this.cityId = cityId;
        this.lat = lat;
        this.lon = lon;
        this.cityName = cityName;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.lastUpdate = lastUpdate;
    }

    public WeatherRecord(Weather weather)
    {
        this.updateFromWeather(weather);
    }

    public void updateFromWeather(Weather weather)
    {
        this.id = weather.id;
        this.cityId = weather.cityId;
        this.lat = weather.lat;
        this.lon = weather.lon;
        this.cityName = weather.cityName;
        this.main = weather.main;
        this.description = weather.description;
        this.icon = weather.icon;
        this.temp = weather.temp;
        this.pressure = weather.pressure;
        this.humidity = weather.humidity;
        this.temp_max = weather.temp_max;
        this.temp_min = weather.temp_min;
        this.lastUpdate = weather.lastUpdate;
    }

    public void updateFromWeatherRecord(WeatherRecord weather)
    {
        this.id = weather.id;
        this.cityId = weather.cityId;
        this.lat = weather.lat;
        this.lon = weather.lon;
        this.cityName = weather.cityName;
        this.main = weather.main;
        this.description = weather.description;
        this.icon = weather.icon;
        this.temp = weather.temp;
        this.pressure = weather.pressure;
        this.humidity = weather.humidity;
        this.temp_max = weather.temp_max;
        this.temp_min = weather.temp_min;
        this.lastUpdate = weather.lastUpdate;
    }

    public Weather toWeather()
    {
        return new Weather(this.id, this.cityId, this.lat, this.lon, this.cityName,this.main,this.description,this.icon,this.temp, this.pressure,this.humidity, this.temp_min, this.temp_max, this.lastUpdate);
    }



}
