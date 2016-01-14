package com.hartcode.hartweather.network;

import com.hartcode.hartweather.libweatherapi.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NetworkResponse {
    public final List<Weather> weatherList;
    public final NetworkRequest networkRequest;

    public NetworkResponse(NetworkRequest networkRequest, Weather weather)
    {
        List<Weather> weatherList = new ArrayList<>();;
        weatherList.add(weather);
        this.networkRequest = networkRequest;
        this.weatherList = weatherList;
    }

    public NetworkResponse(NetworkRequest networkRequest, List<Weather> weatherList)
    {
        this.networkRequest = networkRequest;
        this.weatherList = weatherList;
    }
}
