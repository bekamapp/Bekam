package com.bekamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

//        Thread timerThread = new Thread(){
//            public void run(){
//                try{
//                    sleep(2000);
//                }catch(InterruptedException e){
//                    e.printStackTrace();
//                }finally{
//                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//        };
//        timerThread.start();
//    }
//
//    protected void onPause() {
//        super.onPause();
//        finish();
//    }
    }
}
