package com.hartcode.libweatherapi.libopenweatherapi;

import com.hartcode.hartweather.libweatherapi.*;

import junit.framework.*;

import org.slf4j.*;


public class OpenWeatherMapWeatherAPITest extends TestCase {
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherMapWeatherAPITest.class);
    private final static int CAIRNS_CITY_ID = 2172797;

    // Normally the api key would be pulled from a private server and not hardcoded.
    // However since the api doesn't support ssl it is trivial to scrape the key from http logs.
    private final static String API_KEY = "2de143494c0b295cca9337e1e96b00e0";
    private IWeatherAPI api;

    @Override
    protected void setUp()
    {
        api = new OpenWeatherMapWeatherAPI(API_KEY,Unit.Fahrenheit);
    }

    public void testGetWeatherByCity() {
       Weather weather = api.getWeatherByCity(CAIRNS_CITY_ID);
        logger.debug(weather.toString());
        assertNotNull(weather);
        assertTrue("Weather.temp > -20", weather.temp > -20);
    }

    public void testGetWeatherByLatLon() {
        Weather weather =  api.getWeatherByLatLon(43.3f, -87.99f);
        logger.debug(weather.toString());
        assertNotNull(weather);
        assertTrue("Weather.temp > -20", weather.temp > -20);
    }

    public void testfindCityByNameOrZip() {
        Weather weather = api.findCityByNameOrZip("53012");
        logger.debug(weather.toString());
        assertNotNull(weather);
    }

    public void testWeatherApiFail1sstParam()
    {
        boolean caught = false;
        try
        {
            new OpenWeatherMapWeatherAPI(null,Unit.Celcius);
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
            new OpenWeatherMapWeatherAPI(API_KEY,null);
        }catch(IllegalArgumentException iae)
        {
            caught = true;
        }
        assertTrue("Expected Invalid Argument Exception", caught);
    }
}

