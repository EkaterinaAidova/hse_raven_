package com.hse.raven;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.unity3d.player.UnityPlayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.Result;

public class MainActivity extends UnityPlayerActivity implements AIListener,View.OnTouchListener {

    protected RequestQueueSingleton rq;
    protected AIService aiService;
    SharedPreferences prefs;
    private TextSpeaker speaker;
    Context appContext;
    //protected TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = UnityPlayer.currentActivity.getApplicationContext();
        speaker = TextSpeaker.getInstance(appContext);
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
    public void onResult(ai.api.model.AIResponse response){
        Result result = response.getResult();
        Log.d("assistant",result.toString());
        String action = result.getAction();

        switch(action)
        {
            case "get_group":
                StringBuilder parameterString = new StringBuilder();
                if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                    StringBuilder group = null;
                    StringBuilder date = null;
                    for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                        parameterString.append("(" + entry.getKey() + ", " + entry.getValue() + ") "); // for debug
                        System.out.println(entry.getKey());
                        if (entry.getKey() == "date") {
                            Log.d("parameters", "initializing group");
                            group = new StringBuilder("");
                            date.append(entry.getValue().toString());
                        } else
                        if (entry.getKey() == "group") {
                            Log.d("parameters", "initializing date");
                            date = new StringBuilder("");
                            group.append(entry.getValue().toString());
                        }
                    }
                    Log.d("parameters", parameterString.toString());
                    Log.d("parameters", group.toString() + "\n" + date.toString());
                    if (group != null && !group.toString().isEmpty()) {
                        if (date == null || date.toString().isEmpty())
                            makeRequest(group.toString(), new Date());
                        else
                            makeRequest(group.toString(), result.getDateParameter("date"));
                    }else
                        speaker.speak("расписание какой группы ты хочешь узнать?");
                }else
                    speaker.speak("расписание какой группы ты хочешь узнать?");
                break;
            case "smalltalk.greetings.hello":
                speaker.speak("Привет, студент вышки! Я могу рассказать тебе твоё расписание на сегодня! ");
                break;
            case "smalltalk.agent.good":
                speaker.speak("Добрый день, студент вышки!");
                break;
            case "smalltalk.greetings.goodevening":
                speaker.speak("Добрый вечер, студент вышки!");
                break;
            case "smalltalk.greetings.goodbye":
                speaker.speak("пока!");
                break;
            default:
                speaker.speak("я не понимаю о чём ты");
                break;
        }


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

    protected void makeRequest(String group, Date date ){
        group = group.replaceAll(" ", "");
        group = group.replaceAll("\\[|\\]", "");
        group = group.replaceAll("\"", "");
        group = group.toUpperCase();
        Log.d("date", date.toString());// for debug
        Log.d("group", group);// for debug
        rq.loadSchedule(date ,group);
    }
}
