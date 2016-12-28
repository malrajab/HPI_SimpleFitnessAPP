package com.example.m_alrajab.hpi_simplefitnessapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.m_alrajab.hpi_simplefitnessapp.utils.UserLocalDetails;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NotesFragment extends Fragment {
    UserLocalDetails userdetails;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
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
        View noteView = inflater.inflate(R.layout.fragment_notes, container, false);
        final EditText nEdit = (EditText) noteView.findViewById(R.id.notesEdit);
        Button btnAdd = (Button) noteView.findViewById(R.id.btnAddNote);
        ListView list = (ListView) noteView.findViewById(R.id.noteList);
        userdetails = new UserLocalDetails(getContext());
        userdetails.storeHistory(getActivity(), "Notes page");
        final ArrayList<String> arrayList = new ArrayList<String>();

        final String fName = userdetails.getLoggedInUser().notes;
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(noteView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = nEdit.getText().toString();
                try {
                    FileOutputStream fout = getActivity().openFileOutput(fName, Context.MODE_APPEND);
                    fout.write((data + "\n").getBytes());
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                userdetails.storeHistory(getActivity(), "Added New Note");
                arrayList.add(data);
                adapter.notifyDataSetChanged();
            }
        });
        Button clearBtn = (Button) noteView.findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                v.getContext().deleteFile(userdetails.getLoggedInUser().notes);
            }
        });

        return noteView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
