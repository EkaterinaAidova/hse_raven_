package com.hse.raven;

import android.app.VoiceInteractor;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    protected RequestQueue queue;
    protected ScheduleLoader scheduleLoader= new ScheduleLoader();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getApplicationContext();
        //request
        Date currentDate = new Date();
        Date fromdate = new Date();
        Date todate = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        //int dayOfWeek = scheduleLoader.getDayOfWeek(dateFormat.format(currentDate));
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;
        if (dayOfWeek == 0){
            dayOfWeek = 7;
        }
        Log.i("day", dayOfWeek.toString());
        long time = currentDate.getTime();
        //fromdate.setTime();
        todate.setTime(fromdate.getTime() + 7*24*60*60*60);
        String group = "15ПИ";
        SimpleDateFormat dateFormatToRequest = new SimpleDateFormat("yyyy.MM.dd");
        //queue = new Volley().newRequestQueue(mContext);
        Log.i("sending", "request sends");
        scheduleLoader.getScheduleRequest("2018.04.30", "2018.05.05", group, this);
       /* Request<JSONObject> request = new JsonObjectRequest(Request.Method.GET, "https://www.hse.ru/api/timetable/lessons?fromdate=2018.04.30&todate=2018.05.05&groupoid=6371&receiverType=3", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("resonse", response.toString());
                //Log.i("WORK!!!", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString()); // TODO: Handle error

            }
        });
        queue = new Volley().newRequestQueue(this);
        queue.add(request);*/
        Log.i("sending", "request got");
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDisplay = (TextView) findViewById(R.id.txtDisplay);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyBmSXUzVZBKQv9FJkTpZXn0dObKgEQOIFU&cx=014099860786446192319:t5mr0xnusiy&q=AndroidDev&alt=json&searchType=image";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                txtDisplay.setText("Response => "+response.toString());
                findViewById(R.id.progressBar1).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });

        queue.add(jsObjRequest);

    }*/

}
