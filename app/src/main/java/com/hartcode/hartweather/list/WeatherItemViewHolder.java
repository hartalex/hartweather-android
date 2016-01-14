package com.hartcode.hartweather.list;

import android.content.Intent;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.Model;
import com.hartcode.hartweather.detail.WeatherDetailActivity;
import com.hartcode.hartweather.libweatherapi.Weather;

import java.text.*;
import java.util.*;

/**
 *
 */
public class WeatherItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    private final View view;
    private final TextView txtCityName;
    private final TextView txtWeatherTemp;
    private final ImageView imgWeather;
    private final TextView txtLastUpdate;
    private int weatherIndex;

    public WeatherItemViewHolder(View itemView)
    {
        super(itemView);
        this.view = itemView;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.imgWeather = (ImageView)this.view.findViewById(R.id.imgWeather);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        this.view.setOnClickListener(this);
    }

    public void bindData(Model model, int position) {
        this.weatherIndex = position;
        Weather weather = model.getItem(position);
        this.txtCityName.setText(weather.cityName);
        String temp = String.valueOf((int)weather.temp);
        this.txtWeatherTemp.setText(temp + (char)0x00B0);
        int iconResource = this.view.getContext().getResources().getIdentifier("icon" + weather.icon , "mipmap", this.view.getContext().getPackageName());
        this.imgWeather.setBackgroundResource(iconResource);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate*1000);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String lastUpdate ="Last Update: " + sdfDate.format(calendar.getTime());
        this.txtLastUpdate.setText(lastUpdate);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.view.getContext(), WeatherDetailActivity.class);
        intent.putExtra("WeatherIndex",this.weatherIndex);
        this.view.getContext().startActivity(intent);
    }
}
