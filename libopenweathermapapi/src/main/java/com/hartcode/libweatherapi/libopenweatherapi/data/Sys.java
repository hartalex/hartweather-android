package com.hartcode.libweatherapi.libopenweatherapi.data;

/**
 *
 */
public class Sys
{
    public final int type;
    public final int id;
    public final float message;
    public final String country;
    public final long sunrise;
    public final long sunset;

    public Sys(int type, int id, float message, String country,
               long sunrise, long sunset)
    {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    @Override
    public String toString()
    {
        return "type:" + type + ", id:" + id + ", message:" + message + ", country:" + country + ", sunrise:" + sunrise + ", sunset:" + sunset;
    }
}