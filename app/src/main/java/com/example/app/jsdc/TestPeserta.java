package com.example.app.jsdc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener {
    Button t_praktek, t_sikap, komen, selesai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peserta);

        t_praktek = (Button) findViewById(R.id.b_praktek);
        t_sikap = (Button) findViewById(R.id.b_sikap);
        komen = (Button) findViewById(R.id.b_komentar);
        selesai = (Button) findViewById(R.id.b_selesai);
        TextView logout = (TextView) findViewById(R.id.keluar);

        t_praktek.setOnClickListener(this);
        t_sikap.setOnClickListener(this);
        komen.setOnClickListener(this);
        selesai.setOnClickListener(this);
        logout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        android.support.v4.app.Fragment FG ;
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        if (v == findViewById(R.id.b_praktek))
            FG = new Tes_Praktek();

        else if (v == findViewById(R.id.b_sikap))
            FG = new Tes_Sikap();
        else
            FG = new Komentar();


        switch (v.getId()) {
            case R.id.keluar:
                Intent logout = new Intent(this, ScanQR.class);
                startActivity(logout);
                break;
            case R.id.b_selesai:
                Intent selesai = new Intent(this, LoginPenguji.class);
                startActivity(selesai);
                break;
        }

        transaction.replace(R.id.fragment_tes, FG);
        transaction.commit();
    }
}
