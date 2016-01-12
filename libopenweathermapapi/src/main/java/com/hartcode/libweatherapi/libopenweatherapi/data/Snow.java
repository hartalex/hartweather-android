package com.hartcode.libweatherapi.libopenweatherapi.data;

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
