package com.hartcode.hartweather.search;

import android.support.v4.app.*;
import android.os.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.network.INetworkView;
import com.hartcode.hartweather.network.NetworkManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, INetworkView {

    private RecyclerView recyclerView;
    private Model model;
    private SearchListAdapter searchListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkManager networkManager;

    public void setData(Model model, NetworkManager networkManager) {
        this.model = model;
        this.searchListAdapter.setModel(this.model);
        this.networkManager = networkManager;
        this.networkManager.addNetworkView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        this.searchListAdapter = new SearchListAdapter(this.model,this.getActivity());

        this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(searchListAdapter);
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
    public void onNetworkError(String error)
    {
        Toast toast = Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT);
        toast.show();
    }

}
