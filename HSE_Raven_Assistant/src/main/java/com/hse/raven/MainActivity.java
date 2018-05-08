package com.hse.raven;

import android.content.ContentValues;
import android.content.Entity;
import android.os.Bundle;
import android.util.Log;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ai.api.AIListener;
import ai.api.*;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.EntityEntry;

import static android.content.ContentValues.TAG;

public class MainActivity extends UnityPlayerActivity implements AIListener {

    protected RequestQueueSingleton rq;
    protected AIService aiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = RequestQueueSingleton.getInstance(this);
        rq.loadSchedule(new Date(),"15ПИ");
        rq.stopQueue();
        String aitoken = getResources().getString(R.string.CLIENT_ACCESS_TOKEN);

        //AIConfiguration
        final AIConfiguration config = new AIConfiguration(aitoken,
                AIConfiguration.SupportedLanguages.Russian,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        //Entity hseGroups = new Entity("Groups");
        //hseGroups.addEntry(new EntityEntry("15ПИ", new String[] {"пятнадцать п и", "пятнадцать пи"}));
        //hseGroups.addEntry(new EntityEntry("15ПМИ", new String[] {"пятнадцать пэ мэ и","пятнадцать П М И"}));
        //final List<Entity> entities = Collections.singletonList(hseGroups);
        //RequestExtras requestExtras = new RequestExtras(null,entities);
        aiService.startListening();
    }

    @Override
    public void onResult(ai.api.model.AIResponse result) {
        Log.i(TAG, "Result: " + result.getResult() );
    }

    @Override
    public void onError(ai.api.model.AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
