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

package com.hartcode.hartweather.list;

import android.app.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;

import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.*;

/**
 *
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherItemViewHolder> implements IWeatherChangeDataListener{

    private Model model;
    private Activity activity;

    public WeatherListAdapter(@NonNull Model model, @NonNull Activity activity)
    {
        if (this.model != null)
        {
            this.setModel(model);
        }
        this.activity = activity;
    }

    public void setModel(@NonNull Model model)
    {
        this.model = model;
        this.model.addWeatherChangeDataListener(this);
        this.weatherDataChange();
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

    @Override
    public void weatherDataChange() {
            this.notifyDataSetChanged();
    }

}
