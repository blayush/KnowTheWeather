package com.example.knowtheweather;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editTextCityName;
    TextView weatherTextView;
    String temperature,weather,cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        editTextCityName =(EditText)findViewById(R.id.editTextTextCityName);
        weatherTextView=(TextView)findViewById(R.id.weathertextView);

    }
    public void getWeather(View view){

        MediaPlayer mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.mouseclick);
        mediaPlayer.start();
        cityName=editTextCityName.getText().toString();
        //----------NEW VOLLEY CODE-------------//
        String url="http://api.openweathermap.org/data/2.5/weather?q="+ editTextCityName.getText().toString() + "&APPID=036e44d3f96233198f0c9f1c3608759a";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                JSONObject responseObject= null;
                try {
                    responseObject = new JSONObject(response);
                    JSONArray weatherArray=responseObject.getJSONArray("weather");
                    JSONObject weatherData=weatherArray.getJSONObject(0);
                    weather=weatherData.getString("main");
                    JSONObject mainObject=responseObject.getJSONObject("main");
                    Double temp=mainObject.getDouble("temp");
                    temp=temp-273.15;
                    temperature=String.format("%.1f",temp);
                    weatherTextView.setText("Weather : "+weather+"\nTemperature : "+temperature+" C");
                } catch (JSONException e) {
                    e.printStackTrace();
                    weatherTextView.setText(e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error",error.toString());
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        //---to hide keyboard---//
        InputMethodManager manager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(editTextCityName.getWindowToken(),0);


        // bottomsheet dialog
//        WeatherDialog weatherDialog=new WeatherDialog();
//        weatherDialog.show(getSupportFragmentManager(),weatherDialog.getTag());
        openDialog();
    }

    private void openDialog() {
        WeatherDialog weatherDialog=new WeatherDialog();
        weatherDialog.show(getSupportFragmentManager(),null);
        Bundle bundle= new Bundle();
        bundle.putString("city",cityName);
        bundle.putString("temp",temperature);
        bundle.putString("weather",weather);
        weatherDialog.setArguments(bundle);

    }

}