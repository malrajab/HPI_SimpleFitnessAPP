package com.example.m_alrajab.hpi_simplefitnessapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View hScreen = inflater.inflate(R.layout.fragment_history, container, false);

        ListView list = (ListView) hScreen.findViewById(R.id.historyList);
        final UserLocalDetails userdetails = new UserLocalDetails(getContext());
        userdetails.storeHistory(getActivity(), "Browsing History");
        final ArrayList<String> arrayList = new ArrayList<String>();

        final String fName = userdetails.getLoggedInUser().history;
        try {
            FileInputStream fin = getActivity().openFileInput(fName);
            int c;
            String temp = "";
            while((c=fin.read()) !=-1){
                if(!Character.toString((char)c).equals("\n")){
                    temp = temp + Character.toString((char)c);
                }
                else {
                    arrayList.add(temp);
                    temp = "";
                }
            }
            fin.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(hScreen.getContext(), android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);

        Button clearBtn = (Button) hScreen.findViewById(R.id.btnClearHistory);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                v.getContext().deleteFile(userdetails.getLoggedInUser().history);
            }
        });

        return hScreen;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
