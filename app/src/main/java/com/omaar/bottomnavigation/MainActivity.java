package com.omaar.bottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    BottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestFragment1 f1 = new TestFragment1("Homeeeee");
        RedFrag f2 = new RedFrag();
        TestFragment1 f3 = new TestFragment1("ContactUs");
        TestFragment1 f4 = new TestFragment1("Settings");
        TestFragment1 f5 = new TestFragment1("Settings");

        bottomNavigation = findViewById(R.id.tabs);

        bottomNavigation.add(f1, R.drawable.home_animated_icon);
        bottomNavigation.add(f2, R.drawable.control_panel_animated_icon);
        bottomNavigation.add(f3, R.drawable.contact_us_animated_icon);
        bottomNavigation.add(f4, R.drawable.settings_animated_icon);
        bottomNavigation.add(f5, R.drawable.places_animated_icon);


    }
}
