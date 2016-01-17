package com.hartcode.hartweather.data;

import android.os.*;
import android.support.annotation.*;
import java.util.*;

/**
 *
 */
public class WeatherDataChangeHandler extends Handler {

    private final List<IWeatherChangeDataListener> weatherChangeDataListeners;

    public WeatherDataChangeHandler(@NonNull List<IWeatherChangeDataListener> weatherChangeDataListeners)
    {
        super();
        this.weatherChangeDataListeners = weatherChangeDataListeners;
    }

    public void handleMessage(@NonNull Message msg) {
        for (IWeatherChangeDataListener weatherChangeDataListener : this.weatherChangeDataListeners)
        {
            weatherChangeDataListener.weatherDataChange();
        }
    }

}
