package com.hartcode.libweatherapi.libopenweatherapi.data;

/**
 *
 */
public class Wind
{
    public final float speed;
    public final float deg;

    public Wind(float speed, float deg)
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
