package com.hartcode.hartweather.network;

import android.os.Handler;
import android.os.Message;

import com.hartcode.hartweather.data.IWeatherChangeDataListener;

import java.util.List;

/**
 *
 */
public class NetworkDataChangeHandler extends Handler {

    private final List<INetworkView> networkViews;

    public NetworkDataChangeHandler(List<INetworkView> networkViews)
    {
        super();
        this.networkViews = networkViews;
    }

    public void handleMessage(Message msg) {
        //final int what = msg.what;
        if (this.networkViews != null) {
            for (INetworkView networkView : this.networkViews)
            {
                networkView.onNetworkQueueChange(msg.what==1);
            }
        }
    }

}
