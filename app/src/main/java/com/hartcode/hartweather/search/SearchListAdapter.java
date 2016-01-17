package com.hartcode.hartweather.search;

import android.app.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;

/**
 *
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchItemViewHolder> implements IWeatherChangeDataListener {

    private Model model;
    private Activity activity;

    public SearchListAdapter(@NonNull Model model, @NonNull Activity activity)
    {
        this.setModel(model);
        this.activity = activity;
    }

    public void setModel( Model model)
    {
        this.model = model;
        if (this.model != null)
        {
            this.model.addWeatherChangeDataListener(this);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public SearchItemViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new SearchItemViewHolder(view, this.activity);
    }

    @Override
    public void onBindViewHolder(SearchItemViewHolder holder, int pos) {
        holder.bindData(this.model, pos);
    }

    @Override
    public int getItemCount() {
        int retval = 0;
        if (this.model != null)
        {
            retval = this.model.searchSize();
        }
        return retval;
    }

    @Override
    public void weatherDataChange() {
        this.notifyDataSetChanged();
    }


}
