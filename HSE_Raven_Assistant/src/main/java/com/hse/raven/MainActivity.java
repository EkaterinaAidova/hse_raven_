package com.hse.raven;

import android.os.Bundle;

public class MainActivity extends UnityPlayerActivity {

    protected RequestQueueSingleton rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rq = RequestQueueSingleton.getInstance(this);
        rq.loadSchedule("2018.04.30", "2018.05.06", "15ПИ");
    }
}
