package com.hartcode.hartweather.libweatherapi;

import java.util.List;

/**
 *
 * This interface will be the mobile apps window into the weather api.
 * This way we can swap in and out other API's as necessary.
 */
public interface IWeatherAPI {
    Weather getWeatherByCity(int cityId);
    Weather getWeatherByLatLon(float lat, float lon);
    List<Weather> findCityByNameOrZip(String question);
    void setUnits(Unit unit);
}
