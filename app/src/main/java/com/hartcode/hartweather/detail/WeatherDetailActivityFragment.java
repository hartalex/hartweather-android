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

package com.hartcode.hartweather.detail;

import android.content.res.*;
import android.graphics.drawable.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.os.*;
import android.support.v4.content.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.*;
import com.hartcode.hartweather.libweatherapi.*;
import java.text.*;
import java.util.*;

/**
 *
 */
public class WeatherDetailActivityFragment extends Fragment {
    private View view;
    private TextView txtCityName;
    private TextView txtWeatherTemp;
    private ImageView imgWeather;
    private TextView txtWeatherName;
    private TextView txtLowTemp;
    private TextView txtHighTemp;
    private TextView txtLastUpdate;

    public WeatherDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.imgWeather = (ImageView)this.view.findViewById(R.id.imgWeather);
        this.txtWeatherName = (TextView)this.view.findViewById(R.id.txtWeatherName);
        this.txtLowTemp = (TextView)this.view.findViewById(R.id.txtLowTemp);
        this.txtHighTemp = (TextView)this.view.findViewById(R.id.txtHighTemp);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        return this.view;
    }

    public void setWeather(@NonNull Weather weather)
    {
        Resources resources = this.view.getResources();
        this.txtCityName.setText(weather.cityName);
        String temp = String.format(resources.getString(R.string.temp_format), (int) weather.temp, (char) 0x00B0);
        this.txtWeatherTemp.setText(temp);
        Drawable iconResource = ContextCompat.getDrawable(this.view.getContext(),resources.getIdentifier(resources.getString(R.string.weather_image_prefix) + weather.icon, resources.getString(R.string.weather_image_resource_type), this.view.getContext().getPackageName()));
        this.imgWeather.setImageDrawable(iconResource);
        this.txtWeatherName.setText(weather.main);
        temp = String.format(resources.getString(R.string.temp_low_format), (int) weather.temp_min, (char) 0x00B0);
        this.txtLowTemp.setText(temp);
        temp = String.format(resources.getString(R.string.temp_high_format), (int) weather.temp_max, (char) 0x00B0);
        this.txtHighTemp.setText(temp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate);
        SimpleDateFormat sdfDate = new SimpleDateFormat(resources.getString(R.string.simple_date_format), Locale.US);
        String lastUpdate = String.format(resources.getString(R.string.last_update_format), sdfDate.format(calendar.getTime()));
        this.txtLastUpdate.setText(lastUpdate);
    }
}
