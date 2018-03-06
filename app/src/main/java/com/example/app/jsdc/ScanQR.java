package com.example.app.jsdc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScanQR extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        Button b_masuk = (Button) findViewById(R.id.b_masuk);

        b_masuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_masuk:
                Intent movea = new Intent(this, LoginPenguji.class);
                startActivity(movea);
                break;
        }
    }
}
