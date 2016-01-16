package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Rain
{
    public final double threehr;

    public Rain(double threehr)
    {
        this.threehr = threehr;
    }

    @Override
    public String toString()
    {
        return "threehr:" + threehr;
    }
}
