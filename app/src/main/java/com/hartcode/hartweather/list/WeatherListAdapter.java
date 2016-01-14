package com.hartcode.hartweather.list;

import android.support.v7.widget.*;
import android.view.*;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.*;

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
        return new WeatherItemViewHolder(view);
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
