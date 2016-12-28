package com.example.m_alrajab.hpi_simplefitnessapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;


public class LandingScreen extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    UserLocalDetails userdetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();
        setSupportActionBar(toolbar);
        userdetails = new UserLocalDetails(this);
        userdetails.storeHistory(this, "Landing page");

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        TabLayout.Tab profileTab = tabLayout.newTab().setText("PROFILE");
        TabLayout.Tab notesTab =  tabLayout.newTab().setText("NOTES");
        TabLayout.Tab HistoryTab = tabLayout.newTab().setText("HISTORY");

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()) {
                    case "PROFILE":
                        displayTab(new LandingScreenFragment());
                        break;
                    case "NOTES":
                        displayTab(new NotesFragment());
                        break;
                    case "HISTORY":
                        displayTab(new HistoryFragment());
                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.addTab(profileTab);
        tabLayout.addTab(notesTab);
        tabLayout.addTab(HistoryTab);

    }

    public void displayTab(Fragment frag){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, frag);
        fragmentTransaction.commit();
    }
}
