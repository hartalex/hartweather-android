package com.hartcode.hartweather.network;

import android.os.*;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.network.threads.*;

import org.slf4j.*;

import java.util.*;
import java.util.concurrent.*;

/**
 *
 */
public class NetworkManager implements INetworkView{
    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);
    private final LinkedBlockingQueue<NetworkRequest> outgoingQueue;
    private final LinkedBlockingQueue<NetworkResponse> incomingQueue;
    private final Thread networkRequestThread;
    private final NetworkRequestRunnable networkRequestRunnable;
    private final Thread networkResponseThread;
    private final NetworkResponseRunnable networkResponseRunnable;
    private final List<INetworkView> networkViews;
    private final NetworkViewHandler networkDataChangeHandler;

    public NetworkManager(IWeatherAPI weatherapi, Model model, IConnectivity connectivity) {

        this.outgoingQueue = new LinkedBlockingQueue<>();
        this.incomingQueue = new LinkedBlockingQueue<>();
        this.networkViews = new ArrayList<>();
        this.networkDataChangeHandler = new NetworkViewHandler(this.networkViews);

        this.networkRequestRunnable = new NetworkRequestRunnable(this.outgoingQueue, this.incomingQueue, weatherapi, connectivity, this);
        this.networkResponseRunnable = new NetworkResponseRunnable(this.incomingQueue, model, this);

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
            this.onNetworkQueueChange(false);
        }
    }

    public void clear()
    {
        this.outgoingQueue.clear();
        this.onNetworkQueueChange(true);
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

    @Override
    public void onNetworkQueueChange(boolean isEmpty)
    {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putBoolean("isEmpty",isEmpty);
            msg.setData(bundle);
            msg.what = 0;
            this.networkDataChangeHandler.sendMessage(msg);
    }

    @Override
    public void onNetworkError(String error)
    {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("error",error);
            msg.setData(bundle);
            msg.what = 1;
            this.networkDataChangeHandler.sendMessage(msg);
    }
}
