package com.hartcode.hartweather;

import com.hartcode.hartweather.libweatherapi.IWeatherAPI;
import com.hartcode.hartweather.libweatherapi.Unit;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.libweatherapi.libopenweatherapi.WeatherAPI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;

/**
 *
 */
public class NetworkResponseRunnable implements Runnable{
    private static final Logger logger = LogManager.getLogger(NetworkResponseRunnable.class);
    private boolean isCanceled = false;
    private final Queue<Weather> incomingQueue;
    private final Model model;

    public NetworkResponseRunnable(Queue<Weather> incomingQueue, Model model)
    {
        this.incomingQueue = incomingQueue;
        this.model = model;
    }


    @Override
    public void run() {
        while(!this.isCanceled && !Thread.currentThread().isInterrupted())
        {
            // grab but don't remove an item from the queue.
            Weather weather = this.incomingQueue.peek();
            if (weather != null)
            {
                this.model.update(weather);

                // if successful remove from the queue.
                this.incomingQueue.remove();
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
