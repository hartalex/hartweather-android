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

package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;

import org.junit.*;
import org.junit.Test;
import org.slf4j.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;


public class OpenWeatherMapWeatherAPITest {
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherMapWeatherAPITest.class);
    private final static int CAIRNS_CITY_ID = 2172797;

    // Normally the api key would be pulled from a private server and not hardcoded.
    // However since the api doesn't support ssl it is trivial to scrape the key from http logs.
    private final static String API_KEY = System.getenv("openweathermap_apikey");
    private IWeatherAPI api;

    @Before
    public void before()
    {
        api = new OpenWeatherMapWeatherAPI(API_KEY,Unit.Fahrenheit, Locale.getDefault().getCountry());
    }

    @Test
    public void testGetWeatherByCity() {
        Weather weather = null;
        try {
            weather = api.getWeatherByCity(CAIRNS_CITY_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(weather);
        logger.debug(weather.toString());
        assertTrue("Weather.temp > -20", weather.temp > -20);
    }

    @Test
    public void testGetWeatherByLatLon() {
        Weather weather = null;
        try {
            weather = api.getWeatherByLatLon(43.3f, -87.99f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(weather);
        logger.debug(weather.toString());
        assertTrue("Weather.temp > -20", weather.temp > -20);
    }

    @Test
    public void testFindCityByNameOrZip() {
        List<Weather> weather = null;
        try {
            weather = api.findCityByNameOrZip("53012");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(weather);
        logger.debug(weather.toString());
    }

    @Test
    public void testWeatherApiFail1sstParam()
    {
        boolean caught = false;
        try
        {
            new OpenWeatherMapWeatherAPI(null,Unit.Celsius, Locale.getDefault().getCountry());
        }catch(IllegalArgumentException iae)
        {
            caught = true;
        }
        assertTrue("Expected Invalid Argument Exception", caught);
    }

    @Test
    public void testWeatherApiFail2ndParam()
    {
        boolean caught = false;
        try
        {
            new OpenWeatherMapWeatherAPI(API_KEY,null,Locale.getDefault().getCountry());
        }catch(IllegalArgumentException iae)
        {
            caught = true;
        }
        assertTrue("Expected Invalid Argument Exception", caught);
    }
}

