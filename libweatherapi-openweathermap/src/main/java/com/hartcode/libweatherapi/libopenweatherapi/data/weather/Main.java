package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Main
{
    public final double temp;
    public final double pressure;
    public final double humidity;
    public final double temp_min;
    public final double temp_max;
    public final double sea_level;
    public final double grnd_level;

    public Main(double temp, double pressure, double humidity,
                double temp_min, double temp_max, double sea_level,
                double grnd_level)
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
