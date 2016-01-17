package com.hartcode.hartweather.list;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.*;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

import com.hartcode.hartweather.R;
import com.hartcode.hartweather.data.Model;
import com.hartcode.hartweather.detail.WeatherDetailActivity;
import com.hartcode.hartweather.libweatherapi.Weather;

import java.text.*;
import java.util.*;

/**
 *
 */
public class WeatherItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener, DialogInterface.OnClickListener {

    private final View view;
    private final TextView txtCityName;
    private final TextView txtWeatherTemp;
    private final TextView txtLastUpdate;
    private int weatherIndex;
    private final Activity activity;
    private Model model;

    public WeatherItemViewHolder(View itemView, Activity activity)
    {
        super(itemView);
        this.view = itemView;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        this.activity = activity;
        this.view.setOnClickListener(this);
        this.view.setOnLongClickListener(this);
    }

    public void bindData(Model model, int position) {
        this.weatherIndex = position;
        this.model = model;
        Weather weather = model.getItem(position);
        this.txtCityName.setText(weather.cityName);
        String temp = String.valueOf((int)weather.temp);
        this.txtWeatherTemp.setText(temp + (char)0x00B0);
        Drawable iconResource = this.view.getContext().getResources().getDrawable(this.view.getContext().getResources().getIdentifier("icon" + weather.icon , "mipmap", this.view.getContext().getPackageName()));
        this.txtWeatherTemp.setCompoundDrawablesWithIntrinsicBounds(iconResource,null,null,null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String lastUpdate ="Last Update: " + sdfDate.format(calendar.getTime());
        this.txtLastUpdate.setText(lastUpdate);
    }

    @Override
    public void onClick(View v) {
        Context context = this.view.getContext();
        Intent intent = new Intent(this.activity, WeatherDetailActivity.class);
        intent.putExtra("WeatherIndex",this.weatherIndex);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this.activity,
                            Pair.create(this.view, context.getString(R.string.transition_weather_card))
                    );
            context.startActivity(intent, options.toBundle());
        }else
        {
            context.startActivity(intent);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.view.getContext());
        builder.setTitle("Delete");
        builder.setIcon(android.R.drawable.ic_menu_delete);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", this)
                .setNegativeButton("No", this).show();
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            this.model.delete(this.weatherIndex);
        }
    }
}
