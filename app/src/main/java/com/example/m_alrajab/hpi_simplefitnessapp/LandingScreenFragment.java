package com.example.m_alrajab.hpi_simplefitnessapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.m_alrajab.hpi_simplefitnessapp.model.DBHelper;
import com.example.m_alrajab.hpi_simplefitnessapp.model.DbContract.FeedEntry;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;


public class LandingScreenFragment extends Fragment {

    UserLocalDetails userdetails;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LandingScreenFragment() {
        // Required empty public constructor
    }

    public static LandingScreenFragment newInstance(String param1, String param2) {
        LandingScreenFragment fragment = new LandingScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lScreenView = inflater.inflate(R.layout.fragment_landing_screen, container, false);
        DBHelper database = new DBHelper(getActivity());

        //"userdetails" is the name for the local preference
        userdetails = new UserLocalDetails(getActivity());
        userdetails.storeHistory(getActivity(), "Profile page");
        Cursor cur = database.getRecForUsername(userdetails.getLoggedInUser().username);
        cur.moveToFirst();

        TextView uName = (TextView) lScreenView.findViewById(R.id.landing_fname);


        //Set the values in landing page fields by fetching from shared preference
        uName.setText(cur.getString(cur.getColumnIndex(FeedEntry.COL_USERNAME)));

        Button logout = (Button) lScreenView.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            //Logout will clear the local preference and re-direct to MainActivity
            @Override
            public void onClick(View v) {
                userdetails.storeHistory(getActivity(), "Logged out");
                userdetails.clearUserData();
                userdetails.setUserLoggedIn(false);
                Intent loginPage = new Intent(LandingScreenFragment.this.getActivity(), MainActivity.class);
                loginPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginPage);
            }
        });

        return lScreenView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
