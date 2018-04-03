package com.example.app.jsdc;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.app.jsdc.Utils.FragmentUtils.TestFragmentAdapter;
import com.example.app.jsdc.Utils.SelectedFragment;
import com.example.app.jsdc.Utils.SessionManager;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener {
    Button t_praktek, t_sikap, komen, selesai;
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    TestFragmentAdapter testFragmentAdapter;
    SelectedFragment selectedFrag;
    int counter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = 0;
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
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
                switch (position) {
                    case 0:
                        //getDataPraktek();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }


            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {

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

            case R.id.menu_histori:
                Toast.makeText(this, "History Sedang Dibuat", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        cancelHandler();
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    public void cancelHandler() {
        sessionManager.removeSessionPeserta();
        for (int i = 1; i <= sessionManager.getJumlahSoal(); i++) {
            sessionManager.removeSessionSoal(i, sessionManager.getQuestionId(i));
        }
        sessionManager.removeSessionJumlahSoal();
        progressDialog.dismiss();
        Intent movea = new Intent(TestPeserta.this, LoginPenguji.class);
        startActivity(movea);
        finish();
    }
}
