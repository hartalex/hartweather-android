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
