package com.hexthegame.myapplication;

import com.hexthegame.myapplication.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.Object;
import android.graphics.Typeface;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    public static boolean loggedInBefore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN); // hide status bar

        //decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
            //if(loggedInBefore)
            //    startActivity(new Intent(SplashScreen.this,GameHub.class));
            //else
                startActivity(new Intent(SplashScreen.this, Login.class));
            finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
