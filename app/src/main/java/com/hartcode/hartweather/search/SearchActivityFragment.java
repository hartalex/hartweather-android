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
public class SearchActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, INetworkView
{
    private Model model;
    private SearchListAdapter searchListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkManager networkManager;

    public void setData(@NonNull Model model, @NonNull NetworkManager networkManager) {
        this.model = model;
        this.searchListAdapter.setModel(this.model);
        this.networkManager = networkManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        this.searchListAdapter = new SearchListAdapter(this.model,this.getActivity());

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(searchListAdapter);
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

    }

    @Override
    public void onNetworkQueueChange(boolean isEmpty) {
        if (isEmpty) {
            this.swipeRefreshLayout.setRefreshing(false);
            this.swipeRefreshLayout.setEnabled(false);
            this.networkManager.stopThreads();
        }
    }

    @Override
    public void onNetworkError(@NonNull String error)
    {
        Toast toast = Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT);
        toast.show();
    }

}
