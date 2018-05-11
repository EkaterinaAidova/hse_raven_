package com.hse.raven;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import java.util.Date;


public class RequestQueueSingleton {
        private static RequestQueueSingleton mInstance;
        private RequestQueue mRequestQueue;
        private ScheduleLoader mScheduleLoader;
        private static Context mCtx;

        private RequestQueueSingleton(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();
            mScheduleLoader = new ScheduleLoader(context);
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

        public void loadSchedule(Date date, String group){
            CustomRequest request = mScheduleLoader.getScheduleRequest(date, group);
            addToRequestQueue(request);
        }


        public void stopQueue() {
            getRequestQueue().stop();
        }

    }
