package com.hartcode.hartweather.libweatherapi;

/**
 *
 * This interface will be the mobile apps window into the weather api.
 * This way we can swap in and out other API's as necessary.
 */
public interface IWeatherAPI {
    Weather getWeatherByCity(int cityId);
    Weather getWeatherByZipCode(int zipCode);
}
