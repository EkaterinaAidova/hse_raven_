package com.hse.raven;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {
        private static RequestQueueSingleton mInstance;
        private RequestQueue mRequestQueue;
        private WeatherLoader mWeatherLoader;
        private ScheduleLoader mScheduleLoader;
        private static Context mCtx;


        private RequestQueueSingleton(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();
            mWeatherLoader = new WeatherLoader(context);
            mScheduleLoader = new ScheduleLoader();
        }

        public static synchronized RequestQueueSingleton getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new RequestQueueSingleton(context);
            }
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return mRequestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }
//TODO: change signature
        public void loadSchedule(String fromdate, String todate, String group){
            CustomRequest request = mScheduleLoader.getScheduleRequest(fromdate, todate, group);
            addToRequestQueue(request);
        }

        public void loadForecast(){
            CustomRequest request = mWeatherLoader.getForecastRequest();
            addToRequestQueue(request);

        }

    }
