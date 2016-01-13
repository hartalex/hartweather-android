package com.hartcode.hartweather.libweatherapi;

import java.lang.*;

/**
 *
 */
public class Weather {
    public long id;
    public int cityId;
    public float lat;
    public float lon;
    public String cityName;
    public String main;
    public String description;
    public String icon;
    public float temp;
    public int pressure;
    public int humidity;
    public float temp_min;
    public float temp_max;

    public Weather()
    {

    }

    public Weather(long id, int cityId, float lat, float lon, String cityName, String main, String description, String icon, float temp, int pressure, int humidity, float temp_min, float temp_max)
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
    }

    @Override
    public String toString()
    {
        return "id: " + id + ", cityId:" + cityId + ", lat:" + lat +", lon:" + lon +", cityName:" + cityName  + ", main:" + main + ", description:" + description + ", icon:" + icon + ", temp:" + temp + ", pressure:" + pressure + ", humidity:" + humidity + ", temp_min:" + temp_min + ", temp_max:" + temp_max;

    }


}
