package com.hartcode.hartweather.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.hartweather.list.*;

public class WeatherDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WeatherDetailActivityFragment fragment = (WeatherDetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);


        Model model = new Model();

        int weatherIndex;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            weatherIndex = extras.getInt("WeatherIndex");
            Weather weather = model.getItem(weatherIndex);
            fragment.setWeather(weather);

        }
    }
 }
