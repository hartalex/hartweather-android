package com.hartcode.hartweather.list;

import android.app.Activity;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.Toast;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.*;

/**
 *
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherItemViewHolder> implements IWeatherChangeDataListener{

    private Model model;
    private Activity activity;

    public WeatherListAdapter(Model model, Activity activity)
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
    public WeatherItemViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        return new WeatherItemViewHolder(view, this.activity);
    }

    @Override
    public void onBindViewHolder(WeatherItemViewHolder holder, int pos) {
        holder.bindData(this.model, pos);
    }

    @Override
    public int getItemCount() {
        int retval = 0;
        if (this.model != null)
        {
            retval = this.model.weatherSize();
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
        this.notifyDataSetChanged();
    }

}
