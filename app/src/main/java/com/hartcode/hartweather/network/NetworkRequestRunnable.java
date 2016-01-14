package com.hartcode.hartweather.network;

import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.libweatherapi.libopenweatherapi.*;
import org.apache.logging.log4j.*;
import java.util.*;

/**
 *
 */
public class NetworkRequestRunnable implements Runnable{
    private static final Logger logger = LogManager.getLogger(NetworkRequestRunnable.class);
    private boolean isCanceled = false;
    private final Queue<NetworkParams> outgoingQueue;
    private final Queue<Weather> incomingQueue;
    private final IWeatherAPI weatherapi;

    public NetworkRequestRunnable(Queue<NetworkParams> outgoingQueue, Queue<Weather> incomingQueue, IWeatherAPI weatherapi)
    {
        this.outgoingQueue = outgoingQueue;
        this.incomingQueue = incomingQueue;
        this.weatherapi = weatherapi;
    }

    @Override
    public void run() {
        while(!this.isCanceled && !Thread.currentThread().isInterrupted())
        {
            // grab but don't remove an item from the queue.
            NetworkParams networkParams = this.outgoingQueue.peek();
            if (networkParams != null)
            {
               Weather weather = null;
                if (networkParams.cityId > 0)
                {
                    weather = this.weatherapi.getWeatherByCity(networkParams.cityId);
                }else if (networkParams.lat != Float.MIN_VALUE)
                {
                    weather = this.weatherapi.getWeatherByLatLon(networkParams.lat, networkParams.lon);
                }else
                {
                    weather = this.weatherapi.findCityByNameOrZip(networkParams.cityName);
                }

                // After network intensive task, check if the thread is cancelled
                if (!this.isCanceled) {
                    if (weather != null) {
                        this.incomingQueue.add(weather);
                    }
                    // if successful remove from the queue.
                    try {
                        this.outgoingQueue.remove();
                    } catch (NoSuchElementException nsee) {
                        logger.warn("No Such Element removing from outgoing queue.", nsee);
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
               this.logger.warn("Network Request Runnable Thread was interrupted.", e);
            }
        }
    }

    public void stopThread() {
        this.isCanceled = true;
     }
}
