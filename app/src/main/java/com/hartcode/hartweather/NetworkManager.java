package com.hartcode.hartweather;

import android.os.*;
import com.hartcode.hartweather.libweatherapi.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 *
 */
public class NetworkManager {
    private static final Logger logger = LogManager.getLogger(NetworkManager.class);
    private final Queue<Integer> outgoingQueue;
    private final Queue<Weather> incomingQueue;
    private final Model model;
    private final Thread networkRequestThread;
    private final NetworkRequestRunnable networkRequestRunnable;
    private final Thread networkResponseThread;
    private final NetworkResponseRunnable networkResponseRunnable;

    public NetworkManager(String api_key, Unit units, Model model) {

        this.outgoingQueue = new LinkedList<>();
        this.incomingQueue = new LinkedList<>();
        this.model = model;

        this.networkRequestRunnable = new NetworkRequestRunnable(this.outgoingQueue, this.incomingQueue, api_key, units);

        this.networkResponseRunnable = new NetworkResponseRunnable(this.incomingQueue, this.model);
        this.networkRequestThread = new Thread(this.networkRequestRunnable);
        this.networkRequestThread.start();

        this.networkResponseThread = new Thread(this.networkResponseRunnable);
        this.networkResponseThread.start();
    }

    public void setUnits(Unit units)
    {
        this.networkRequestRunnable.setUnits(units);
    }

    public void addRequest(Integer cityId)
    {
        this.outgoingQueue.add(cityId);
    }

    public void stopThreads()
    {
        this.networkRequestRunnable.stopThread();
        try {
            this.networkRequestThread.join();
        } catch (InterruptedException e) {
            logger.warn("Network Request Thread Interrupted: ", e);
        }

        this.networkResponseRunnable.stopThread();
        try {
            this.networkResponseThread.join();
        } catch (InterruptedException e) {
            logger.warn("Network Response Thread Interrupted: ", e);
        }
    }
}
