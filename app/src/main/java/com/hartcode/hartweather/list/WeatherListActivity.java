package com.hartcode.hartweather.list;

import android.app.*;
import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.*;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.*;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libweatherapi.*;
import com.hartcode.hartweather.network.*;
import com.hartcode.libweatherapi.libopenweatherapi.*;

import org.slf4j.*;

public class WeatherListActivity extends AppCompatActivity implements View.OnClickListener, MenuItemCompat.OnActionExpandListener, IConnectivity {
    private static final Logger logger = LoggerFactory.getLogger(WeatherListActivity.class);

    private String api_key = "34b3e14b5a4abd6edcc4c2e4051a6cab";
    private Unit units = Unit.Fahrenheit;
    private NetworkManager networkManager;
    private Model model;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ConnectivityManager connectivityManager;
    private boolean isSearchShown;
    private CharSequence searchText;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.model = new Model();
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        String temp_unit_string = prefs.getString(getString(R.string.temp_unit_key), Unit.Fahrenheit.toString());
        if (temp_unit_string != null) {
            this.units = Unit.valueOf(temp_unit_string);
        }

        this.connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        IWeatherAPI weatherapi = new OpenWeatherMapWeatherAPI(this.api_key, this.units);
        this.networkManager = new NetworkManager(weatherapi, model, this);


        for (int i = 0; i < this.model.weatherSize(); i++) {
            this.networkManager.addRequest(this.model.getItem(i));
        }

        setContentView(R.layout.activity_weather_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WeatherListActivityFragment fragment = (WeatherListActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setData(model);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        if (savedInstanceState != null) {
            this.isSearchShown = savedInstanceState.getBoolean("isSearchShown", false);
            this.searchText = savedInstanceState.getCharSequence("searchText", "");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_list, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        this.searchMenuItem = menu.findItem(R.id.menu_add);
        this.searchView = (SearchView) searchMenuItem.getActionView();
        this.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        this.searchView.setIconifiedByDefault(true);
        MenuItemCompat.setOnActionExpandListener(this.searchMenuItem,this);
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
            // todo(alex) goto setting activity
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
     * @param v
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
        if (this.searchMenuItem != null) {
            this.isSearchShown = this.searchMenuItem.isActionViewExpanded();
        } else {
            this.isSearchShown = false;
        }

        if (this.searchView != null) {
            this.searchText = this.searchView.getQuery();
        } else {
            this.searchText = "";
        }
        bundle.putBoolean("isSearchShown", this.isSearchShown);
        bundle.putCharSequence("searchText", this.searchText);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            // change fab to search button
            this.floatingActionButton.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_search));
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            this.floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.fab_add));
        }
        return true;
    }
}
