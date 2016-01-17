package com.hartcode.hartweather.network;

import android.os.Handler;
import android.os.Message;

import com.hartcode.hartweather.data.IWeatherChangeDataListener;

import java.util.List;

/**
 *
 */
public class NetworkViewHandler extends Handler {

    private final List<INetworkView> networkViews;

    public NetworkViewHandler(List<INetworkView> networkViews)
    {
        super();
        this.networkViews = networkViews;
    }

    public void handleMessage(Message msg) {
        final int what = msg.what;
        switch(what)
        {
            case 0:
            if (this.networkViews != null)
            {
                for (INetworkView networkView : this.networkViews)
                {
                    networkView.onNetworkQueueChange(msg.getData().getBoolean("isEmpty"));
                }
            }
                break;
            case 1:
                for (INetworkView networkView : this.networkViews)
                {
                    networkView.onNetworkError(msg.getData().getString("error"));
                }
                break;
        }
    }

}
