package com.hartcode.hartweather.search;

import android.support.v4.app.Fragment;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.data.record.WeatherRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private Model model;
    private SearchListAdapter searchListAdapter;

    public void setData(Model model) {
        this.model = model;
        this.searchListAdapter.setModel(this.model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        this.searchListAdapter = new SearchListAdapter(this.model,this.getActivity());

        this.recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(searchListAdapter);
        return v;
    }
}
