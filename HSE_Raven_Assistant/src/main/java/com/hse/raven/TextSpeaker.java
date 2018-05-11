package com.hse.raven;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class TextSpeaker {
    private TextToSpeech TTS;
    private static Context mCtx;
    private static TextSpeaker mInstance;

    public TextSpeaker(Context context) {
        mCtx = context;
        TTS = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    if (TTS.isLanguageAvailable(new Locale(Locale.getDefault().getLanguage()))
                            == TextToSpeech.LANG_AVAILABLE) {
                        TTS.setLanguage(new Locale(Locale.getDefault().getLanguage()));
                    } else {
                        TTS.setLanguage(Locale.US);
                    }
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                } else if (initStatus == TextToSpeech.ERROR) {
                    Log.e("tts", "tts error");
                }
            }
        });

    }

    public void speak(String text) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }
    @SuppressWarnings("deprecation") private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
    public static synchronized TextSpeaker getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TextSpeaker(context);
        }
        return mInstance;
    }

}
