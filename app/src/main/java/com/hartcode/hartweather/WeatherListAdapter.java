package com.hartcode.hartweather;

import android.support.v7.widget.*;
import android.view.*;

/**
 *
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherItemViewHolder> {

    private Model model = null;

    public WeatherListAdapter(Model model)
    {
        if (this.model != null)
        {
            this.setModel(model);
        }
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    @Override
    public WeatherItemViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        return new WeatherItemViewHolder(view, pos);
    }

    @Override
    public void onBindViewHolder(WeatherItemViewHolder holder, int pos) {
        holder.bindData(this.model, pos);
    }

    @Override
    public int getItemCount() {
        return this.model.size();
    }
}
