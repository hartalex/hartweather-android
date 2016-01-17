package com.hartcode.hartweather.search;

import android.app.*;
import android.graphics.drawable.*;
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
    private Model model;
    private Weather weather;

    public SearchItemViewHolder(View itemView, Activity activity)
    {
        super(itemView);
        this.view = itemView;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.activity = activity;
        this.view.setOnClickListener(this);
    }

    public void bindData(Model model, int position) {
        this.model = model;
        this.weather = model.getSearchItem(position);
        this.txtCityName.setText(weather.cityName);
        String temp = String.format(this.view.getResources().getString(R.string.temp_format), (int) weather.temp, (char) 0x00B0);
        this.txtWeatherTemp.setText(temp);
        Drawable iconResource = ContextCompat.getDrawable(this.view.getContext(), this.view.getContext().getResources().getIdentifier("icon" + weather.icon, "mipmap", this.view.getContext().getPackageName()));
        this.txtWeatherTemp.setCompoundDrawablesWithIntrinsicBounds(iconResource,null,null,null);
    }

    @Override
    public void onClick(View v) {
        WeatherRecord weatherRecord = new WeatherRecord(this.weather);
        boolean added = this.model.addUpdate(weatherRecord);
        if (!added)
        {
            Toast toast = Toast.makeText(v.getContext(),"Location maximum exceeded.",Toast.LENGTH_SHORT);
            toast.show();
        }
        NavUtils.navigateUpFromSameTask(this.activity);
    }
}
