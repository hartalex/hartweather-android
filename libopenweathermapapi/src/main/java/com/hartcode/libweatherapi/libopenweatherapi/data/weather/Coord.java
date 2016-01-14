package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Coord
{
    public final float lon;
    public final float lat;

    public Coord(float lon, float lat)
    {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return  "lon:" + lon + ", lat:" + lat;
    }
}
