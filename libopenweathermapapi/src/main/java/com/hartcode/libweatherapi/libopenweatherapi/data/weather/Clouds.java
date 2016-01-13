package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Clouds
{
    public final float all;

    public Clouds(float all)
    {
        this.all = all;
    }

    @Override
    public String toString()
    {
        return  "all:" + all;
    }
}
