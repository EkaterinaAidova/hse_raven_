package com.hse.raven;

import android.Manifest;
import android.content.ContentValues;
import android.content.Entity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ai.api.AIListener;
import ai.api.*;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.EntityEntry;
import ai.api.model.Result;

import static android.content.ContentValues.TAG;

public class MainActivity extends UnityPlayerActivity implements AIListener,View.OnTouchListener {

    protected RequestQueueSingleton rq;
    protected AIService aiService;
    protected TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = RequestQueueSingleton.getInstance(this);
       // rq.loadSchedule(new Date(),"15ПИ");
       // rq.stopQueue();
        String aitoken = getResources().getString(R.string.CLIENT_ACCESS_TOKEN);
        //AIConfiguration
        final AIConfiguration config = new AIConfiguration(aitoken,
                AIConfiguration.SupportedLanguages.Russian,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        // set OnTouchListener
        View view = mUnityPlayer.getView();
        view.setOnTouchListener(this);
        //tv = new TextView(this); // for debug
        //tv.setOnTouchListener(this); // for debug
        //setContentView(tv);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                aiService.startListening();
                break;
            case MotionEvent.ACTION_UP: // отпускание
                aiService.stopListening();
                break;
                default:
                    break;
        }
        return true;
    }

    @Override
    public void onResult(ai.api.model.AIResponse response) {
        Result result = response.getResult();

        Log.d("assistant",result.toString());
        StringBuilder parameterString = new StringBuilder();
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString.append("(" + entry.getKey() + ", " + entry.getValue() + ") ");
                makeRequest(entry.getValue().toString());
            }
        }
        //tv.setText(parameterString.toString()); // for debug
    }

    @Override
    public void onError(ai.api.model.AIError error) {
        System.out.println("assistant: "+ error.getMessage());
        Log.e("assistant", error.getMessage());

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        Log.i("assistant","Listening started");

    }

    @Override
    public void onListeningCanceled() {

        Log.i("assistant","Listening canseled");
    }

    @Override
    public void onListeningFinished() {
        Log.i("assistant","Listening finished");
    }

    protected void makeRequest(String group){
        group = group.replaceAll(" ", "");
        group = group.replaceAll("\\[|\\]", "");
        group = group.replaceAll("\"", "");
        group = group.toUpperCase();
        //System.out.println(group); // for debug
        rq.loadSchedule(new Date(),group);
        rq.stopQueue();
    }
}
