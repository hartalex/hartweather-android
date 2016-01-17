package com.hartcode.hartweather.list;

import android.support.v4.app.*;
import android.os.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.network.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherListActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, INetworkView {

    private RecyclerView recyclerView = null;
    private Model model;
    private WeatherListAdapter weatherListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkManager networkManager;

    public void setData(Model model, NetworkManager networkManager) {
        this.model = model;
        this.weatherListAdapter.setModel(this.model);
        this.networkManager = networkManager;
        this.networkManager.addNetworkView(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_list, container, false);


        this.weatherListAdapter = new WeatherListAdapter(this.model,this.getActivity());

        this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(weatherListAdapter);

        this.swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.swipeRefreshLayout.post(new Runnable()
        {
            public void run()
            {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        return v;
    }

    @Override
    public void onRefresh() {
        for (int i = 0; i < this.model.weatherSize(); i++) {
            this.networkManager.addRequest(this.model.getItem(i));
        }
    }

    @Override
    public void onNetworkQueueChange(boolean isEmpty) {
        if (isEmpty) {
            this.swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onNetworkError(String error)
    {
        Toast toast = Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT);
        toast.show();
    }

    public boolean onBackPressed() {
        boolean isHandled = false;
        if (this.swipeRefreshLayout.isRefreshing())
        {
            this.networkManager.clear();
            this.swipeRefreshLayout.setRefreshing(false);
            isHandled = true;
        }
        return isHandled;
    }
}
