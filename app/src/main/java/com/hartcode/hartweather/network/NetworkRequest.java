package com.hartcode.hartweather.network;

import java.lang.*;
/**
 *
 */
public class NetworkRequest
{
    public final float lat;
    public final float lon;
    public final String cityName;

    public NetworkRequest(float lat, float lon)
    {
        this.lat = lat;
        this.lon = lon;
        this.cityName = null;
    }

    public NetworkRequest(String cityName)
    {
        this.lat = Float.MIN_VALUE;
        this.lon = Float.MIN_VALUE;
        this.cityName = cityName;
    }

    @Override public boolean equals(Object obj) {
        boolean retval = false;
        if ( this == obj )
        {
            retval = true;
        } else {
            if ((obj instanceof NetworkRequest)) {
                NetworkRequest rhs = (NetworkRequest) obj;
                retval = (this.cityName == rhs.cityName ||
                        (this.cityName != null && this.cityName.equals(rhs.cityName))) &&
                        this.lat == rhs.lat &&
                        this.lon == rhs.lon;
            }
        }
        return retval;
    }

}
