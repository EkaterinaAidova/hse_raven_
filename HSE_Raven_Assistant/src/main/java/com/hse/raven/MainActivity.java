package com.hse.raven;

import android.os.Bundle;

import ai.api.AIListener;
import ai.api.*;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;

public class MainActivity extends UnityPlayerActivity implements AIListener {

    protected RequestQueueSingleton rq;
    protected AIService aiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = RequestQueueSingleton.getInstance(this);
        rq.loadSchedule("2018.04.30", "2018.05.06", "15ПИ");
        rq.stopQueue();

        //AIConfiguration
        final AIConfiguration config = new AIConfiguration(this.getResources().getString(R.string.CLIENT_ACESS_TOKEN),
                AIConfiguration.SupportedLanguages.Russian,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        aiService.startListening();
    }

    @Override
    public void onResult(ai.api.model.AIResponse result) {

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
