package com.example.app.jsdc;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
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
import com.example.app.jsdc.Utils.ApiUtils;
import com.example.app.jsdc.Utils.AuthService;
import com.example.app.jsdc.Utils.FragmentUtils.TestFragmentAdapter;
import com.example.app.jsdc.Utils.SelectedFragment;
import com.example.app.jsdc.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener{
    Button bSubmit;
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    TestFragmentAdapter testFragmentAdapter;
    int jumlahSoal;
    String status, message;
    ProgressDialog progressDialog;
    Tes_Praktek tes_praktek;
    Tes_Sikap tes_sikap;
    Komentar komentar;
    AuthService mAuthAPIService;
    EditText[] etSoal, etJawab;
    int[] questionId;
//    EditText[] etJawaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tes_praktek = new Tes_Praktek();
        tes_sikap = new Tes_Sikap();
        komentar = new Komentar();
        etJawab = new EditText[jumlahSoal];
        questionId = new int[jumlahSoal];
        progressDialog = new ProgressDialog(TestPeserta.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Mohon Tunggu");
        setContentView(R.layout.activity_test_peserta);
        sessionManager = new SessionManager(this);
        jumlahSoal = sessionManager.getJumlahSoal();
        bSubmit = (Button) findViewById(R.id.bsubmit);
        bSubmit.setOnClickListener(this);
//        etJawaban = new EditText[(sessionManager.getJumlahSoal())];
        TextView penguji = (TextView) findViewById(R.id.penguji2);
        TextView peserta = (TextView) findViewById(R.id.peserta);
        TextView kategori = (TextView) findViewById(R.id.jenis_tes);

        peserta.setText(sessionManager.getNama());
        kategori.setText(sessionManager.getCate().toString());
        penguji.setText(sessionManager.getUid());
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(testFragmentAdapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bsubmit:
                setSubmit();
                break;
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

//    public void setJawab(EditText et, int i){
//        etJawaban[i] = et;
//    }

    public void setSubmit(){
            String stringSikap = ((EditText) findViewById(R.id.ET_PerilakuA)).getText().toString();
            String stringBahasa = ((EditText) findViewById(R.id.ET_PerilakuB)).getText().toString();
            String stringKonsen = ((EditText) findViewById(R.id.ET_PerilakuC)).getText().toString();
            String stringPengetahuan = ((EditText) findViewById(R.id.ET_Pengetahuan)).getText().toString();
            String stringTeknik = ((EditText) findViewById(R.id.ET_Mengemudi)).getText().toString();
            String stringPerilaku = ((EditText) findViewById(R.id.ET_Perilaku)).getText().toString();

//            for (int i=0;i<sessionManager.getJumlahSoal();i++){
//                String stringJawab = (tes_praktek.etSoal[i]).getText().toString();
//            }
//            new AlertDialog.Builder(this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Error")
//                    .setMessage("Semua field wajib diisi")
//                    .setPositiveButton("OK", null)
//                    .show();

        //tes_praktek.arrayJawaban(getBaseContext());
        //Map<String, Object> jsonParams = new ArrayMap<>();
        JSONObject jsonParams = new JSONObject();
        JSONObject jsonKomen = new JSONObject();
        try {
            jsonKomen.put("peserta_id", sessionManager.getPId());
            jsonKomen.put("pengetahuan", stringPengetahuan);
            jsonKomen.put("teknik", stringTeknik);
            jsonKomen.put("perilaku", stringPerilaku);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray comments = new JSONArray();
        comments.put(jsonKomen);
        try {
            jsonParams.put("comments", comments);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JSONArray soal = new JSONArray();
        if (sessionManager.getIdSikap(67)==67){
            JSONObject jsonSikap = new JSONObject();
            try {
                jsonSikap.put("soal_id", sessionManager.getIdSikap(67));
                jsonSikap.put("peserta_id", sessionManager.getPId());
                jsonSikap.put("hasil", stringSikap);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            soal.put(jsonSikap);
            JSONObject jsonBahasa = new JSONObject();
            try {
                jsonBahasa.put("soal_id", sessionManager.getIdSikap(68));
                jsonBahasa.put("peserta_id", sessionManager.getPId());
                jsonBahasa.put("hasil", stringBahasa);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            soal.put(jsonBahasa);
            JSONObject jsonKonsen = new JSONObject();
            try {
                jsonKonsen.put("soal_id", sessionManager.getIdSikap(69));
                jsonKonsen.put("peserta_id", sessionManager.getPId());
                jsonKonsen.put("hasil", stringKonsen);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            soal.put(jsonKonsen);
        }else if (sessionManager.getIdSikap(16)==16){
            JSONObject jsonSikap = new JSONObject();
            try {
                jsonSikap.put("soal_id", sessionManager.getIdSikap(16));
                jsonSikap.put("peserta_id", sessionManager.getPId());
                jsonSikap.put("hasil", stringSikap);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            soal.put(jsonSikap);
            JSONObject jsonBahasa = new JSONObject();
            try {
                jsonBahasa.put("soal_id", sessionManager.getIdSikap(17));
                jsonBahasa.put("peserta_id", sessionManager.getPId());
                jsonBahasa.put("hasil", stringBahasa);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            soal.put(jsonBahasa);
            JSONObject jsonKonsen = new JSONObject();
            try {
                jsonKonsen.put("soal_id", sessionManager.getIdSikap(18));
                jsonKonsen.put("peserta_id", sessionManager.getPId());
                jsonKonsen.put("hasil", stringKonsen);
            }catch (JSONException e) {
                e.printStackTrace();
            }
            soal.put(jsonKonsen);
        }
        try {
            jsonParams.put("soal",soal);
        }catch (JSONException e){
            e.printStackTrace();
        }
//        for (int i=0;i<sessionManager.getJumlahSoal();i++){
//            String stringJawab = (getEtJawab(i)).getText().toString();
//            int intId = getQuestId(i);
//
//            JSONObject jsonJawab= new JSONObject();
//            try {
//                jsonJawab.put("soal_id", intId);
//                jsonJawab.put("peserta_id", sessionManager.getPId());
//                jsonJawab.put("hasil", stringJawab);
//                jsonParams.put("soal",jsonJawab);
//            }catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        mAuthAPIService = ApiUtils.getAuthAPIService();

        Call<ResponseBody> response = mAuthAPIService.submitPeserta(jsonParams);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                        message = jsonObject.getString("message");
                        status = jsonObject.getString("status");
                        //TAMBAHIN REMOVE SESI

                        new CountDownTimer(1000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                // You don't need anything here
                            }

                            public void onFinish() {
                                sessionManager.removeSessionPeserta();
                                sessionManager.removeSessionSikap(67);
                                sessionManager.removeSessionSikap(68);
                                sessionManager.removeSessionSikap(69);
                                sessionManager.removeSessionSikap(17);
                                sessionManager.removeSessionSikap(18);
                                sessionManager.removeSessionSikap(19);
                                Toast.makeText(TestPeserta.this, message,
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                Intent movea = new Intent(TestPeserta.this, LoginPenguji.class);
                                startActivity(movea);
                                finish();
                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(TestPeserta.this, "Submit Gagal",
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void setEtJawab(EditText et, int i){
        etJawab[i] = new EditText(getParent());
        etJawab[i] = et;
    }

    public void setQuestionId(int i, int id){
        questionId[i] = new Integer(id);
        questionId[i] = id;
    }

    public  int getQuestId(int i){
        int id = questionId[i];
        return id;
    }

    public EditText getEtJawab(int i){
        EditText et = etJawab[i];
        return et;
    }


//    @Override
//    public void onEditTextSikapChanged(String stringSikap, String stringBahasa, String stringKonsentrasi) {
//
//    }
}
