package com.hartcode.hartweather.detail;

import android.graphics.drawable.*;
import android.support.v4.app.*;
import android.os.*;
import android.support.v4.content.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.libweatherapi.*;
import java.text.*;
import java.util.*;

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
        String temp = String.format(this.view.getResources().getString(R.string.temp_format), (int) weather.temp, (char) 0x00B0);
        this.txtWeatherTemp.setText(temp);
        Drawable iconResource = ContextCompat.getDrawable(this.view.getContext(), this.view.getContext().getResources().getIdentifier("icon" + weather.icon, "mipmap", this.view.getContext().getPackageName()));
        this.imgWeather.setImageDrawable(iconResource);
        this.txtWeatherName.setText(weather.main);
        temp = String.format(this.view.getResources().getString(R.string.temp_low_format), (int) weather.temp_min, (char) 0x00B0);
        this.txtLowTemp.setText(temp);
        temp = String.format(this.view.getResources().getString(R.string.temp_high_format), (int) weather.temp_max, (char) 0x00B0);
        this.txtHighTemp.setText(temp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String lastUpdate ="Last Update: " + sdfDate.format(calendar.getTime());
        this.txtLastUpdate.setText(lastUpdate);
    }
}
