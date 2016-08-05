package com.example.darko.testgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.darko.testgame.fragments.ScreenSlidePageFragment;
import com.example.darko.testgame.util.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends FragmentActivity {
    private static final int NUM_PAGES = 4;

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        findViewById(R.id.start_game).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, TestGameActivity.class));
//            }
//        });
//        findViewById(R.id.colision_game).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, .class));
//            }
//        });


    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<String> texts;

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<String> texts) {
            super(fm);
            this.texts = texts;
        }

        @Override
        public Fragment getItem(int position) {
            Random rand = new Random();
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int randomColor = Color.rgb(r, g, b);
            Log.d(TAG,"position :"+position);
            return new ScreenSlidePageFragment().newInstance(randomColor, texts.get(position), position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    private void init() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Color book");
        titles.add("Galaxy invaders");
        titles.add("Whack a mole");
        titles.add("Nemo's bubbles");
        mPager = (ViewPager) findViewById(R.id.main_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), titles);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

    }
}
