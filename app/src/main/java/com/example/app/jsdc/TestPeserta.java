package com.example.app.jsdc;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener {

    Button peserta,sikap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peserta);
        peserta = (Button) findViewById(R.id.b_praktek);
        sikap = (Button) findViewById(R.id.b_sikap);

        peserta.setOnClickListener(this);
        sikap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_tes, new Tes_Sikap()).commit();
            }
        });

    }

    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tes, new Tes_Praktek()).commit();
    }
}
