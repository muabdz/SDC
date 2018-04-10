package com.example.app.jsdc;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;


public class Tes_Sikap extends Fragment {
    //private OnDataSikapSelected fCallback = null;

    // Store instance variables
    private int page;


    EditText etSikap;
    EditText etBahasa;
    EditText etKonsentrasi;

    // newInstance constructor for creating fragment with arguments
    public static Tes_Sikap newInstance(int page) {
        Tes_Sikap tesSikap = new Tes_Sikap();
        Bundle args = new Bundle();
        args.putInt("1", page);
        tesSikap.setArguments(args);
        return tesSikap;
    }

//    public interface OnDataSikapSelected {
//        public void onEditTextSikapChanged(String stringSikap, String stringBahasa, String stringKonsentrasi);
//    }

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
        final View view = inflater.inflate(R.layout.fragment_tes__sikap, container, false);
        etSikap = (EditText) view.findViewById(R.id.ET_PerilakuA);
        etBahasa = (EditText) view.findViewById(R.id.ET_PerilakuB);
        etKonsentrasi = (EditText) view.findViewById(R.id.ET_PerilakuC);
        return view;

    }

    public String getSikap(){
        String sikap = etSikap.getText().toString();
        return sikap;
    }

    public String getBahasa(){
        String bahasa = etBahasa.getText().toString();
        return bahasa;
    }

    public String getKonsen(){
        String konsen = etKonsentrasi.getText().toString();
        return konsen;
    }
}