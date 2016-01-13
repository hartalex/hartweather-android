package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Main
{
    public final float temp;
    public final int pressure;
    public final int humidity;
    public final float temp_min;
    public final float temp_max;
    public final int sea_level;
    public final int grnd_level;

    public Main(float temp, int pressure, int humidity,
                     float temp_min, float temp_max, int sea_level,
                     int grnd_level)
    {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
    }
    @Override
    public String toString()
    {
        return "temp:" + temp + ", pressure:" + pressure + ", humidity:" + humidity + ", temp_min:" + temp_min + ", temp_max:" + temp_max + ", sea_level:" + sea_level + ", grnd_level:" + grnd_level;
    }
}
