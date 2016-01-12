package com.hartcode.hartweather.libweatherapi;

import java.lang.*;

/**
 *
 */
public class Weather {
    public final int id;
    public final String main;
    public final String description;
    public final String icon;
    public final float temp;
    public final int pressure;
    public final int humidity;
    public final float temp_min;
    public final float temp_max;

    public Weather(int id, String main, String description, String icon, float temp, int pressure, int humidity, float temp_min, float temp_max)
    {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = "icon" + icon;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
    }

    @Override
    public String toString()
    {
        return "id:" + id + ", main:" + main + ", description:" + description + ", icon:" + icon + ", temp:" + temp + ", pressure:" + pressure + ", humidity:" + humidity + ", temp_min:" + temp_min + ", temp_max:" + temp_max;

    }


}
