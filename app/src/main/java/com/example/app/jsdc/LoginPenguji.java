package com.example.app.jsdc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPenguji extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_penguji);
        Button b_scanPeserta = (Button) findViewById(R.id.b_scanPeserta);

        b_scanPeserta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_scanPeserta:
                Intent movea = new Intent(this, TestPeserta.class);
                startActivity(movea);
                break;
        }
    }
}
