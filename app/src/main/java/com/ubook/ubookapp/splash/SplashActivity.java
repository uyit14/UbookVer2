package com.ubook.ubookapp.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ubook.ubookapp.base.BaseApplication;
import com.ubook.ubookapp.MainActivity;
import com.ubook.ubookapp.R;
import com.ubook.ubookapp.SignInActivity;
import com.ubook.ubookapp.helper.UbookHelper;

public class SplashActivity extends AppCompatActivity {
    private Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startAnimation();
    }

    //
    private void startAnimation() {
        mThread =
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        int waited = 0;
                        while (waited < 3000) {
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            waited += 100;
                        }
                        UbookHelper.changeLanguage(BaseApplication.getInstance().sharedPreferencesUtils.getLanguageCode(), SplashActivity.this);
                        if (!BaseApplication.getInstance().sharedPreferencesUtils.getAccessToken().equals("")) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            SplashActivity.this.finish();
                            SplashActivity.this.overridePendingTransition(R.anim.logo_spl, R.anim.activity_exit);
                        } else {
                            Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            SplashActivity.this.finish();
                            SplashActivity.this.overridePendingTransition(R.anim.logo_spl, R.anim.activity_exit);
                        }
                    }
                };
        mThread.start();
    }
}
