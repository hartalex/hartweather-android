/**
*
*    HartWeather - A Simple Weather Android App
*    Copyright (C) 2016  Alex Hart
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see 
*    <https://github.com/hartcode/hartweather-android/blob/master/LICENSE>.
*
*/

package com.hartcode.hartweather.libhartweather.network.threads;

import com.hartcode.hartweather.libhartweather.network.ErrorNetworkResponse;
import com.hartcode.hartweather.libhartweather.network.IConnectivity;
import com.hartcode.hartweather.libhartweather.network.INetworkView;
import com.hartcode.hartweather.libhartweather.network.NetworkRequest;
import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.libhartweather.network.NetworkResponse;

import org.slf4j.*;

import java.util.*;

/**
 *
 */
public class NetworkRequestRunnable extends NetworkRunnable
{
    private static final Logger logger = LoggerFactory.getLogger(NetworkRequestRunnable.class);

    private final Queue<NetworkRequest> outgoingQueue;
    private final Queue<NetworkResponse> incomingQueue;
    private final IWeatherAPI weatherapi;
    private final IConnectivity connectivity;
    private final INetworkView networkView;

    public NetworkRequestRunnable(Queue<NetworkRequest> outgoingQueue, Queue<NetworkResponse> incomingQueue, IWeatherAPI weatherapi, IConnectivity connectivity, INetworkView networkView)
    {
        this.outgoingQueue = outgoingQueue;
        this.incomingQueue = incomingQueue;
        this.weatherapi = weatherapi;
        this.connectivity = connectivity;
        this.networkView = networkView;
    }

    @Override
    public void doThreadWork()
    {
        if (this.connectivity.isConnectionActive())
        {
            // grab but don't remove an item from the queue.
            NetworkRequest networkRequest = this.outgoingQueue.peek();
            if (networkRequest != null)
            {
                Weather weather = null;
                List<Weather> weatherList = null;
                try
                {
                    if (networkRequest.cityId != NetworkRequest.DEFAULT_CITY_ID)
                    {
                        weather = this.weatherapi.getWeatherByCity(networkRequest.cityId);
                    } else
                    {
                        weatherList = this.weatherapi.findCityByNameOrZip(networkRequest.cityName);
                    }
                }catch(Exception ex)
                {
                    this.incomingQueue.add(new ErrorNetworkResponse(networkRequest, ex));
                    this.popFromQueue();
                }

                // After network intensive task, check if the thread is cancelled
                if (!this.isCanceled)
                {
                    if (weather != null || weatherList != null)
                    {

                        if (weather != null && weather.error == HTTP_OK)
                        {
                            this.incomingQueue.add(new NetworkResponse(networkRequest, weather));

                            this.popFromQueue();
                        } else if (weatherList != null) {
                            if(weatherList.size() > 0 && weatherList.get(0).error == HTTP_OK)
                            {
                                this.incomingQueue.add(new NetworkResponse(networkRequest, weatherList));
                            } else
                            {
                                this.incomingQueue.add(new ErrorNetworkResponse(networkRequest,new Exception("Nothing Found")));
                            }

                            this.popFromQueue();
                        } else
                        {
                            // api is down or call count exceeded
                            this.popFromQueue();
                        }
                    } else
                    {
                        // Network is probably down
                        // need to increase backoff time exponentially
                        if (backoffTime < DEFAULT_BACK_OFF_TIME_MAX)
                        {
                            backoffTime *= BACKOFF_MULTIPLE;
                        }
                    }
                }
            } else
            {
                // Tell the view that the queue is empty
                this.networkView.onNetworkQueueChange(true);
                // Network is connected, but queue is empty
                backoffTime = DEFAULT_BACK_OFF_TIME;
            }
        } else
        {
            // Network is not connected
            // need to increase backoff time exponentially
            if (backoffTime < DEFAULT_BACK_OFF_TIME_MAX)
            {
                backoffTime *= 2;
            }
        }
    }

    private void popFromQueue()
    {
        // if successful remove from the queue.
        try
        {
            this.outgoingQueue.remove();
        }
        catch (NoSuchElementException nsee)
        {
            logger.warn("No Such Element removing from outgoing queue.", nsee);
        }
        // network request successful reset backofftime
        backoffTime = DEFAULT_BACK_OFF_TIME;
    }
}
