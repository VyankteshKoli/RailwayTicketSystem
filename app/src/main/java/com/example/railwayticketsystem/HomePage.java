package com.example.railwayticketsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity {

    ImageView bookticket,profile,mybooking;

    ViewPager viewPager;
    int currentPage = 0;
    Timer timer;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);


        bookticket = findViewById(R.id.bookticket);
        bookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomePage.this, BookTicketPage.class);
                startActivity(intent);
            }
        });

        mybooking = findViewById(R.id.mybooking);
        mybooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, TicketBooking.class);
                startActivity(intent);
            }
        });

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

//        viewPager = findViewById(R.id.viewPager);
//        ImagePagerAdapter adapter = new ImagePagerAdapter(this);
//        viewPager.setAdapter(adapter);
//
//        final Handler handler = new Handler(Looper.getMainLooper());
//        final Runnable update = new Runnable() {
//            public void run() {
//                if (currentPage == adapter.getCount()) {
//                    currentPage = 0;
//                }
//                viewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        }, 2000, 2000);
//}
//
//        @Override
//        protected void onDestroy () {
//            super.onDestroy();
//            if (timer != null) {
//                timer.cancel();
//            }
    }


}