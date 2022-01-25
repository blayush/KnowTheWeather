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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        editTextCityName =(EditText)findViewById(R.id.editTextTextCityName);
        weatherTextView=(TextView)findViewById(R.id.weathertextView);
    }
    public void getWeather(View view){
//        DownloadTask task= new DownloadTask();
        MediaPlayer mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.mouseclick);
        mediaPlayer.start();
//        try {
//            task.execute("http://api.openweathermap.org/data/2.5/weather?q="+ editTextCityName.getText().toString() + "&APPID=036e44d3f96233198f0c9f1c3608759a");
//           // weatherTextView.setText("Weather : " + task.main + " "+" Description : "+task.description );
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "Type Correct City Name!", Toast.LENGTH_SHORT).show();
//        }

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
                    String weather=weatherData.getString("main");
                    JSONObject mainObject=responseObject.getJSONObject("main");
                    Double temp=mainObject.getDouble("temp");
                    temp=temp-273.15;
                    String temperature=String.format("%.2f",temp);
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
    }
//    public class DownloadTask extends AsyncTask<String,Void,String> {
//        public String main ;
//        public String description;
//        public String message="";
//        @Override
//        protected String doInBackground(String... urls) {
//            String result="";
//            URL url;
//            HttpURLConnection urlConnection = null;
//            try {
//                url= new URL(urls[0]);
//                urlConnection= (HttpURLConnection) url.openConnection();
//                InputStream in=urlConnection.getInputStream();
//                InputStreamReader reader=new InputStreamReader(in);
//                int data=reader.read();
//                while(data!=-1){
//                    char current=(char)data;
//                    result=result + current;
//                    data=reader.read();
//                }
//                return result;
//            } catch (Exception e) {
//
//                e.printStackTrace();
//              //  Toast.makeText(MainActivity.this, "Type Correct City Name!", Toast.LENGTH_SHORT).show();
//                return "";
//            }
//        }
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            if(s!=null){
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String weatherInfo = null;
//                    weatherInfo = jsonObject.getString("weather");
//                    Log.i("Weather content", weatherInfo);
//                    JSONArray a = new JSONArray(weatherInfo);
//                    for (int i = 0; i < a.length(); i++) {
//                        JSONObject jsonPart = a.getJSONObject(i);
//                         main=jsonPart.getString("main");
//                         description=jsonPart.getString("description");
//                         Log.i("main ",main);
//                         Log.i("description",description);
//                         if(!main.equals("")&&!description.equals(""))message+=main + " : " + description +"\r\n";
//                    }
//                    if(!message.equals(""))weatherTextView.setText(message);
//
//                } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "Type Correct City Name!", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                    //
//                }
//            }
//
//
//        }
//    }
}