package com.hartcode.hartweather.list;

import android.support.v4.app.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherListActivityFragment extends Fragment {

    private RecyclerView recyclerView = null;
    private Model model;
    private WeatherListAdapter weatherListAdapter;

    public void setData(Model model) {
        this.model = model;
        this.weatherListAdapter.setModel(this.model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_list, container, false);

        this.weatherListAdapter = new WeatherListAdapter(this.model);

        this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(weatherListAdapter);

        return v;
    }
}
