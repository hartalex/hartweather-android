package com.hartcode.hartweather.list;

import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
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
        this.activity = activity;
        this.txtCityName = (TextView)this.view.findViewById(R.id.txtCityName);
        this.txtWeatherTemp = (TextView)this.view.findViewById(R.id.txtWeatherTemp);
        this.txtLastUpdate = (TextView)this.view.findViewById(R.id.txtLastUpdate);
        this.view.setOnClickListener(this);
        this.view.setOnLongClickListener(this);

    }

    public void bindData(Model model, int position) {
        this.weatherIndex = position;
        this.model = model;
        Weather weather = model.getItem(position);
        this.txtCityName.setText(weather.cityName);

        String temp = String.format(this.view.getResources().getString(R.string.temp_format),(int)weather.temp,(char)0x00B0);
        this.txtWeatherTemp.setText(temp);
        Drawable iconResource = ContextCompat.getDrawable(this.view.getContext(), this.view.getContext().getResources().getIdentifier("icon" + weather.icon, "mipmap", this.view.getContext().getPackageName()));
        this.txtWeatherTemp.setCompoundDrawablesWithIntrinsicBounds(iconResource,null,null,null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(weather.lastUpdate);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String lastUpdate ="Last Update: " + sdfDate.format(calendar.getTime());
        this.txtLastUpdate.setText(lastUpdate);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.activity, WeatherDetailActivity.class);
        intent.putExtra("WeatherIndex",this.weatherIndex);
        this.view.getContext().startActivity(intent);
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
