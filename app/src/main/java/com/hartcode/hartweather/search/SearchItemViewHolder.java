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
import android.content.res.*;
import android.graphics.drawable.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.data.record.*;
import com.hartcode.hartweather.libweatherapi.*;

/**
 *
 */
public class SearchItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

    private final View view;
    private final TextView txtCityName;
    private final TextView txtWeatherTemp;
    private final Activity activity;
    private final Resources resources;
    private Model model;
    private Weather weather;


    public SearchItemViewHolder(@NonNull View itemView, @NonNull Activity activity)
    {
        super(itemView);
        this.view = itemView;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.activity = activity;
        this.view.setOnClickListener(this);
        this.resources = activity.getResources();
    }

    public void bindData(@NonNull Model model, int position) {
        this.model = model;
        this.weather = model.getSearchItem(position);
        this.txtCityName.setText(weather.cityName);
        String temp = String.format(resources.getString(R.string.temp_format), (int) weather.temp, (char) 0x00B0);
        this.txtWeatherTemp.setText(temp);
        Drawable iconResource = ContextCompat.getDrawable(this.view.getContext(),resources.getIdentifier(resources.getString(R.string.weather_image_prefix) + weather.icon, resources.getString(R.string.weather_image_resource_type), this.view.getContext().getPackageName()));
        this.txtWeatherTemp.setCompoundDrawablesWithIntrinsicBounds(iconResource,null,null,null);
    }

    @Override
    public void onClick(View v) {
        WeatherRecord weatherRecord = new WeatherRecord(this.weather);
        boolean added = this.model.addUpdate(weatherRecord);
        if (!added)
        {
            Toast toast = Toast.makeText(v.getContext(),resources.getString(R.string.location_max_exceeded),Toast.LENGTH_SHORT);
            toast.show();
        }
        NavUtils.navigateUpFromSameTask(this.activity);
    }
}
