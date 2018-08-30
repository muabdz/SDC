package com.example.app.sdc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class TesKomentar extends Fragment {
    private int page;
    static EditText etPengetahuan;
    static EditText etTeknik;
    static EditText etPerilaku;

    public static TesKomentar newInstance(int page) {
        TesKomentar tesKomentar = new TesKomentar();
        Bundle args = new Bundle();
        args.putInt("1", page);
        tesKomentar.setArguments(args);
        return tesKomentar;
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

    public static String getPengatahuan() {
        try {
            String sikap = etPengetahuan.getText().toString();
            return sikap;
        } catch (NullPointerException ep) {
            String strKosong = "kosong";
            return strKosong;
        }
    }

    public static String getTeknik() {
        try {
            String bahasa = etTeknik.getText().toString();
            return bahasa;
        } catch (NullPointerException ep) {
            String strKosong = "kosong";
            return strKosong;
        }
    }

    public static String getPerilaku() {
        try {
            String konsen = etPerilaku.getText().toString();
            return konsen;
        } catch (NullPointerException ep) {
            String strKosong = "kosong";
            return strKosong;
        }
    }


}