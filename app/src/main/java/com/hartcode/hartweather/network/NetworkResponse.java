package com.hartcode.hartweather.network;

import com.hartcode.hartweather.libweatherapi.*;

import java.util.*;

/**
 *
 */
public class NetworkResponse {
    public final List<Weather> weatherList;
    public final NetworkRequest networkRequest;
    public int error;

    public NetworkResponse(NetworkRequest networkRequest, Weather weather)
    {

        this.networkRequest = networkRequest;

        if (weather != null) {
            List<Weather> weatherList = new ArrayList<>();
            weatherList.add(weather);
            this.weatherList = weatherList;
            this.error = weather.error;
        }else
        {
            this.weatherList = null;
        }

    }

    public NetworkResponse(NetworkRequest networkRequest, List<Weather> weatherList)
    {
        this.networkRequest = networkRequest;
        this.weatherList = weatherList;
        if (this.weatherList != null && this.weatherList.size() > 0)
        {
            this.error = this.weatherList.get(0).error;
        }
    }
}
