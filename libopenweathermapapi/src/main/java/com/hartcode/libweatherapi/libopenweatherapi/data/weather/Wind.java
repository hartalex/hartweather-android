package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Wind
{
    public final double speed;
    public final double deg;

    public Wind(long speed, long deg)
    {
        this.speed = speed;
        this.deg = deg;
    }

    @Override
    public String toString()
    {
        return "speed:" + speed + ", deg:" + deg;
    }
}
