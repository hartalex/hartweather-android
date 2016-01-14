package com.hartcode.hartweather.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.Model;
import com.hartcode.hartweather.data.record.WeatherRecord;
import com.hartcode.hartweather.detail.WeatherDetailActivity;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.hartweather.list.WeatherListActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 */
public class SearchItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    private final View view;
    private final TextView txtCityName;
    private final TextView txtWeatherTemp;
    private final ImageView imgWeather;
    private final TextView txtLastUpdate;
    private final Activity activity;
    private Model model;
    private Weather weather;

    public SearchItemViewHolder(View itemView, Activity activity)
    {
        super(itemView);
        this.view = itemView;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.imgWeather = (ImageView)this.view.findViewById(R.id.imgWeather);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        this.activity = activity;
        this.view.setOnClickListener(this);
    }

    public void bindData(Model model, int position) {
        this.model = model;
        this.weather = model.getSearchItem(position);
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
        WeatherRecord weatherRecord = new WeatherRecord(this.weather);
        this.model.addUpdate(weatherRecord);
        Context context = this.view.getContext();
        Intent intent = new Intent(this.activity, WeatherListActivity.class);
        context.startActivity(intent);
    }
}
