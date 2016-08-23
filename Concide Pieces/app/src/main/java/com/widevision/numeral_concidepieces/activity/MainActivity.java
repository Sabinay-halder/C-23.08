package com.widevision.numeral_concidepieces.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.widevision.numeral_concidepieces.R;
import com.widevision.numeral_concidepieces.util.PreferenceConnector;

public class MainActivity extends Activity {

    ImageView enter, exit, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        PreferenceConnector.writeString(MainActivity.this, PreferenceConnector.RELOAD, "");

        enter = (ImageView) findViewById(R.id.enter);
        exit = (ImageView) findViewById(R.id.exit);
        help = (ImageView) findViewById(R.id.help);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StartActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(i);

            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HelpActivity.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                startActivity(i);

            }
        });
    }
}
    

