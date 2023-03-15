package com.example.pjk.mapd_721_final_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pjk.mapd_721_final_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class WeatherFragment extends Fragment {

    TextView textViewDesc;
    TextView textViewCurrentTemp;
    TextView textViewMinTemp;
    TextView textViewMaxTemp;
    TextView textViewFeelsLike;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        Button buttonRefresh = rootView.findViewById(R.id.buttonRefreshWeather);

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
        RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Toronto&appid=48e94cfe895bf7c584b02fa01b5cad5a&units=metric";

        textViewDesc = view.findViewById(R.id.textViewWeatherDesc);
        textViewCurrentTemp = view.findViewById(R.id.textViewCurrentTemp);
        textViewMinTemp = view.findViewById(R.id.textViewMinTemp);
        textViewMaxTemp = view.findViewById(R.id.textViewMaxTemp);
        textViewFeelsLike = view.findViewById(R.id.textViewFeelsLike);
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

                    textViewDesc.setText(description);
                    textViewCurrentTemp.setText(tempCurr);
                    textViewMinTemp.setText(tempMin);
                    textViewMaxTemp.setText(tempMax);
                    textViewFeelsLike.setText(feelsLike);
//
//                    System.out.println("Description  = " + description);
//                    System.out.println("current temp = " + tempCurr);
//                    System.out.println("min temp = " + tempMin);
//                    System.out.println("max temp = " + tempMax);
//                    System.out.println("feels like  = " + feelsLike);
                    //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
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