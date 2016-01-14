package com.hartcode.hartweather.list;

import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.Model;
import com.hartcode.hartweather.libweatherapi.Weather;

import java.text.*;
import java.util.*;

/**
 *
 */
public class WeatherItemViewHolder extends RecyclerView.ViewHolder {

    private final View view;
    private final TextView txtCityName;
    private final TextView txtWeatherName;
    private final TextView txtWeatherTemp;
    private final ImageView imgWeather;
    private final TextView txtLastUpdate;

    public WeatherItemViewHolder(View itemView)
    {
        super(itemView);
        this.view = itemView;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherName = (TextView)this.view.findViewById(R.id.txtWeatherName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.imgWeather = (ImageView)this.view.findViewById(R.id.imgWeather);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
    }

    public void bindData(Model model, int position) {
        Weather weather = model.getItem(position);
        this.txtCityName.setText(weather.cityName);
        this.txtWeatherName.setText(weather.main);
        String temp = String.valueOf(weather.temp);
        this.txtWeatherTemp.setText(temp + (char)0x00B0);
        int iconResource = this.view.getContext().getResources().getIdentifier("icon" + weather.icon , "mipmap", this.view.getContext().getPackageName());
        this.imgWeather.setBackgroundResource(iconResource);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate*1000);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastUpdate ="Last Update: " + sdfDate.format(calendar.getTime());
        this.txtLastUpdate.setText(lastUpdate);

    }

}
