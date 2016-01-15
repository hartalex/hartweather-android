package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Snow
{
    public final double threehr;

    public Snow(double threehr)
    {
        this.threehr = threehr;
    }

    @Override
    public String toString()
    {
        return "threehr:" + threehr;
    }
}
