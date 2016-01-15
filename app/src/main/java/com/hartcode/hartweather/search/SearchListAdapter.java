package com.hartcode.hartweather.search;

import android.app.*;
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

    public SearchListAdapter(Model model, Activity activity)
    {
        if (this.model != null)
        {
            this.setModel(model);
        }
        this.activity = activity;
    }

    public void setModel(Model model)
    {

        this.model = model;
        this.model.addWeatherChangeDataListener(this);
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

    /*@Override
    public void showErrorMessage(String message) {
        Toast toast = Toast.makeText(this.activity, message, Toast.LENGTH_LONG);
        toast.show();
    }*/

    @Override
    public void weatherDataChange() {
        // Update list
        this.notifyDataSetChanged();
        // notify
    }


}
