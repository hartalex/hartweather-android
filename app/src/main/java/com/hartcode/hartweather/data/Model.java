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

package com.hartcode.hartweather.data;

import android.support.annotation.*;

import com.hartcode.hartweather.data.record.*;
import com.hartcode.hartweather.libhartweather.network.*;
import com.hartcode.hartweather.libweatherapi.*;

import java.util.*;

/**
 *
 */
public class Model implements IModel
{
    private static final int INDEX_NOT_FOUND = -1;
    private static final int MAX_LIST_SIZE = 10;
    private static final double LAT_LONG_THRESHOLD = 0.1;

    private final List<WeatherRecord> favoriteWeatherList;
    private final List<WeatherRecord> searchWeatherList;

    private WeatherDataChangeHandler weatherDataChangeHandler;
    private List<IWeatherChangeDataListener> weatherChangeDataListeners;

    /**
     * The data model for HartWeather.
     */
    public Model()
    {
        this.favoriteWeatherList = new ArrayList<>();
        this.searchWeatherList = new ArrayList<>();
        this.weatherChangeDataListeners = new ArrayList<>();
        this.weatherDataChangeHandler = new WeatherDataChangeHandler(this.weatherChangeDataListeners);
        this.loadFromDB();
    }

    public void addWeatherChangeDataListener(@NonNull IWeatherChangeDataListener weatherChangeDataListener)
    {
        this.weatherChangeDataListeners.add(weatherChangeDataListener);

    }


    /**
     * Adds or Updates the weather in the data model.
     * @param weather weather data
     */
    public boolean addUpdate(@NonNull WeatherRecord weather)
    {
        return this.addUpdate(weather, true);
    }

    private boolean addUpdate(@NonNull WeatherRecord weather, boolean okToSave)
    {
        boolean retval = false;
        Calendar cal = Calendar.getInstance();
        weather.lastUpdate = cal.getTimeInMillis();

        if (this.containsWeather(weather))
        {
            int index = this.getWeatherIndex(weather);
            // Replace current weather
            WeatherRecord record = this.favoriteWeatherList.get(index);
            record.updateFromWeatherRecord(weather);
            if (okToSave)
            {
                record.save();
            }
            this.sendWeatherDataChange();
            retval = true;
        }else
        {
            if (this.favoriteWeatherList.size() < MAX_LIST_SIZE)
            {
                // Add new Weather to list
                this.favoriteWeatherList.add(weather);
                this.sendWeatherDataChange();
                if (okToSave)
                {
                    weather.save();
                }
                retval = true;
            }
        }
        return retval;
    }

    public void addRecordSearchData(@NonNull List<WeatherRecord> weatherRecords)
    {
        this.searchWeatherList.clear();
        this.searchWeatherList.addAll(weatherRecords);
        this.sendWeatherDataChange();
    }
    /**
     *  Checks the data model for the given weather.
     *  Returns the index in the array if it's found.
     *  Returns -1 if not found.
     * @param weather weather data
     */
    public int getWeatherIndex(@NonNull WeatherRecord weather)
    {
        int retval = INDEX_NOT_FOUND;
        int i = 0;
        while(retval == INDEX_NOT_FOUND && i < this.favoriteWeatherList.size())
        {
            WeatherRecord weatherRecord = this.favoriteWeatherList.get(i);
            double lat = Math.abs(weatherRecord.lat) - Math.abs(weather.lat);
            double lon = Math.abs(weatherRecord.lon) - Math.abs(weather.lon);

            if (weatherRecord.cityId == weather.cityId || (weatherRecord.cityName.equals(weather.cityName) && lat < LAT_LONG_THRESHOLD && lon < LAT_LONG_THRESHOLD))
            {
                retval = i;
            }
            i++;
        }
        return retval;
    }

    /**
     * Checks the data model for the given weather.
     * Returns true if it's found.
     * @param weather weather data
     */
    public boolean containsWeather(@NonNull WeatherRecord weather)
    {
        return this.getWeatherIndex(weather) != INDEX_NOT_FOUND;
    }

    public Weather getItem(int index)
    {
        return this.favoriteWeatherList.get(index).toWeather();
    }

    public Weather getSearchItem(int index)
    {
        return this.searchWeatherList.get(index).toWeather();
    }

    public int weatherSize()
    {
        return this.favoriteWeatherList.size();
    }
    public int searchSize()
    {
        return this.searchWeatherList.size();
    }

    public void loadFromDB()
    {
        List<WeatherRecord> weatherRecords = WeatherRecord.listAll(WeatherRecord.class);
        this.favoriteWeatherList.clear();
        for(int i = 0; i < weatherRecords.size(); i++)
        {
            this.addUpdate(weatherRecords.get(i), false);
        }
        this.sendWeatherDataChange();
    }

    public void delete(int index)
    {
        WeatherRecord weatherRecord = this.favoriteWeatherList.remove(index);
        weatherRecord.delete();
        this.sendWeatherDataChange();
    }

    private void sendWeatherDataChange()
    {
        if (this.weatherDataChangeHandler != null) {
            this.weatherDataChangeHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public boolean addUpdate(@NonNull Weather weather)
    {
        return this.addUpdate(new WeatherRecord(weather));
    }

    @Override
    public void addSearchData(@NonNull List<Weather> weathers)
    {
        List<WeatherRecord> weatherRecords = new ArrayList<>();
        for(Weather weather : weathers)
        {
            weatherRecords.add(new WeatherRecord(weather));
        }
        addRecordSearchData(weatherRecords);
    }
}
