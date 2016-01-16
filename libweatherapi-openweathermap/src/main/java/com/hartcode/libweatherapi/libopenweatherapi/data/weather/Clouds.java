package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Clouds
{
    public final double all;

    public Clouds(double all)
    {
        this.all = all;
    }

    @Override
    public String toString()
    {
        return  "all:" + all;
    }
}
