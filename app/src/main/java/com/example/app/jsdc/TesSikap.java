package com.example.app.jsdc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TesSikap extends Fragment {
    // Store instance variables
    private int page;


    EditText etSikap;
    EditText etBahasa;
    EditText etKonsentrasi;

    // newInstance constructor for creating fragment with arguments
    public static TesSikap newInstance(int page) {
        TesSikap tesSikap = new TesSikap();
        Bundle args = new Bundle();
        args.putInt("1", page);
        tesSikap.setArguments(args);
        return tesSikap;
    }

        // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tes_sikap, container, false);
        etSikap = (EditText) view.findViewById(R.id.ET_PerilakuA);
        etBahasa = (EditText) view.findViewById(R.id.ET_PerilakuB);
        etKonsentrasi = (EditText) view.findViewById(R.id.ET_PerilakuC);
        return view;

    }
}