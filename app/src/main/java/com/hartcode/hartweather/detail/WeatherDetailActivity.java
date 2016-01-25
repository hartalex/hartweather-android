/**
*
*    HartWeather - A Simple Weather Android App
*    Copyright (C) 2016  Alex Hart
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see 
*    <https://github.com/hartcode/hartweather-android/blob/master/LICENSE>.
*
*/

package com.hartcode.hartweather.detail;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.*;

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        WeatherDetailActivityFragment fragment = (WeatherDetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);


        this.model = new Model();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.weatherIndex = extras.getInt(getString(R.string.intent_weather_index_key));
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
                builder.setTitle(getString(R.string.dialog_delete_title));
                builder.setIcon(android.R.drawable.ic_menu_delete);
                builder.setMessage(getString(R.string.dialog_delete_question))
                        .setPositiveButton(getString(R.string.dialog_delete_positive), this)
                        .setNegativeButton(getString(R.string.dialog_delete_negative), this).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Delete Dialog button is clicked
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            this.model.delete(this.weatherIndex);
            this.finish();
        }
    }
}
