package com.hartcode.hartweather.network;

import android.content.res.*;
import android.os.*;
import android.support.annotation.*;
import com.hartcode.hartweather.*;
import com.hartcode.hartweather.libhartweather.network.*;
import java.util.*;

/**
 *
 */
public class NetworkViewHandler extends Handler {

    private final List<INetworkView> networkViews;
    private final Resources resources;

    public NetworkViewHandler(@NonNull List<INetworkView> networkViews, @NonNull Resources resources)
    {
        super();
        this.networkViews = networkViews;
        this.resources = resources;
    }

    public void handleMessage(Message msg) {
        final int what = msg.what;
        switch(what)
        {
            case 0:
                for (INetworkView networkView : this.networkViews)
                {
                    networkView.onNetworkQueueChange(msg.getData().getBoolean(resources.getString(R.string.network_is_empty_key)));
                }
                break;
            case 1:
                for (INetworkView networkView : this.networkViews)
                {
                    networkView.onNetworkError(msg.getData().getString(resources.getString(R.string.network_error_key)));
                }
                break;
        }
    }

}
