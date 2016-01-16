package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

import java.util.*;

/**
 *
 */
public class OpenWeather {
    public final Coord coord;
    public final List<Weather> weather;
    public final Main main;
    public final Wind wind;
    public final Clouds clouds;
    public final Rain rain;
    public final Snow snow;
    public final long dt;
 //   public final Date date;
    public final Sys sys;
    public final int id;
    public final String name;
    public final int cod;


    public OpenWeather(Coord coord, List<Weather> weather, Main main, Wind wind,
                       Clouds clouds, Rain rain, Snow snow, long dt,
                       Sys sys, int id, String name, int cod)
    {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.snow = snow;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;

    }

    @Override
    public String toString()
    {
        return "coord:" + coord + ", weather:" + weather + ", main:" + main + ", wind:" + wind + ", clouds:" + clouds + ", rain:" + rain + ", snow:" + snow + ", dt:" + dt + ", sys:" + sys + ", id:" + id + ", name:" + name + ", cod:" + cod;
    }
}
