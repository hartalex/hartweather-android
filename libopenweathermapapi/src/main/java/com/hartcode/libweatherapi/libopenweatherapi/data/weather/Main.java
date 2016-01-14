package com.hartcode.libweatherapi.libopenweatherapi.data.weather;

/**
 *
 */
public class Main
{
    public final float temp;
    public final float pressure;
    public final float humidity;
    public final float temp_min;
    public final float temp_max;
    public final float sea_level;
    public final float grnd_level;

    public Main(float temp, float pressure, float humidity,
                     float temp_min, float temp_max, float sea_level,
                float grnd_level)
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
