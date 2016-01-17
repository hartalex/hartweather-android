package com.hartcode.hartweather.libweatherapi;

import java.io.IOException;
import java.util.List;

/**
 *
 * This interface will be the mobile apps window into the weather api.
 * This way we can swap in and out other API's as necessary.
 */
public interface IWeatherAPI {
    Weather getWeatherByCity(int cityId) throws IOException;
    Weather getWeatherByLatLon(double lat, double lon) throws IOException;
    List<Weather> findCityByNameOrZip(String question) throws IOException, IllegalArgumentException;
    void setUnits(Unit unit);
}
