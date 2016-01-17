package com.hartcode.hartweather.list;

import android.support.annotation.*;
import android.support.v4.app.*;
import android.os.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.libhartweather.network.*;

/**
 *
 */
public class WeatherListActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, INetworkView
{

    private Model model;
    private WeatherListAdapter weatherListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkManager networkManager;
    private Boolean isOkToCancelRefresh = false;

    public void setData(Model model, NetworkManager networkManager) {
        this.model = model;
        this.weatherListAdapter.setModel(this.model);
        this.networkManager = networkManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_list, container, false);

        this.weatherListAdapter = new WeatherListAdapter(this.model,this.getActivity());

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(weatherListAdapter);

        this.swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        return v;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        this.swipeRefreshLayout.post(new Runnable()
        {
            public void run()
            {
                swipeRefreshLayout.setRefreshing(true);
                isOkToCancelRefresh = true;
            }
        });
        this.model.loadFromDB();

        // initiate network data refresh
        if (this.networkManager != null)
        {
            for (int i = 0; i < this.model.weatherSize(); i++)
            {
                this.networkManager.addRequest(this.model.getItem(i));
            }
        }
    }

    @Override
    public void onRefresh() {
        for (int i = 0; i < this.model.weatherSize(); i++) {
            this.networkManager.addRequest(this.model.getItem(i));
        }
        isOkToCancelRefresh = true;
    }

    @Override
    public void onNetworkQueueChange(boolean isEmpty) {
        if (isEmpty && isOkToCancelRefresh && this.swipeRefreshLayout.isRefreshing()) {
            this.swipeRefreshLayout.setRefreshing(false);
            this.isOkToCancelRefresh = false;
        }
    }

    @Override
    public void onNetworkError(@NonNull String error)
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
