package app.kimyeonjung.visionmirror.core;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/* TTS를 상속받기 위한 클래스*/

public class TTSActivity extends AppCompatActivity {
    private static TextToSpeech tts;

    @Override
    protected void onResume() {
        super.onResume();
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.KOREAN);
                    tts.setSpeechRate((float) 0.8);
                }
            }
        });
    }

    protected void readTextAdd(String input) {
        if (tts == null) {
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        tts.setLanguage(Locale.KOREAN);
                        tts.setSpeechRate((float) 0.8);
                    }
                }
            });
        }
        if (tts.isSpeaking()) {
            tts.stop();
        }
        tts.speak(input, TextToSpeech.QUEUE_ADD, null);
    }

    protected void readTextFlush(String input) {
        if (tts == null) {
            tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        tts.setLanguage(Locale.KOREAN);
                        tts.setSpeechRate((float) 0.8);
                    }
                }
            });
        }
        if (tts.isSpeaking()) {
            tts.stop();
        }
        tts.speak(input, TextToSpeech.QUEUE_FLUSH, null);
    }

    protected void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}
