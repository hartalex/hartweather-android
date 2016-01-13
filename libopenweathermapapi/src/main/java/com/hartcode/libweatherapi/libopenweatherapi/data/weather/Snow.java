package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Snow
{
    public final int threehr;

    public Snow(int threehr)
    {
        this.threehr = threehr;
    }

    @Override
    public String toString()
    {
        return "threehr:" + threehr;
    }
}
