package com.hartcode.hartweather.libweatherapi;

import java.lang.*;

/**
 *
 */
public class Weather {
    public long id;
    public int cityId;
    public double lat;
    public double lon;
    public String cityName;
    public String main;
    public String description;
    public String icon;
    public double temp;
    public double pressure;
    public double humidity;
    public double temp_min;
    public double temp_max;
    public long lastUpdate;
    public int error;

    public Weather(int error)
    {
        this.error = error;
    }

    public Weather(long id, int cityId, double lat, double lon, String cityName, String main, String description, String icon, double temp, double pressure, double humidity, double temp_min, double temp_max, long lastUpdate, int error)
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
        this.error = error;
    }

    @Override
    public String toString()
    {
        return "id: " + id + ", cityId:" + cityId + ", lat:" + lat + ", lon:" + lon +", cityName:" + cityName  + ", main:" + main + ", description:" + description + ", icon:" + icon + ", temp:" + temp + ", pressure:" + pressure + ", humidity:" + humidity + ", temp_min:" + temp_min + ", temp_max:" + temp_max + ", lastUpdate: " + lastUpdate + ", error: " + error;
    }


}
