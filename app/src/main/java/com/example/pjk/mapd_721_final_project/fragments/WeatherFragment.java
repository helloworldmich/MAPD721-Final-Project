package com.example.pjk.mapd_721_final_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pjk.mapd_721_final_project.services.GpsTracker;
import com.example.pjk.mapd_721_final_project.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WeatherFragment extends Fragment {

    TextView textViewDesc;
    TextView textViewCurrentTemp;
    TextView textViewMinTemp;
    TextView textViewMaxTemp;
    TextView textViewFeelsLike;
    ImageView imageViewWeather;
    TextView textViewWeatherCity;

    TextView textViewWeatherTime;
    String currentCity;
    Double currentLong;
    Double currentLat;
    TextView textViewWeatherCoordinates;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(new Date());
        textViewWeatherTime = rootView.findViewById(R.id.textViewWeatherTime);
        textViewWeatherTime.setText(currentTime);


        Button buttonRefresh = rootView.findViewById(R.id.buttonRefreshWeather);

        GpsTracker gpsTracker = new GpsTracker(this.getContext());
        currentCity = gpsTracker.getCityName();
        currentLong = gpsTracker.getLongitude();
        currentLat = gpsTracker.getLatitude();

        refresh(rootView);
        buttonRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                refresh(rootView);
            }
        });

        return rootView;
    }

    public void refresh(View view)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(new Date());
        textViewWeatherTime = view.findViewById(R.id.textViewWeatherTime);
        textViewWeatherTime.setText(currentTime);
        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+currentCity+"&appid=48e94cfe895bf7c584b02fa01b5cad5a&units=metric";

        textViewDesc = view.findViewById(R.id.textViewWeatherDesc);
        textViewCurrentTemp = view.findViewById(R.id.textViewCurrentTemp);
        textViewMinTemp = view.findViewById(R.id.textViewMinTemp);
        textViewMaxTemp = view.findViewById(R.id.textViewMaxTemp);
        textViewFeelsLike = view.findViewById(R.id.textViewFeelsLike);
        imageViewWeather = view.findViewById(R.id.imageViewWeather);
        textViewWeatherCity = view.findViewById(R.id.textViewWeatherCity);
        textViewWeatherCoordinates = view.findViewById(R.id.textViewWeatherCoordinates);
        imageViewWeather.setScaleX(2.20f);
        imageViewWeather.setScaleY(2.20f);

        textViewWeatherCoordinates.setText(currentLat + " , " + currentLong);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject array1= (JSONObject) response.get("main");
                    String tempCurr = array1.getString("temp");
                    String feelsLike = array1.getString("feels_like");
                    String tempMin = array1.getString("temp_min");
                    String tempMax = array1.getString("temp_max");

                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject weatherObject = jsonArray.getJSONObject(0);
                    String description = weatherObject.getString("description");
                    String icon = weatherObject.getString("icon");
                    String name =  response.getString("name");

                    textViewWeatherCity.setText(name);
                    textViewDesc.setText(description);
                    textViewCurrentTemp.setText(tempCurr);
                    textViewMinTemp.setText(tempMin);
                    textViewMaxTemp.setText(tempMax);
                    textViewFeelsLike.setText(feelsLike);
                    Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@4x.png").into(imageViewWeather);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

}