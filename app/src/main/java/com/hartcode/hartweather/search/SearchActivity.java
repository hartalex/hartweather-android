package com.hartcode.hartweather.search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.Model;
import com.hartcode.hartweather.libweatherapi.Weather;
import com.hartcode.hartweather.list.IView;
import com.hartcode.hartweather.list.WeatherListActivityFragment;

public class SearchActivity extends AppCompatActivity{

    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.model = new Model();

        SearchActivityFragment fragment = (SearchActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setData(model);
    }

}
