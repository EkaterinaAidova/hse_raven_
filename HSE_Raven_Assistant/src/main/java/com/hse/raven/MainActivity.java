package com.hse.raven;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.google.gson.JsonElement;
import java.util.Calendar;
import java.util.Date;
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

    private int getDayOfWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK) -1;
        if (dayOfWeek == 0){
            dayOfWeek = 7;
        }

        return dayOfWeek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speaker = TextSpeaker.getInstance(this);
        rq = RequestQueueSingleton.getInstance(this);
        mUnityPlayer.getView().setOnTouchListener(this);
        String aitoken = getResources().getString(R.string.CLIENT_ACCESS_TOKEN);
        final AIConfiguration config = new AIConfiguration(aitoken,
                AIConfiguration.SupportedLanguages.Russian,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                aiService.startListening();
                break;
            case MotionEvent.ACTION_UP:
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
                StringBuilder group = new StringBuilder();
                StringBuilder date = new StringBuilder();
                if (result.getParameters() != null && !result.getParameters().isEmpty()) {
                    for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                        parameterString.append("(" + entry.getKey() + ", " + entry.getValue() + ") "); // for debug
                        if ("date".equals(entry.getKey().toString())) {
                            date.append(entry.getValue().toString());
                        } else
                        if ("group".equals(entry.getKey().toString())) {
                            group.append(entry.getValue().toString());
                        }
                    }
                    Log.d("parameters", date.toString()); // for debug
                    if (group != null && !group.toString().isEmpty()) {
                        if (date == null || date.toString().isEmpty() || "[]".equals(date.toString()))
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
        try {
            if (getDayOfWeek(date) == 7)
                throw new NoSuchElementException();
            group = group.replaceAll(" ", "");
            group = group.replaceAll("\\[|\\]", "");
            group = group.replaceAll("\"", "");
            group = group.toUpperCase();
            Log.d("date", date.toString());// for debug
            Log.d("group", group);// for debug
            rq.loadSchedule(date, group);
        }
        catch(NoSuchElementException exp){
            speaker.speak("воскресенье выходной");
        }
    }
}
