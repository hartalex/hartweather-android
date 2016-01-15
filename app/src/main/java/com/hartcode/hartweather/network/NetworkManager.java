package com.hartcode.hartweather.network;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 */
public class NetworkManager {
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);
    private final LinkedBlockingQueue<NetworkRequest> outgoingQueue;
    private final LinkedBlockingQueue<NetworkResponse> incomingQueue;
    private final Model model;
    private final Thread networkRequestThread;
    private final NetworkRequestRunnable networkRequestRunnable;
    private final Thread networkResponseThread;
    private final NetworkResponseRunnable networkResponseRunnable;
    private final IWeatherAPI weatherapi;
    private final IConnectivity connectivity;
    private final List<INetworkView> networkViews;
    private final NetworkDataChangeHandler networkDataChangeHandler;

    public NetworkManager(IWeatherAPI weatherapi, Model model, IConnectivity connectivity) {

        this.weatherapi = weatherapi;
        this.outgoingQueue = new LinkedBlockingQueue<>();
        this.incomingQueue = new LinkedBlockingQueue<>();
        this.model = model;
        this.connectivity = connectivity;
        this.networkViews = new ArrayList<>();
        this.networkDataChangeHandler = new NetworkDataChangeHandler(this.networkViews);

        this.networkRequestRunnable = new NetworkRequestRunnable(this.outgoingQueue, this.incomingQueue, this.weatherapi, this.connectivity, this);
        this.networkResponseRunnable = new NetworkResponseRunnable(this.incomingQueue, this.model);

        this.networkRequestThread = new Thread(this.networkRequestRunnable);
        this.networkRequestThread.start();

        this.networkResponseThread = new Thread(this.networkResponseRunnable);
        this.networkResponseThread.start();
    }

    public void addNetworkView(INetworkView networkView)
    {
        this.networkViews.add(networkView);
    }

    public void addRequest(Weather weather)
    {
        this.addNetworkRequest(new NetworkRequest(weather.cityId));
    }

    public void addRequest(String name)
    {
        this.addNetworkRequest(new NetworkRequest(name));
    }

    private void addNetworkRequest(NetworkRequest networkParams)
    {
        if (!this.outgoingQueue.contains(networkParams)) {
            this.outgoingQueue.add(networkParams);
            // queue is full need to update view.
            this.sendNetworkQueueChange(false);
        }
    }

    public void clear()
    {
        this.outgoingQueue.clear();
        this.sendNetworkQueueChange(true);
    }

    public void sendNetworkQueueChange(boolean isEmpty)
    {
        if (this.networkDataChangeHandler != null) {
            int what = 0;
            if (isEmpty)
            {
                what = 1;
            }
            this.networkDataChangeHandler.sendEmptyMessage(what);
        }
    }

    public void stopThreads()
    {
        // let view know that the queue is no longer full

        this.networkRequestRunnable.stopThread();
        try {
            this.networkRequestThread.interrupt();
            this.networkRequestThread.join();
        } catch (InterruptedException e) {
            logger.warn("Network Request Thread Interrupted: ", e);
        }
        this.clear();

        this.networkResponseRunnable.stopThread();

        try {
            this.networkResponseThread.interrupt();
            this.networkResponseThread.join();
        } catch (InterruptedException e) {
            logger.warn("Network Response Thread Interrupted: ", e);
        }
        this.incomingQueue.clear();
    }

}
