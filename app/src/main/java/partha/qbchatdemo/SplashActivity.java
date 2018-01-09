package partha.qbchatdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import partha.qbchatdemo.chat.CreateSessionActivity;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        splashHandlerStart(SPLASH_TIME_OUT);
    }

    public void splashHandlerStart(int timeOut) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent in = new Intent(SplashActivity.this, CreateSessionActivity.class);
                    startActivity(in);
                    finish();
            }
        }, timeOut);
    }
}
