package com.hartcode.libweatherapi.libopenweatherapi.data.find;

import com.hartcode.libweatherapi.libopenweatherapi.data.weather.*;
import java.util.*;

/**
 *
 */
public class SearchData
{
    public final String message;
    public final int cod;
    public final int count;
    public final List<OpenWeather> list;

    public SearchData(String message, int cod, int count, List<OpenWeather> list) {
        this.message = message;
        this.cod = cod;
        this.count = count;
        this.list = list;
    }

    @Override
    public String toString()
    {
        return "message:" + message + ", cod:" + cod + ", count:" + count+ ", list:" + list;
    }

}
