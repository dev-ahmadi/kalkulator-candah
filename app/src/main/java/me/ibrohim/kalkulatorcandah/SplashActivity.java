package me.ibrohim.kalkulatorcandah;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ibrohim on 7/8/16.
 */
public class SplashActivity extends AppCompatActivity {

    private SplashActivity mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mInstance = this;

        new CountDownTimer(1500, 1000) {

            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                Intent intent = new Intent(mInstance, TampilanUtama.class);
                startActivity(intent);
                mInstance.finish();
            }

        }.start();


    }
}