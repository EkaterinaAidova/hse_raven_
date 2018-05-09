package com.hse.raven;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.JsonElement;
import com.unity3d.player.UnityPlayer;

import java.util.Date;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;

public class MainActivity extends UnityPlayerActivity implements AIListener,View.OnTouchListener {

    protected RequestQueueSingleton rq;
    protected AIService aiService;
    SharedPreferences prefs;
    Context appContext;
    //protected TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = UnityPlayer.currentActivity.getApplicationContext();
//PlayerPrefs: unity uses package name for preferences file ("com.example.app")
        prefs = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
        rq = RequestQueueSingleton.getInstance(this);
        mUnityPlayer.getView().setOnTouchListener(this);
        String aitoken = getResources().getString(R.string.CLIENT_ACCESS_TOKEN);
        //AIConfiguration
        final AIConfiguration config = new AIConfiguration(aitoken,
                AIConfiguration.SupportedLanguages.Russian,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //
                appContext = UnityPlayer.currentActivity.getApplicationContext();
//PlayerPrefs: unity uses package name for preferences file ("com.example.app")
                prefs = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);// нажатие
                Map<String, ?>pr = prefs.getAll();
                Log.i("unityprefs", pr.toString());
              /*  if() {
                    Boolean onRaven = mUnityPlayer.getSettings().getBoolean("onRaven");
                    if (onRaven) {*/
                        aiService.startListening();
                    /*}
                }*/
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
     //   rq.stopQueue();
    }
}
