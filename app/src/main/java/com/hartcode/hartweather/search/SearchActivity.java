package com.hartcode.hartweather.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.Model;
import com.hartcode.hartweather.libweatherapi.IWeatherAPI;
import com.hartcode.hartweather.libweatherapi.Unit;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.hartweather.list.IView;
import com.hartcode.hartweather.list.WeatherListActivityFragment;
import com.hartcode.hartweather.network.IConnectivity;
import com.hartcode.hartweather.network.NetworkManager;
import com.hartcode.libweatherapi.libopenweatherapi.OpenWeatherMapWeatherAPI;

public class SearchActivity extends AppCompatActivity implements IConnectivity {

    private String api_key = "34b3e14b5a4abd6edcc4c2e4051a6cab";
    private Model model;
    private Unit units = Unit.Fahrenheit;
    private NetworkManager networkManager;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.model = new Model();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        String temp_unit_string = prefs.getString(getString(R.string.temp_unit_key), Unit.Fahrenheit.toString());
        if (temp_unit_string != null)
        {
            this.units = Unit.valueOf(temp_unit_string);
        }

        this.connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);


        IWeatherAPI weatherapi = new OpenWeatherMapWeatherAPI(this.api_key, this.units);
        this.networkManager = new NetworkManager(weatherapi, model, this);
        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String name = getIntent().getStringExtra(SearchManager.QUERY);
            this.networkManager.addRequest(name);
        }

        SearchActivityFragment fragment = (SearchActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setData(model);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.networkManager.stopThreads();
    }

    @Override
    public boolean isConnectionActive() {
        boolean retval;
        NetworkInfo networkInfo = this.connectivityManager.getActiveNetworkInfo();
        retval = (networkInfo != null && networkInfo.isConnected()) ;
        return retval;
    }
}
