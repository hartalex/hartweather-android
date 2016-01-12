package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;

import junit.framework.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeatherAPITest extends TestCase {
    private static final Logger logger = LogManager.getLogger(WeatherAPITest.class);
    private final static int CAIRNS_CITY_ID = 2172797;
    private final static String API_KEY = "34b3e14b5a4abd6edcc4c2e4051a6cab";
    private IWeatherAPI api;

    @Override
    protected void setUp()
    {
        api = new WeatherAPI(API_KEY,Unit.Fahrenheit);
    }

    public void testGetWeatherByCity() {
       Weather weather = api.getWeatherByCity(CAIRNS_CITY_ID);
        logger.debug(weather);
        assertNotNull(weather);
        assertTrue("Weather.temp > -20", weather.temp > -20);
    }

    public void testGetWeatherByZipCode() {
        Weather weather =  api.getWeatherByZipCode(53012);
        logger.debug(weather);
        assertNotNull(weather);
        assertTrue("Weather.temp > -20", weather.temp > -20);
    }

    public void testWeatherApiFail1sstParam()
    {
        boolean caught = false;
        try
        {
            new WeatherAPI(null,Unit.Celcius);
        }catch(IllegalArgumentException iae)
        {
            caught = true;
        }
        assertTrue("Expected Invalid Argument Exception", caught);
    }

    public void testWeatherApiFail2ndParam()
    {
        boolean caught = false;
        try
        {
            new WeatherAPI(API_KEY,null);
        }catch(IllegalArgumentException iae)
        {
            caught = true;
        }
        assertTrue("Expected Invalid Argument Exception", caught);
    }
}

