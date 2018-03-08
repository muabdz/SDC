package com.example.app.jsdc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener {
    Button t_praktek, t_sikap, komen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peserta);

        t_praktek = (Button) findViewById(R.id.b_praktek);
        t_sikap = (Button) findViewById(R.id.b_sikap);
        komen = (Button) findViewById(R.id.b_komentar);

        t_praktek.setOnClickListener(this);
        t_sikap.setOnClickListener(this);
        komen.setOnClickListener(this);

    }

//    public void selectfrag(View view) {
//        android.support.v4.app.Fragment FG;
//
//        if (view == findViewById(R.id.b_praktek))
//            FG = new Tes_Praktek();
//
//        if (view == findViewById(R.id.b_sikap))
//            FG = new Tes_Sikap();
//        else
//            FG = new Komentar();
//
//        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.fragment_tes, FG);
//        transaction.commit();
//    }

    @Override
    public void onClick(View v) {
        android.support.v4.app.Fragment FG = null;
        switch (v.getId()) {
            case R.id.b_praktek:
                FG = new Tes_Praktek();
                break;
        }

        switch (v.getId()) {
            case R.id.b_sikap:
                FG = new Tes_Sikap();
                break;
        }

        switch (v.getId()) {
            case R.id.b_komentar:
                FG = new Komentar();
                break;
        }

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_tes, FG);
        transaction.commit();
    }
}
