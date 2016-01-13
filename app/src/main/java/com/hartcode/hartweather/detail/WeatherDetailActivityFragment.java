package com.hartcode.hartweather.detail;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.libweatherapi.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherDetailActivityFragment extends Fragment {
    private View view;
    private TextView txtCityName;
    private TextView txtWeatherTemp;
    private ImageView imgWeather;
    private TextView txtWeatherName;
    private TextView txtLowTemp;
    private TextView txtHighTemp;
    private TextView txtLastUpdate;

    public WeatherDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.imgWeather = (ImageView)this.view.findViewById(R.id.imgWeather);
        this.txtWeatherName = (TextView)this.view.findViewById(R.id.txtWeatherName);
        this.txtLowTemp = (TextView)this.view.findViewById(R.id.txtLowTemp);
        this.txtHighTemp = (TextView)this.view.findViewById(R.id.txtHighTemp);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        return this.view;
    }

    public void setWeather(Weather weather)
    {
        this.txtCityName.setText(weather.cityName);
        String temp = String.valueOf((int)weather.temp);
        this.txtWeatherTemp.setText(temp + (char)0x00B0);
        int iconResource = this.view.getContext().getResources().getIdentifier("icon" + weather.icon , "mipmap", this.view.getContext().getPackageName());
        this.imgWeather.setBackgroundResource(iconResource);
        this.txtWeatherName.setText(weather.main);
        temp = String.valueOf((int)weather.temp_min);
        this.txtLowTemp.setText("Low: " + temp + (char)0x00B0);
        temp = String.valueOf((int)weather.temp_max);
        this.txtHighTemp.setText("High: " + temp + (char)0x00B0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate*1000);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastUpdate ="Last Update: " + sdfDate.format(calendar.getTime());
        this.txtLastUpdate.setText(lastUpdate);
    }
}
