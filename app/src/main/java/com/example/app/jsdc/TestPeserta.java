package com.example.app.jsdc;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        t_praktek.setOnClickListener(this);
        t_sikap.setOnClickListener(this);
        komen.setOnClickListener(this);
        selesai.setOnClickListener(this);

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
            case R.id.b_selesai:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setTitle("Keluar");
//                        builder.setMessage("Apakah Anda Yakin ?");
//                        builder.setNegativeButton("Tidak", null);
//                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                    Intent keluar = new Intent(this, LoginPenguji.class);
//                                    startActivity(keluar);
//
//     Masih ERROR
//                            }
//                        });

                Intent selesai = new Intent(this, LoginPenguji.class);
                startActivity(selesai);
                break;
        }

        transaction.replace(R.id.fragment_tes, FG);
        transaction.commit();
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

                Intent keluar = new Intent(this, ScanQR.class);
                startActivity(keluar);

//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setTitle("Keluar");
//                        builder.setMessage("Apakah Anda Yakin ?");
//                        builder.setNegativeButton("Tidak", null);
//                        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which, Intent keluar) {
//
//                                Intent keluar = new Intent(this, ScanQR.class);
//                                startActivity(keluar);
//
//                            }
//                        });

            case R.id.menu_histori:
                Toast.makeText(this, "History Sedang Dibuat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
