package com.hartcode.hartweather.network;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.*;

import org.apache.logging.log4j.*;
import java.util.*;

/**
 *
 */
public class NetworkManager {
    private static final Logger logger = LogManager.getLogger(NetworkManager.class);
    private final Queue<NetworkParams> outgoingQueue;
    private final Queue<Weather> incomingQueue;
    private final Model model;
    private final Thread networkRequestThread;
    private final NetworkRequestRunnable networkRequestRunnable;
    private final Thread networkResponseThread;
    private final NetworkResponseRunnable networkResponseRunnable;
    private final IWeatherAPI weatherapi;

    public NetworkManager(IWeatherAPI weatherapi, Model model) {

        this.weatherapi = weatherapi;
        this.outgoingQueue = new LinkedList<>();
        this.incomingQueue = new LinkedList<>();
        this.model = model;

        this.networkRequestRunnable = new NetworkRequestRunnable(this.outgoingQueue, this.incomingQueue, this.weatherapi);
        this.networkResponseRunnable = new NetworkResponseRunnable(this.incomingQueue, this.model);

        this.networkRequestThread = new Thread(this.networkRequestRunnable);
        this.networkRequestThread.start();

        this.networkResponseThread = new Thread(this.networkResponseRunnable);
        this.networkResponseThread.start();
    }

    public void addRequest(Integer cityId)
    {
        this.outgoingQueue.add(new NetworkParams(cityId));
    }

    public void addRequest(float lat, float lon)
    {
        this.outgoingQueue.add(new NetworkParams(lat, lon));
    }

    public void addRequest(String name)
    {
        this.outgoingQueue.add(new NetworkParams(name));
    }

    public void stopThreads()
    {
        this.networkRequestRunnable.stopThread();
        try {
            this.networkRequestThread.join();
        } catch (InterruptedException e) {
            logger.warn("Network Request Thread Interrupted: ", e);
        }
        this.outgoingQueue.clear();

        this.networkResponseRunnable.stopThread();
        try {
            this.networkResponseThread.join();
        } catch (InterruptedException e) {
            logger.warn("Network Response Thread Interrupted: ", e);
        }
        this.incomingQueue.clear();
    }
}
