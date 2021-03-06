/**
*
*    HartWeather - A Simple Weather Android App
*    Copyright (C) 2016  Alex Hart
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see 
*    <https://github.com/hartcode/hartweather-android/blob/master/LICENSE>.
*
*/

package com.hartcode.hartweather.libhartweather.network;

import com.hartcode.hartweather.libhartweather.network.threads.NetworkRequestRunnable;
import com.hartcode.hartweather.libhartweather.network.threads.NetworkResponseRunnable;
import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.libhartweather.network.threads.*;

import org.slf4j.*;

import java.util.concurrent.*;

/**
 *
 */
public class NetworkManager {
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);
    private final LinkedBlockingQueue<NetworkRequest> outgoingQueue;
    private final LinkedBlockingQueue<NetworkResponse> incomingQueue;
    private final Thread networkRequestThread;
    private final NetworkRequestRunnable networkRequestRunnable;
    private final Thread networkResponseThread;
    private final NetworkResponseRunnable networkResponseRunnable;
    private final INetworkView networkView;

    public NetworkManager(IWeatherAPI weatherapi, IModel model, IConnectivity connectivity, INetworkView networkView) {

        this.outgoingQueue = new LinkedBlockingQueue<>();
        this.incomingQueue = new LinkedBlockingQueue<>();

        this.networkView = networkView;
        this.networkRequestRunnable = new NetworkRequestRunnable(this.outgoingQueue, this.incomingQueue, weatherapi, connectivity, this.networkView);
        this.networkResponseRunnable = new NetworkResponseRunnable(this.incomingQueue, model, this.networkView);

        this.networkRequestThread = new Thread(this.networkRequestRunnable);
        this.networkRequestThread.start();

        this.networkResponseThread = new Thread(this.networkResponseRunnable);
        this.networkResponseThread.start();
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
            this.networkView.onNetworkQueueChange(false);
        }
    }

    public void clear()
    {
        this.outgoingQueue.clear();
        this.networkView.onNetworkQueueChange(true);
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
