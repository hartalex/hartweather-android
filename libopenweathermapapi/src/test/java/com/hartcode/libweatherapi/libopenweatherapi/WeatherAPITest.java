package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;
import junit.framework.*;

public class WeatherAPITest extends TestCase {
    private final static int CAIRNS_CITY_ID = 2172797;
    private final static String API_KEY = "b72e613dd2fd49e1dcbbe06d97ef182d";
    private IWeatherAPI api;

    @Override
    protected void setUp()
    {
        api = new WeatherAPI(API_KEY);
    }

    public void testGetWeatherByCity() {
       Weather weather = api.getWeatherByCity(CAIRNS_CITY_ID);
        assertNotNull(weather);
        assertEquals(weather.main,"Cairns");
    }

    public void testGetWeatherByZipCode() {
        Weather weather =  api.getWeatherByZipCode(53012);
        assertNotNull(weather);
        assertEquals(weather.main,"Cedarburg");
    }
}
