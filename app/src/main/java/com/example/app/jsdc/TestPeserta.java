package com.example.app.jsdc;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.app.jsdc.Utils.FragmentUtils.TestFragmentAdapter;
import com.example.app.jsdc.Utils.SessionManager;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener {
    Button t_praktek, t_sikap, komen, selesai;
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    TestFragmentAdapter testFragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peserta);
        sessionManager = new SessionManager(this);
        TextView penguji = (TextView) findViewById(R.id.penguji2);
        penguji.setText(sessionManager.getUid());
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(testFragmentAdapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        /*t_praktek = (Button) findViewById(R.id.b_praktek);
        t_sikap = (Button) findViewById(R.id.b_sikap);
        komen = (Button) findViewById(R.id.b_komentar);
        selesai = (Button) findViewById(R.id.b_selesai);

        t_praktek.setOnClickListener(this);
        t_sikap.setOnClickListener(this);
        komen.setOnClickListener(this);
        selesai.setOnClickListener(this);*/

    }


    @Override
    public void onClick(View v) {
        /*android.support.v4.app.Fragment FG ;
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
                AlertDialog.Builder keluar = new AlertDialog.Builder(this);
                keluar.setMessage("Apakah Anda Yakin?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new AlertDialog.OnClickListener(){
                public void onClick(DialogInterface dialog, int arg1){
                Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(exit);
            }
        }).setNegativeButton("Tidak", new AlertDialog.OnClickListener(){
            public void onClick(DialogInterface arg, int arg1){
                arg.cancel();
            }
        });
        AlertDialog arg1 = keluar.create();
        arg1.setTitle("Selesai");
        arg1.show();
        break;
    }

        transaction.replace(R.id.fragment_tes, FG);
        transaction.commit();*/
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
<<<<<<< HEAD
            case R.id.configIP:

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

=======
            case R.id.menu_logout:
              /*  new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Logout")
                        .setMessage("Apakah anda yakin ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                logoutHandler();
                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();*/
                return true;
>>>>>>> 1d9e37eac4342d1990ea6217360b286f95e7de8f
            case R.id.menu_histori:
                Toast.makeText(this, "History Sedang Dibuat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed(){
        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Tekan tombol kembali lagi untuk keluar",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                doubleBackToExitPressedOnce = false;
            }
        },2000);
    }
}
