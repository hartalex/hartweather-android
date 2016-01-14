package com.hartcode.hartweather.list;

import android.os.Handler;
import android.os.Message;

/**
 *
 */
public class WeatherDataChangeHandler extends Handler {

    private final IView view;

    public WeatherDataChangeHandler(IView view)
    {
        super();
        this.view = view;
    }

    public void handleMessage(Message msg) {
        //final int what = msg.what;
        if (this.view != null) {
            this.view.weatherDataChange();
        }
    }

}
