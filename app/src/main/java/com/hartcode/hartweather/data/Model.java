package com.hartcode.hartweather.data;

import com.hartcode.hartweather.list.IView;
import com.hartcode.hartweather.libweatherapi.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 *
 */
public class Model {
    private static final Logger logger = LogManager.getLogger(Model.class);
    private final List<WeatherRecord> favoriteWeatherList;
    private static final int INDEX_NOT_FOUND = -1;
    private static final int MAX_LIST_SIZE = 10;
    private final IView view;


    /**
     * The data model for HartWeather.
     * @param view The user interface to update when the model changes.
     */
    public Model(IView view)
    {
        if (view == null)
        {
            throw new IllegalArgumentException("View cannot be null.");
        }
        this.favoriteWeatherList = new ArrayList<>();
        this.view = view;
        this.loadFromDB();
    }


    /**
     * Adds or Updates the weather in the data model.
     * @param weather weather data
     */
    public boolean addUpdate(WeatherRecord weather)
    {
        return this.addUpdate(weather,true);
    }

    private boolean addUpdate(WeatherRecord weather, boolean okToSave)
    {
        logger.debug("addUpdate");
        boolean retval = false;
        if (weather == null)
        {
            throw new IllegalArgumentException("Weather cannot be null.");
        }

        if (this.containsWeather(weather))
        {
            int index = this.getWeatherIndex(weather);
            // replace current weather
            WeatherRecord record = this.favoriteWeatherList.get(index);
            record.updateFromWeather(weather);
            if (okToSave)
            {

                record.save();
            }
            this.view.updateWeatherItem(index, weather.toWeather());
            retval = true;
        }else
        {
            if (this.favoriteWeatherList.size() >= MAX_LIST_SIZE)
            {
                // Favorites is Full
                view.showErrorMessage("Too Many Favorites Already.");
            }
            else
            {
                // Add new Weather to Full
                this.favoriteWeatherList.add(weather);
                this.view.updateWeatherItem(this.favoriteWeatherList.size()-1, weather.toWeather());
                if (okToSave)
                {
                    weather.save();
                }
                retval = true;
            }
        }
        return retval;
    }

    /**
     *  Checks the data model for the given weather.
     *  Returns the index in the array if it's found.
     *  Returns -1 if not found.
     * @param weather weather data
     */
    public int getWeatherIndex(WeatherRecord weather)
    {
        int retval = INDEX_NOT_FOUND;
        if (weather == null)
        {
            throw new IllegalArgumentException("Weather cannot be null.");
        }
        int i = 0;
        while(retval == INDEX_NOT_FOUND && i < this.favoriteWeatherList.size())
        {
            if (this.favoriteWeatherList.get(i).cityId == weather.cityId)
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
    public boolean containsWeather(WeatherRecord weather)
    {
        if (weather == null)
        {
            throw new IllegalArgumentException("Weather cannot be null.");
        }
        return this.getWeatherIndex(weather) != INDEX_NOT_FOUND;
    }

    public Weather getItem(int index)
    {
        return this.favoriteWeatherList.get(index).toWeather();
    }

    public int size()
    {
        return this.favoriteWeatherList.size();
    }

    private void loadFromDB()
    {
        logger.debug("loading from db.");
        List<WeatherRecord> weatherRecords = WeatherRecord.listAll(WeatherRecord.class);
        this.favoriteWeatherList.clear();
        for(int i = 0; i < weatherRecords.size(); i++)
        {
            this.addUpdate(weatherRecords.get(i), false);
        }

    }
}
