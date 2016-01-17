package com.hartcode.hartweather.list;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.design.widget.*;
import android.support.v4.content.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libhartweather.network.*;
import com.hartcode.hartweather.settings.*;
import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.network.*;
import com.hartcode.libweatherapi.libopenweatherapi.*;

import java.util.*;

public class WeatherListActivity extends AppCompatActivity implements View.OnClickListener, MenuItemCompat.OnActionExpandListener, IConnectivity, INetworkView
{
    private Unit units = Unit.Fahrenheit;
    private NetworkManager networkManager;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ConnectivityManager connectivityManager;
    private boolean isSearchShown;
    private CharSequence searchText;
    private FloatingActionButton floatingActionButton;
    WeatherListActivityFragment weatherListActivityFragment;
    private NetworkViewHandler networkDataChangeHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Model model = new Model();
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(this);
        String temp_unit_string = prefs.getString(getString(R.string.pref_temp_unit_key), Unit.Fahrenheit.toString());
        if (temp_unit_string != null) {
            this.units = Unit.valueOf(temp_unit_string);
        }

        this.connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        String api_key = getString(R.string.openweathermap_apikey);
        IWeatherAPI weatherapi = new OpenWeatherMapWeatherAPI(api_key, this.units, Locale.getDefault().getCountry());

        List<INetworkView> networkViews = new ArrayList<>();
        this.networkDataChangeHandler = new NetworkViewHandler(networkViews, getResources());

        this.networkManager = new NetworkManager(weatherapi, model, this, this);

        setContentView(R.layout.activity_weather_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        this.weatherListActivityFragment = (WeatherListActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        this.weatherListActivityFragment.setData(model, networkManager);
        networkViews.add(this.weatherListActivityFragment);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        if (savedInstanceState != null) {
            this.isSearchShown = savedInstanceState.getBoolean(getString(R.string.saved_instance_is_search_shown_key), false);
            this.searchText = savedInstanceState.getCharSequence(getString(R.string.saved_instance_search_text_key), getString(R.string.default_saved_instance_search_text));

        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        this.searchMenuItem = menu.findItem(R.id.action_add);
        this.searchView = (SearchView) searchMenuItem.getActionView();
        this.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        this.searchView.setIconifiedByDefault(true);
        MenuItemCompat.setOnActionExpandListener(this.searchMenuItem, this);
        if (this.isSearchShown) {
            this.searchMenuItem.expandActionView();
        }
        if (this.searchText != null && this.searchText.length() > 0) {
            this.searchView.setQuery(searchText, false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.networkManager.stopThreads();
    }

    /**
     * Handles the floating action bar click
     *
     */
    @Override
    public void onClick(View v) {
        if (!this.searchMenuItem.isActionViewExpanded()) {
            // Enable search
            this.searchMenuItem.expandActionView();
        } else {
            // If fab is in search mode than submit search
            CharSequence queryText = this.searchView.getQuery();
            if (queryText != null && queryText.length() > 0) {
                this.searchView.setQuery(queryText, true);
            }
        }
    }

    @Override
    public boolean isConnectionActive() {
        boolean retval;
        NetworkInfo networkInfo = this.connectivityManager.getActiveNetworkInfo();
        retval = (networkInfo != null && networkInfo.isConnected());
        return retval;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        this.isSearchShown = this.searchMenuItem != null && this.searchMenuItem.isActionViewExpanded();

        if (this.searchView != null) {
            this.searchText = this.searchView.getQuery();
        } else {
            this.searchText = "";
        }
        bundle.putBoolean(getString(R.string.saved_instance_is_search_shown_key), this.isSearchShown);
        bundle.putCharSequence(getString(R.string.saved_instance_search_text_key), this.searchText);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            // change fab to search button
            this.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_search));
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            this.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.fab_add));
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        boolean isHandled = false;
        if (this.weatherListActivityFragment != null) {
            isHandled = this.weatherListActivityFragment.onBackPressed();
        }

        if (!isHandled){
            super.onBackPressed();
        }
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
        bundle.putString(getString(R.string.network_error_key), error);
        msg.setData(bundle);
        msg.what = 1;
        this.networkDataChangeHandler.sendMessage(msg);
    }


}
