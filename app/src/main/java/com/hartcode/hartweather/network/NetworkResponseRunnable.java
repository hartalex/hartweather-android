package com.hartcode.hartweather.network;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.data.record.*;
import com.hartcode.hartweather.libweatherapi.*;
import org.apache.logging.log4j.*;
import java.util.*;

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
                WeatherRecord record = new WeatherRecord(weather);
                this.model.addUpdate(record);

                // After main task which could be intensive, check if the thread is cancelled
                if (!this.isCanceled) {
                    // if successful remove from the queue.

                    try {
                        this.incomingQueue.remove();
                    } catch (NoSuchElementException nsee) {
                        logger.warn("No Such Element removing from incoming queue.", nsee);
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
