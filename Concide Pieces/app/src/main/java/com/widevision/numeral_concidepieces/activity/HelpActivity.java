package com.widevision.numeral_concidepieces.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.widevision.numeral_concidepieces.R;

import java.util.Timer;
import java.util.TimerTask;

public class HelpActivity extends Activity {

    ViewPager viewPager;
    ImageView home_button;
    Handler handler = new Handler();
    Timer swipeTimer = new Timer();
    int currentPage;
    int NumberOfPages = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_help);
        viewPager = (ViewPager) findViewById(R.id.show_help);
        home_button = (ImageView) findViewById(R.id.home_button);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);

        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 100, 4000);



        //home button click
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private class MyPagerAdapter extends PagerAdapter {



        int[] res = {R.drawable.image1, R.drawable.image2,R.drawable.image3,R.drawable.popup};

        @Override
        public int getCount() {
            return NumberOfPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(HelpActivity.this);

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


            BitmapFactory.Options ourOptions = null;
            ourOptions = new BitmapFactory.Options();
            ourOptions.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res[position], ourOptions);
            imageView.setImageBitmap(bitmap);
            imageView.setBackgroundResource(R.drawable.transparentgradient);

            container.addView(imageView, imageParams);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }

    }

    //Change view automatically
    Runnable Update = new Runnable() {
        public void run() {
            if (currentPage == NumberOfPages) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        }
    };




}


