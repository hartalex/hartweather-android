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
