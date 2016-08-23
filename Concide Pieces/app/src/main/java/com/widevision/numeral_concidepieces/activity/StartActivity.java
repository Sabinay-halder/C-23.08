package com.widevision.numeral_concidepieces.activity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class StartActivity extends Activity {

    public static String TAG = "";

    public static CCGLSurfaceView ccglsurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ccglsurface = new CCGLSurfaceView(this);
        ccglsurface.setZOrderOnTop(true);
        ccglsurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(ccglsurface);


    }

    @Override
    public void onBackPressed() {
//    	 if(TAG.equals("second_layer")){
//    		 SoundEngine.sharedEngine().pauseSound();
//    		 onStart();
//    	 	}
//    	 else if(TAG.equals("first_layer"))
//    	 {
        onStop();
        System.exit(0);
//    	 }
    }


    public void onStart() {
        super.onStart();

        CCDirector.sharedDirector().attachInView(ccglsurface);
        CCScene scene = GameLayer.scene();
        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);
        CCDirector.sharedDirector().runWithScene(scene);


    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        ccglsurface.onResume();

    }

    public void onStop() {
        super.onStop();
    }


}

