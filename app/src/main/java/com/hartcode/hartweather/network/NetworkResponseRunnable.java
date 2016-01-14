package com.hartcode.hartweather.network;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.data.record.*;

import org.slf4j.*;

import java.util.*;

/**
 *
 */
public class NetworkResponseRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(NetworkResponseRunnable.class);
    private boolean isCanceled = false;
    private final Queue<NetworkResponse> incomingQueue;
    private final Model model;


    public NetworkResponseRunnable(Queue<NetworkResponse> incomingQueue, Model model) {
        this.incomingQueue = incomingQueue;
        this.model = model;
    }

    @Override
    public void run() {
        while (!this.isCanceled && !Thread.currentThread().isInterrupted()) {
            NetworkResponse networkResponse = this.incomingQueue.peek();
            if (networkResponse != null && networkResponse.weatherList != null) {
                    if (networkResponse.networkRequest.cityName != null) {

                        List<WeatherRecord> weatherList = new ArrayList<>();
                        for(int i = 0; i < networkResponse.weatherList.size(); i++) {
                            weatherList.add(new WeatherRecord(networkResponse.weatherList.get(i)));
                        }
                        this.model.addSearchData(weatherList);
                    } else {
                        if (networkResponse.weatherList.size() > 0) {
                            this.model.addUpdate(new WeatherRecord(networkResponse.weatherList.get(0)));
                        }
                    }



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
