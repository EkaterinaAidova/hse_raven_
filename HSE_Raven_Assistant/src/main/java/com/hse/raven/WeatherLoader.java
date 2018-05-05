package com.hse.raven;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class WeatherLoader{
    String basicUri = "http://api.openweathermap.org/data/2.5/forecast";
    private Map<String, String> params;
    RequestQueue queue;
    public WeatherLoader(Context context) {
        params = new HashMap<>(5);
        params.put("id", "520555");
        params.put("units", "metric");
        params.put("lang", "ru");
        params.put("APPID", context.getResources().getString(R.string.weather_key));
    }

    public CustomRequest getForecastRequest(int cityID){
        params.put( "id", String.valueOf(cityID));
        CustomRequest request = new CustomRequest(Request.Method.GET, basicUri, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //TODO:Parse args
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

            }
        });
        return request;
    }
    public CustomRequest getForecastRequest(){
        CustomRequest request = new CustomRequest(Request.Method.GET, basicUri, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("resonse", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString()); // TODO: Handle error

            }
        });
        return request;
    }
}

