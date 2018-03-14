package com.example.app.jsdc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class LoginPenguji extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_penguji);
        final Activity activity = this;
        Button T_scanpeserta = (Button) findViewById(R.id.b_scanPeserta);
        TextView b_loginkode = (TextView) findViewById(R.id.Bantuan);
        TextView logout = (TextView) findViewById(R.id.keluar);

        b_loginkode.setOnClickListener(this);
        logout.setOnClickListener(this);

        T_scanpeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(this, ScanQR.class));
                finish();
                return true;
            case R.id.menu_histori:
                Toast.makeText(this, "History Sedang Dibuat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "GAGAL", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            Intent movea = new Intent(this, TestPeserta.class);
            startActivity(movea);

        } else {


            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Bantuan:
                Intent kodePendaftaran = new Intent(this, Login_kode.class);
                startActivity(kodePendaftaran);
                break;
            case R.id.keluar:
                Intent logout = new Intent(this, ScanQR.class);
                startActivity(logout);
                break;
        }

    }
}
