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

package com.hartcode.hartweather.search;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.v7.app.*;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.*;
import android.view.*;
import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.libhartweather.network.*;
import com.hartcode.hartweather.network.*;
import com.hartcode.libweatherapi.libopenweatherapi.*;
import java.util.*;

public class SearchActivity extends AppCompatActivity implements IConnectivity, INetworkView
{
    private Unit units = Unit.Fahrenheit;
    private NetworkManager networkManager;
    private ConnectivityManager connectivityManager;
    private NetworkViewHandler networkDataChangeHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Model model = new Model();
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        String temp_unit_string = prefs.getString(getString(R.string.pref_temp_unit_key), Unit.Fahrenheit.toString());
        if (temp_unit_string != null)
        {
            this.units = Unit.valueOf(temp_unit_string);
        }

        this.connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        String api_key = getString(R.string.openweathermap_apikey);
        IWeatherAPI weatherapi = new OpenWeatherMapWeatherAPI(api_key, this.units, Locale.getDefault().getCountry());

        List<INetworkView> networkViews = new ArrayList<>();
        this.networkDataChangeHandler = new NetworkViewHandler(networkViews, getResources());

        this.networkManager = new NetworkManager(weatherapi, model, this, this);

        SearchActivityFragment searchActivityFragment = (SearchActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        searchActivityFragment.setData(model, this.networkManager);
        networkViews.add(searchActivityFragment);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String name = getIntent().getStringExtra(SearchManager.QUERY);
            this.networkManager.addRequest(name);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNetworkQueueChange(boolean isEmpty)
    {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.network_is_empty_key),isEmpty);
        msg.setData(bundle);
        msg.what = 0;
        this.networkDataChangeHandler.sendMessage(msg);
    }

    @Override
    public void onNetworkError(String error)
    {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.network_error_key),error);
        msg.setData(bundle);
        msg.what = 1;
        this.networkDataChangeHandler.sendMessage(msg);
    }


}
