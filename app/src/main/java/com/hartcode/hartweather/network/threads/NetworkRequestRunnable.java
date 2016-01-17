package com.hartcode.hartweather.network.threads;

import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.network.ErrorNetworkResponse;
import com.hartcode.hartweather.network.IConnectivity;
import com.hartcode.hartweather.network.INetworkView;
import com.hartcode.hartweather.network.NetworkManager;
import com.hartcode.hartweather.network.NetworkRequest;
import com.hartcode.hartweather.network.NetworkResponse;

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

                        if (weather != null && weather.error == 200)
                        {
                            this.incomingQueue.add(new NetworkResponse(networkRequest, weather));

                            this.popFromQueue();
                        } else if (weatherList != null) {
                            if(weatherList.size() > 0 && weatherList.get(0).error == 200)
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
                            backoffTime *= 2;
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
