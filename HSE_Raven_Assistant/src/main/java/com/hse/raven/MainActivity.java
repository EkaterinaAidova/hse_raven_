package com.hse.raven;

import android.os.Bundle;

import java.util.Date;

public class MainActivity extends UnityPlayerActivity {

    protected RequestQueueSingleton rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = RequestQueueSingleton.getInstance(this);
        rq.loadSchedule(new Date(),"15ПИ");
        rq.stopQueue();
    }
}
