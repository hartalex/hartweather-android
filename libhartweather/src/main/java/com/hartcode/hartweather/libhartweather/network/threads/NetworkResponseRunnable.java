package com.hartcode.hartweather.libhartweather.network.threads;

import com.hartcode.hartweather.libhartweather.network.ErrorNetworkResponse;
import com.hartcode.hartweather.libhartweather.network.IModel;
import com.hartcode.hartweather.libhartweather.network.INetworkView;
import com.hartcode.hartweather.libhartweather.network.NetworkResponse;

import org.slf4j.*;

import java.util.*;

/**
 *
 */
public class NetworkResponseRunnable extends NetworkRunnable
{
    private static final Logger logger = LoggerFactory.getLogger(NetworkResponseRunnable.class);
    private final Queue<NetworkResponse> incomingQueue;
    private final IModel model;
    private final INetworkView networkView;

    public NetworkResponseRunnable(Queue<NetworkResponse> incomingQueue, IModel model, INetworkView networkView)
    {
        this.incomingQueue = incomingQueue;
        this.model = model;
        this.networkView = networkView;
    }

    @Override
    public void doThreadWork()
    {
        NetworkResponse networkResponse = this.incomingQueue.peek();
        if (networkResponse != null)
        {
            if (networkResponse.weatherList != null)
            {
                if (networkResponse.networkRequest.cityName != null)
                {
                  this.model.addSearchData(networkResponse.weatherList);
                } else
                {
                    if (networkResponse.weatherList.size() > 0)
                    {
                        this.model.addUpdate(networkResponse.weatherList.get(0));
                    }
                }
            } else
            {
                if (networkResponse instanceof ErrorNetworkResponse)
                {
                    Exception exception = ((ErrorNetworkResponse) networkResponse).exception;
                    this.networkView.onNetworkError(exception.getMessage());
                }
            }

            // After main task which could be intensive, check if the thread is cancelled
            if (!this.isCanceled)
            {
                // if successful remove from the queue.
                try
                {
                    this.incomingQueue.remove();
                }
                catch (NoSuchElementException nsee)
                {
                    logger.warn("No Such Element removing from incoming queue.", nsee);
                }
            }
        }
    }
}
