package com.hartcode.hartweather.network;

import java.lang.*;
/**
 *
 */
public class NetworkParams
{
    public final int cityId;
    public final float lat;
    public final float lon;
    public final String cityName;

    public NetworkParams(int cityId)
    {
        this.cityId = cityId;
        this.lat = Float.MIN_VALUE;
        this.lon = Float.MIN_VALUE;
        this.cityName = null;

    }
    public NetworkParams(float lat, float lon)
    {
        this.cityId = 0;
        this.lat = lat;
        this.lon = lon;
        this.cityName = null;
    }

    public NetworkParams(String cityName)
    {
        this.cityId = 0;
        this.lat = Float.MIN_VALUE;
        this.lon = Float.MIN_VALUE;
        this.cityName = cityName;
    }
}
