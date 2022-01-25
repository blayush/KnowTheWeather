package com.example.knowtheweather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class WeatherDialog extends BottomSheetDialogFragment {

    TextView cityName,temp,weatherText;
    String cityBundle,tempBundle,weatherBundle;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Bundle data=getArguments();
        if(data!=null){
            cityBundle=data.getString("city");
            tempBundle=data.getString("temp");
            weatherBundle=data.getString("weather");

        }
    }

    public WeatherDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.weather_dialog,container,false);
        cityName=(TextView)view.findViewById(R.id.cityNameTextView);
        weatherText=(TextView) view.findViewById(R.id.weatherTextView);
        temp=(TextView)view.findViewById(R.id.temperatureTextView);
        cityName.setText(cityBundle);
    //    weatherText.setText(weatherBundle);
        temp.setText(tempBundle);
        getDialog().getWindow().setLayout(1000,1000);
        return view;
    }
}
