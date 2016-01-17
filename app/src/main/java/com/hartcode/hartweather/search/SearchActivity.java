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
import com.hartcode.hartweather.network.*;
import com.hartcode.libweatherapi.libopenweatherapi.*;
import java.util.*;

public class SearchActivity extends AppCompatActivity implements IConnectivity{
    private Unit units = Unit.Fahrenheit;
    private NetworkManager networkManager;
    private ConnectivityManager connectivityManager;

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
        this.networkManager = new NetworkManager(weatherapi, model, this);

        SearchActivityFragment searchActivityFragment = (SearchActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        searchActivityFragment.setData(model, this.networkManager);

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
}
