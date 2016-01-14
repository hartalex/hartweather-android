package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Weather
{
    public final int id;
    public final String main;
    public final String description;
    public final String icon;

    public Weather(int id, String main, String description, String icon)
    {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public String toString()
    {
        return "id:" + id + ", main:" + main + ", description:" + description + ", icon:" + icon;
    }
}
