package com.hartcode.hartweather.libhartweather.network;

import com.hartcode.hartweather.libweatherapi.*;

/**
 *
 */
public class ErrorNetworkResponse extends NetworkResponse
{
    public final Exception exception;

    public ErrorNetworkResponse(NetworkRequest networkRequest, Exception exception)
    {
        super(networkRequest, (Weather)null);
        this.exception = exception;
    }
}
