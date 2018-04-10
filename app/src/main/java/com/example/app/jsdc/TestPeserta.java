package com.example.app.jsdc;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TestPeserta extends AppCompatActivity implements View.OnClickListener {
    Button bSubmit;
    SessionManager sessionManager;
    TestFragmentAdapter testFragmentAdapter;
    int jumlahSoal;
    String status, message;
    ProgressDialog progressDialog;
    Tes_Praktek tes_praktek;
    Tes_Sikap tes_sikap;
    Komentar komentar;
    AuthService mAuthAPIService;
    EditText[]  etJawab;
    int[] questionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_peserta);

        sessionManager = new SessionManager(this);
        jumlahSoal = sessionManager.getJumlahSoal();

        tes_praktek = new Tes_Praktek();
        tes_sikap = new Tes_Sikap();
        komentar = new Komentar();
        etJawab = new EditText[jumlahSoal];
        questionId = new int[jumlahSoal];
        progressDialog = new ProgressDialog(TestPeserta.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Mohon Tunggu");

        bSubmit = (Button) findViewById(R.id.bsubmit);
        bSubmit.setOnClickListener(this);
        TextView penguji = (TextView) findViewById(R.id.penguji2);
        TextView peserta = (TextView) findViewById(R.id.peserta);
        TextView kategori = (TextView) findViewById(R.id.jenis_tes);

        peserta.setText(sessionManager.getNama());
        kategori.setText(sessionManager.getCate().toString());
        penguji.setText(sessionManager.getUid());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(testFragmentAdapter);


        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.actionbar_menu, menu);
//        return true;
//    }


        switch (v.getId()) {
            case R.id.bsubmit:
                if (tes_sikap.toString().length()==0){
                Toast.makeText(TestPeserta.this, "Submit Gagal",
                        Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
                else {
                setSubmit();}
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
        for (int i = 0; i <= sessionManager.getJumlahSoal(); i++) {
            sessionManager.removeSessionSoal(i, sessionManager.getQuestionId(i));
        }
        sessionManager.removeSessionJumlahSoal();
        progressDialog.dismiss();
        Intent movea = new Intent(TestPeserta.this, LoginPenguji.class);
        startActivity(movea);
        finish();
    }

    public void setSubmit() {

        try {

        JSONObject jsonParams = new JSONObject();

        JSONArray soal = new JSONArray();

        for (int i = 1; i < sessionManager.getJumlahSoal(); i++) {

            if(Tes_Praktek.etSoal[i].getText().toString().isEmpty()){
                Toast.makeText(this, "Harap isi semua soal praktek", Toast.LENGTH_SHORT).show();
                return;
            }

            String jawabanSoal = Tes_Praktek.etSoal[i].getText().toString();
            int idSoal = Tes_Praktek.etSoal[i].getId();

            JSONObject jsonJawab = new JSONObject();

            jsonJawab.put("soal_id", idSoal);
            jsonJawab.put("peserta_id", sessionManager.getPId());
            jsonJawab.put("hasil", Integer.parseInt(jawabanSoal));
            soal.put(jsonJawab);
        }

        jsonParams.put("soal", soal);

        final String stringSikap = ((EditText) findViewById(R.id.ET_PerilakuA)).getText().toString();
        String stringBahasa = ((EditText) findViewById(R.id.ET_PerilakuB)).getText().toString();
        String stringKonsen = ((EditText) findViewById(R.id.ET_PerilakuC)).getText().toString();

        String stringPengetahuan = Komentar.getPengatahuan();
        String stringTeknik = Komentar.getTeknik();
        String stringPerilaku = Komentar.getPerilaku();

        if (stringSikap.isEmpty()){
            Toast.makeText(this, "Harap isi nilai sikap", Toast.LENGTH_SHORT).show();
            return;
        }else if (stringBahasa.isEmpty()){
            Toast.makeText(this, "Harap isi nilai bahasa", Toast.LENGTH_SHORT).show();
            return;
        }else if (stringKonsen.isEmpty()){
            Toast.makeText(this, "Harap isi nilai konsen", Toast.LENGTH_SHORT).show();
            return;
        }





            if (sessionManager.getIdSikap(67) == 67) {
                JSONObject jsonSikap = new JSONObject();

                jsonSikap.put("soal_id", sessionManager.getIdSikap(67));
                jsonSikap.put("peserta_id", sessionManager.getPId());
                jsonSikap.put("hasil", Integer.parseInt(stringSikap));

                soal.put(jsonSikap);
                JSONObject jsonBahasa = new JSONObject();

                jsonBahasa.put("soal_id", sessionManager.getIdSikap(68));
                jsonBahasa.put("peserta_id", sessionManager.getPId());
                jsonBahasa.put("hasil", Integer.parseInt(stringBahasa));

                soal.put(jsonBahasa);
                JSONObject jsonKonsen = new JSONObject();

                jsonKonsen.put("soal_id", sessionManager.getIdSikap(69));
                jsonKonsen.put("peserta_id", sessionManager.getPId());
                jsonKonsen.put("hasil", Integer.parseInt(stringKonsen));

                soal.put(jsonKonsen);
            } else if (sessionManager.getIdSikap(16) == 16) {
                JSONObject jsonSikap = new JSONObject();

                jsonSikap.put("soal_id", sessionManager.getIdSikap(16));
                jsonSikap.put("peserta_id", sessionManager.getPId());
                jsonSikap.put("hasil", Integer.parseInt(stringSikap));

                soal.put(jsonSikap);
                JSONObject jsonBahasa = new JSONObject();

                jsonBahasa.put("soal_id", sessionManager.getIdSikap(17));
                jsonBahasa.put("peserta_id", sessionManager.getPId());
                jsonBahasa.put("hasil", Integer.parseInt(stringBahasa));

                soal.put(jsonBahasa);
                JSONObject jsonKonsen = new JSONObject();

                jsonKonsen.put("soal_id", sessionManager.getIdSikap(18));
                jsonKonsen.put("peserta_id", sessionManager.getPId());
                jsonKonsen.put("hasil", Integer.parseInt(stringKonsen));

                soal.put(jsonKonsen);
            }


            JSONObject jsonKomen = new JSONObject();

            jsonKomen.put("peserta_id", sessionManager.getPId());
            jsonKomen.put("pengetahuan", stringPengetahuan);
            jsonKomen.put("teknik", stringTeknik);
            jsonKomen.put("perilaku", stringPerilaku);

            JSONArray comments = new JSONArray();
            comments.put(jsonKomen);

            jsonParams.put("comments", comments);

            mAuthAPIService = ApiUtils.getAuthAPIService();

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset = utf-8"),
                    (jsonParams).toString());

            Call<ResponseBody> response = mAuthAPIService.submitPeserta(body);

            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                    if (rawResponse.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                            message = jsonObject.getString("message");
                            status = jsonObject.getString("status");

                            new CountDownTimer(1000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    // You don't need anything here
                                }

                                public void onFinish() {
                                    for (int i = 0; i <= sessionManager.getJumlahSoal(); i++) {
                                        sessionManager.removeSessionSoal(i, sessionManager.getQuestionId(i));
                                    }
                                    sessionManager.removeSessionJumlahSoal();
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
                    }
                    else {
                            Toast.makeText(TestPeserta.this, "Submit Gagal",
                                Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
