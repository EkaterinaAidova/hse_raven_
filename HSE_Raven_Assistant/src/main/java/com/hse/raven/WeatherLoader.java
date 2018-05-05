package com.hse.raven;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cat on 04.05.18.
 */

public class WeatherLoader extends Application{
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

    public Request<JSONObject> getForecastRequest(int cityID){
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

