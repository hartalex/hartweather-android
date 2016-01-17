package com.hartcode.hartweather.data;

import android.os.*;
import java.util.*;

/**
 *
 */
public class WeatherDataChangeHandler extends Handler {

    private final List<IWeatherChangeDataListener> weatherChangeDataListeners;

    public WeatherDataChangeHandler(List<IWeatherChangeDataListener> weatherChangeDataListeners)
    {
        super();
        this.weatherChangeDataListeners = weatherChangeDataListeners;
    }

    public void handleMessage(Message msg) {
        //final int what = msg.what;
        if (this.weatherChangeDataListeners != null) {
            for (IWeatherChangeDataListener weatherChangeDataListener : this.weatherChangeDataListeners)
            {
                weatherChangeDataListener.weatherDataChange();
            }
        }
    }

}
