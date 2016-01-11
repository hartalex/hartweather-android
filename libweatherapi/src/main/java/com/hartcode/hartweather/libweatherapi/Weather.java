package com.hartcode.hartweather.libweatherapi;

import java.lang.*;

/**
 *
 */
public class Weather {
    public final int id;
    public final String main;
    public final String description;
    public final String icon_id;

    public Weather(int id, String main, String description, String icon_id)
    {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon_id = icon_id;
    }


}
