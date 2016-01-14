package com.hartcode.hartweather.network;

import com.hartcode.hartweather.libweatherapi.*;

import org.slf4j.*;
import java.util.*;

/**
 *
 */
public class NetworkRequestRunnable implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(NetworkRequestRunnable.class);
    private static final long DEFAULT_BACK_OFF_TIME = 100;
    private static final long DEFAULT_BACK_OFF_TIME_MAX = 60000;
    private boolean isCanceled = false;
    private final Queue<NetworkRequest> outgoingQueue;
    private final Queue<NetworkResponse> incomingQueue;
    private final IWeatherAPI weatherapi;
    private long backoffTime = DEFAULT_BACK_OFF_TIME;
    private final IConnectivity connectivity;

    public NetworkRequestRunnable(Queue<NetworkRequest> outgoingQueue, Queue<NetworkResponse> incomingQueue, IWeatherAPI weatherapi, IConnectivity connectivity)
    {
        this.outgoingQueue = outgoingQueue;
        this.incomingQueue = incomingQueue;
        this.weatherapi = weatherapi;
        this.connectivity = connectivity;
    }

    @Override
    public void run() {
        while(!this.isCanceled && !Thread.currentThread().isInterrupted())
        {
            if (this.connectivity.isConnectionActive()) {
                // grab but don't remove an item from the queue.
                NetworkRequest networkParams = this.outgoingQueue.peek();
                if (networkParams != null) {
                    Weather weather = null;
                    List<Weather> weatherList = null;
                    if (networkParams.lat != Float.MIN_VALUE) {
                        weather = this.weatherapi.getWeatherByLatLon(networkParams.lat, networkParams.lon);
                    } else {
                        weatherList = this.weatherapi.findCityByNameOrZip(networkParams.cityName);
                    }

                    // After network intensive task, check if the thread is cancelled
                    if (!this.isCanceled) {
                        if (weather != null || weatherList != null) {
                            if (weather != null) {
                                this.incomingQueue.add(new NetworkResponse(networkParams, weather));
                            }
                            if (weatherList != null)
                            {
                                this.incomingQueue.add(new NetworkResponse(networkParams, weatherList));
                            }

                            // if successful remove from the queue.
                            try {
                                this.outgoingQueue.remove();
                            } catch (NoSuchElementException nsee) {
                                logger.warn("No Such Element removing from outgoing queue.", nsee);
                            }
                            // network request successful reset backofftime
                            backoffTime = DEFAULT_BACK_OFF_TIME;
                        } else {
                            // API Request Failure
                            // need to increase backoff time exponentially
                            if (backoffTime < DEFAULT_BACK_OFF_TIME_MAX) {
                                backoffTime *= 2;
                            }
                        }
                    }
                }else
                {
                    // Network is connected, but queue is empty
                    backoffTime = DEFAULT_BACK_OFF_TIME;
                }
            }else {
                // Network is not connected
                // need to increase backoff time exponentially
                if (backoffTime < DEFAULT_BACK_OFF_TIME_MAX) {
                    backoffTime *= 2;
                }
            }

            try {
                logger.debug("Here");
                logger.debug("Sleeping for {}ms",backoffTime);
                Thread.sleep(backoffTime);
            } catch (InterruptedException e) {
               logger.warn("Network Request Runnable Thread was interrupted.", e);
            }
        }
    }

    public void stopThread() {
        this.isCanceled = true;
     }
}
