package com.example.knowtheweather

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.widget.TextView
import android.widget.LinearLayout
import android.os.Bundle
import com.example.knowtheweather.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class WeatherDialog : BottomSheetDialogFragment() {
    private var cityName: TextView? = null
    private var temp: TextView? = null
    private var weatherText: TextView? = null
    private var cityBundle: String? = null
    private var tempBundle: String? = null
    private var weatherBundle: String? = null
    private var dialogLayout: LinearLayout? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val data = arguments
        if (data != null) {
            cityBundle = data.getString("city")
            tempBundle = data.getString("temp")
            weatherBundle = data.getString("weather")
        }
        if (weatherBundle == "mist") {
            dialogLayout!!.setBackgroundResource(R.drawable.dialog_gradient)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_dialog, container, false)
        dialogLayout = view.findViewById(R.id.dialoglinearLayout)
        cityName = view.findViewById<View>(R.id.cityNameTextView) as TextView
        weatherText = view.findViewById<View>(R.id.weatherTextView) as TextView
        temp = view.findViewById<View>(R.id.temperatureTextView) as TextView
        cityName!!.text = cityBundle
        weatherText!!.text = weatherBundle
        temp!!.text = tempBundle
        dialog!!.window!!.setLayout(1000, 1000)
        return view
    }
}