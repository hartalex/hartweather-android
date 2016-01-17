package com.hartcode.hartweather.libhartweather.network;

import java.lang.*;
/**
 *
 */
public class NetworkRequest
{
    public final static int DEFAULT_CITY_ID = -1;
    public final int cityId;
    public final String cityName;

    public NetworkRequest(int cityId)
    {
        this.cityId = cityId;
        this.cityName = null;
    }

    public NetworkRequest(String cityName)
    {
        this.cityId = DEFAULT_CITY_ID;
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
                        this.cityId == rhs.cityId;
            }
        }
        return retval;
    }

}
