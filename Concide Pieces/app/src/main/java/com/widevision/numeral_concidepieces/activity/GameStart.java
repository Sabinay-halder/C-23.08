package com.widevision.numeral_concidepieces.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class GameStart extends Activity {

    public static CCGLSurfaceView lsv;

    //public boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        lsv = new CCGLSurfaceView(this);
        setContentView(lsv);

    }

    public void onStart() {
        super.onStart();
        CCDirector.sharedDirector().attachInView(lsv);
        CCScene scene = GameLayer.scene();
        CCDirector.sharedDirector().runWithScene(scene);
        Log.e("Start", "start");
    }

    public void onPause() {
        super.onPause();
        CCDirector.sharedDirector().pause();
        Log.e("Pause", "Pause");

    }

    public void onResume() {
        super.onResume();
        CCDirector.sharedDirector().resume();
        Log.e("Resume", "Resume");
    }

    public void onRestart() {
        super.onRestart();
        CCScene scene = GameLayer.scene();
        CCDirector.sharedDirector().replaceScene(scene);
        Log.e("Restart", "Restart");
    }

    public void onStop() {
        super.onStop();
        CCDirector.sharedDirector().end();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
   