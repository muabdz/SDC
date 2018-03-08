package com.example.app.jsdc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login_kode extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kode);
        Button masukpeserta = (Button) findViewById(R.id.b_loginPeserta);
        TextView logout = (TextView) findViewById(R.id.keluar);


        masukpeserta.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_loginPeserta:
                Intent login = new Intent(this, TestPeserta.class);
                startActivity(login);
                break;

            case R.id.keluar:
                Intent logout = new Intent(this, ScanQR.class);
                startActivity(logout);
                break;
        }
    }
}
