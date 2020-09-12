package com.example.controler.musicPage;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class TextAnimationTest {

    Handler handler1 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            assertEquals(1, message.what);
        }
    };

    Handler handler2 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message message) {
            assertEquals(2, message.what);
        }
    };

    @Test
    public void run() {
        TextAnimation textAnimation = new TextAnimation(handler1, handler2, new CurrentSongValues(1));
        textAnimation.run();
    }
}