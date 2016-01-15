package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Coord
{
    public final double lon;
    public final double lat;

    public Coord(double lon, double lat)
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
