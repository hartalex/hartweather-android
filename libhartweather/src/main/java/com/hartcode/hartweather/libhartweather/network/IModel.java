package com.hartcode.hartweather.libhartweather.network;

import com.hartcode.hartweather.libweatherapi.*;

import java.util.*;

/**
 *
 */
public interface IModel
{
    boolean addUpdate(Weather weather);
    void addSearchData(List<Weather> weathers);
}
