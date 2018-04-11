package com.example.app.jsdc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class Komentar extends Fragment {
    private int page;
    static EditText etPengetahuan;
    static EditText etTeknik;
    static EditText etPerilaku;
    public static Komentar newInstance(int page) {
        Komentar komentar = new Komentar();
        Bundle args = new Bundle();
        args.putInt("1", page);
        komentar.setArguments(args);
        return komentar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_komentar, container, false);
        etPengetahuan = (EditText) view.findViewById(R.id.ET_Pengetahuan);
        etTeknik = (EditText) view.findViewById(R.id.ET_Mengemudi);
        etPerilaku = (EditText) view.findViewById(R.id.ET_Perilaku);
        return view;
    }



    public static String getPengatahuan(){
        String sikap = etPengetahuan.getText().toString();
        return sikap;
    }

    public static String getTeknik(){
        String bahasa = etTeknik.getText().toString();
        return bahasa;
    }

    public static String getPerilaku(){
        String konsen = etPerilaku.getText().toString();
        return konsen;
    }



}