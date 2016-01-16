package com.hartcode.hartweather.detail;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.hartweather.list.*;

public class WeatherDetailActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    private final static int DEFAULT_WEATHER_INDEX = -1;
    private int weatherIndex = DEFAULT_WEATHER_INDEX;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WeatherDetailActivityFragment fragment = (WeatherDetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);


        this.model = new Model();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.weatherIndex = extras.getInt("WeatherIndex");
            Weather weather = this.model.getItem(this.weatherIndex);
            fragment.setWeather(weather);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete");
                builder.setIcon(android.R.drawable.ic_menu_delete);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", this)
                        .setNegativeButton("No", this).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            this.model.delete(this.weatherIndex);
            this.finish();
        }
    }
}
