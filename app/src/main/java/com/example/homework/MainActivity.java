package com.example.homework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       BottomNavigationView  bottomNavigationView=findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                Fragment fragment= null;
                switch (menuitem.getItemId()){
                    case  R.id.home:
                        fragment= new HomeFragment();
                        break;
                    case  R.id.search:
                        fragment= new SearchFragment();
                        break;
                    case  R.id.bookmark:
                        fragment= new BookmarkFragment();
                        break;
                    case  R.id.call:
                        fragment= new CallFragment();

                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framentContainer,fragment)
                        .commit();

                return true;
            }
        });
    }



}