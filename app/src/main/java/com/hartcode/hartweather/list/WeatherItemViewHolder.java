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
import android.content.*;
import android.content.res.*;
import android.graphics.drawable.*;
import android.support.annotation.*;
import android.support.v4.content.*;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.data.*;
import com.hartcode.hartweather.detail.*;
import com.hartcode.hartweather.libweatherapi.*;

import java.text.*;
import java.util.*;

/**
 *
 */
public class WeatherItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, DialogInterface.OnClickListener {
    private final View view;
    private final TextView txtCityName;
    private final TextView txtWeatherTemp;
    private final TextView txtLastUpdate;
    private int weatherIndex;
    private final Activity activity;
    private Model model;
    private final Resources resources;

    public WeatherItemViewHolder(@NonNull View itemView, @NonNull Activity activity)
    {
        super(itemView);
        this.view = itemView;
        this.activity = activity;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        this.view.setOnClickListener(this);
        this.view.setOnLongClickListener(this);
        this.resources = this.activity.getResources();
    }

    public void bindData(@NonNull Model model, int position) {
        this.weatherIndex = position;
        this.model = model;

        Weather weather = model.getItem(position);
        this.txtCityName.setText(weather.cityName);

        String temp = String.format(this.view.getResources().getString(R.string.temp_format),(int)weather.temp,(char)0x00B0);
        this.txtWeatherTemp.setText(temp);
        Drawable iconResource = ContextCompat.getDrawable(this.view.getContext(),resources.getIdentifier(resources.getString(R.string.weather_image_prefix) + weather.icon, resources.getString(R.string.weather_image_resource_type), this.view.getContext().getPackageName()));
        this.txtWeatherTemp.setCompoundDrawablesWithIntrinsicBounds(iconResource,null,null,null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate);
        SimpleDateFormat sdfDate = new SimpleDateFormat(resources.getString(R.string.simple_date_format), Locale.US);
        String lastUpdate = String.format(resources.getString(R.string.last_update_format), sdfDate.format(calendar.getTime()));
        this.txtLastUpdate.setText(lastUpdate);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.activity, WeatherDetailActivity.class);
        intent.putExtra(resources.getString(R.string.intent_weather_index_key), this.weatherIndex);
        this.view.getContext().startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.view.getContext());
        builder.setTitle(resources.getString(R.string.dialog_delete_title));
        builder.setIcon(android.R.drawable.ic_menu_delete);
        builder.setMessage(resources.getString(R.string.dialog_delete_question))
                .setPositiveButton(resources.getString(R.string.dialog_delete_positive), this)
                .setNegativeButton(resources.getString(R.string.dialog_delete_negative), this).show();
        return false;
    }

    /**
     * Delete Dialog button is clicked
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            this.model.delete(this.weatherIndex);
        }
    }
}
