package com.hartcode.libweatherapi.libopenweatherapi.data;

/**
 *
 */
public class Rain
{
    public final int threehr;

    public Rain(int threehr)
    {
        this.threehr = threehr;
    }

    @Override
    public String toString()
    {
        return "threehr:" + threehr;
    }
}
