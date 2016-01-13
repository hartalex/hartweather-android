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
    private final Queue<Integer> outgoingQueue;
    private final Queue<Weather> incomingQueue;
    private final String api_key;
    private Unit units = Unit.Fahrenheit;
    private IWeatherAPI weatherapi;

    public NetworkRequestRunnable(Queue<Integer> outgoingQueue, Queue<Weather> incomingQueue, String api_key, Unit units)
    {
        this.outgoingQueue = outgoingQueue;
        this.incomingQueue = incomingQueue;
        this.api_key = api_key;
        this.setUnits(units);
    }

    public void setUnits(Unit units)
    {
        this.units = units;
        this.weatherapi = new WeatherAPI(this.api_key,this.units);
        this.logger.debug("Changing Units to:" + units);
    }

    @Override
    public void run() {
        while(!this.isCanceled && !Thread.currentThread().isInterrupted())
        {
            // grab but don't remove an item from the queue.
            Integer cityId = this.outgoingQueue.peek();
            if (cityId != null)
            {
               Weather weather = this.weatherapi.getWeatherByCity(cityId);
               if (weather != null)
               {
                   this.incomingQueue.add(weather);
               }
                // if successful remove from the queue.
                this.outgoingQueue.remove();
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
        this.outgoingQueue.clear();
    }
}
