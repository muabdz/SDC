package com.example.app.sdc;


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
import com.example.app.sdc.Utils.ApiUtils;
import com.example.app.sdc.Utils.AuthService;
import com.example.app.sdc.Utils.FragmentUtils.TestFragmentAdapter;
import com.example.app.sdc.Utils.SessionManager;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TestPraktekAll extends AppCompatActivity implements View.OnClickListener {
    Button bSubmit;
    SessionManager sessionManager;
    TestFragmentAdapter testFragmentAdapter;
    int jumlahSoal, nilai;
    String status, message;
    ProgressDialog progressDialog;
    TesPraktek tes_praktek;
    TesSikap tes_sikap;
    AuthService mAuthAPIService;
    EditText[]  etJawab;
    int[] questionId;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_praktek_all);

        sessionManager = new SessionManager(this);
        jumlahSoal = sessionManager.getJumlahSoal();

        tes_praktek = new TesPraktek();
        tes_sikap = new TesSikap();
        etJawab = new EditText[jumlahSoal];
        questionId = new int[jumlahSoal];
        progressDialog = new ProgressDialog(TestPraktekAll.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Mohon Tunggu");

        bSubmit = (Button) findViewById(R.id.bsubmit);
        bSubmit.setOnClickListener(this);
        TextView penguji = (TextView) findViewById(R.id.penguji2);
        TextView peserta = (TextView) findViewById(R.id.peserta);
        TextView kategori = (TextView) findViewById(R.id.jenis_tes);

        peserta.setText(sessionManager.getNama());
        kategori.setText("Roda " + sessionManager.getCate().toString());
        penguji.setText(sessionManager.getUid());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(testFragmentAdapter);


        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bsubmit:
                progressDialog.show();
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
        for (int i = 0; i <= sessionManager.getJumlahSoal(); i++) {
            sessionManager.removeSessionSoal(i, sessionManager.getQuestionId(i));
        }
        sessionManager.removeSessionJumlahSoal();
        progressDialog.dismiss();
        Intent movea = new Intent(TestPraktekAll.this, LoginPeserta.class);
        startActivity(movea);
        finish();
    }

    public void setSubmit() {

        try {

        JSONObject jsonParams = new JSONObject();

        JSONArray soal = new JSONArray();

        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        for (int i = 1; i < sessionManager.getJumlahSoal(); i++) {

            if(TesPraktek.etSoal[i].getText().toString().isEmpty()){
                Toast.makeText(this, "Harap isi semua soal praktek", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                break;
            }else if(Integer.parseInt(TesPraktek.etSoal[i].getText().toString())>100||Integer.parseInt(TesPraktek.etSoal[i].getText().toString())<0|| TesPraktek.etSoal[i].getText().toString().contains("-")){
                Toast.makeText(this, "Masukkan nilai 0 - 100", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                break;
            }

            String jawabanSoal = TesPraktek.etSoal[i].getText().toString();
            int idSoal = TesPraktek.etSoal[i].getId()-300;

            JSONObject jsonJawab = new JSONObject();

            jsonJawab.put("soal_id", sessionManager.getQuestionId(idSoal));
            jsonJawab.put("peserta_id", sessionManager.getPId());
            jsonJawab.put("hasil", Integer.parseInt(jawabanSoal));
            jsonJawab.put("tanggal", date);
            soal.put(jsonJawab);
        }

        jsonParams.put("soal", soal);

        String stringSikap = ((EditText) findViewById(R.id.ET_PerilakuA)).getText().toString();
        String stringBahasa = ((EditText) findViewById(R.id.ET_PerilakuB)).getText().toString();
        String stringKonsen = ((EditText) findViewById(R.id.ET_PerilakuC)).getText().toString();

        if (stringSikap.isEmpty()){
            Toast.makeText(this, "Harap isi nilai sikap", Toast.LENGTH_SHORT).show();
            return;
        }else if (stringBahasa.isEmpty()){
            Toast.makeText(this, "Harap isi nilai bahasa", Toast.LENGTH_SHORT).show();
            return;
        }else if (stringKonsen.isEmpty()){
            Toast.makeText(this, "Harap isi nilai konsen", Toast.LENGTH_SHORT).show();
            return;
        }else if(Integer.parseInt(stringSikap)>100||Integer.parseInt(stringSikap)<0||stringSikap.contains("-")||
                Integer.parseInt(stringBahasa)>100||Integer.parseInt(stringBahasa)<0||stringBahasa.contains("-")||
                Integer.parseInt(stringKonsen)>100||Integer.parseInt(stringKonsen)<0||stringKonsen.contains("-")){
            Toast.makeText(this, "Masukkan nilai 0 - 100", Toast.LENGTH_SHORT).show();
            return;
        }
            if (sessionManager.getIdSikap(1) == 1) {
                JSONObject jsonSikap = new JSONObject();

                jsonSikap.put("soal_id", 1);
                jsonSikap.put("peserta_id", sessionManager.getPId());
                jsonSikap.put("hasil", Integer.parseInt(stringSikap));
                jsonSikap.put("tanggal", date);

                soal.put(jsonSikap);
                JSONObject jsonBahasa = new JSONObject();

                jsonBahasa.put("soal_id", 3);
                jsonBahasa.put("peserta_id", sessionManager.getPId());
                jsonBahasa.put("hasil", Integer.parseInt(stringBahasa));
                jsonBahasa.put("tanggal", date);

                soal.put(jsonBahasa);
                JSONObject jsonKonsen = new JSONObject();

                jsonKonsen.put("soal_id", 2);
                jsonKonsen.put("peserta_id", sessionManager.getPId());
                jsonKonsen.put("hasil", Integer.parseInt(stringKonsen));
                jsonKonsen.put("tanggal", date);

                soal.put(jsonKonsen);
            } else if (sessionManager.getIdSikap(11) == 11) {
                JSONObject jsonSikap = new JSONObject();

                jsonSikap.put("soal_id", 4);
                jsonSikap.put("peserta_id", sessionManager.getPId());
                jsonSikap.put("hasil", Integer.parseInt(stringSikap));
                jsonSikap.put("tanggal", date);

                soal.put(jsonSikap);
                JSONObject jsonBahasa = new JSONObject();

                jsonBahasa.put("soal_id", 6);
                jsonBahasa.put("peserta_id", sessionManager.getPId());
                jsonBahasa.put("hasil", Integer.parseInt(stringBahasa));
                jsonBahasa.put("tanggal", date);

                soal.put(jsonBahasa);
                JSONObject jsonKonsen = new JSONObject();

                jsonKonsen.put("soal_id", 5);
                jsonKonsen.put("peserta_id", sessionManager.getPId());
                jsonKonsen.put("hasil", Integer.parseInt(stringKonsen));
                jsonKonsen.put("tanggal", date);

                soal.put(jsonKonsen);
            }

            mAuthAPIService = new ApiUtils().getAuthAPIService();

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
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
                            nilai = jsonObject.getInt("nilai");

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
                                    sessionManager.removeSessionSikap(1);
                                    sessionManager.removeSessionSikap(2);
                                    sessionManager.removeSessionSikap(3);
                                    sessionManager.removeSessionSikap(4);
                                    sessionManager.removeSessionSikap(5);
                                    sessionManager.removeSessionSikap(6);
                                    Toast.makeText(TestPraktekAll.this, message,
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    String hasil;
                                    if (nilai>=70){
                                        hasil ="LULUS";
                                    }else{
                                        hasil ="GAGAL";
                                    }
                                    new AlertDialog.Builder(TestPraktekAll.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Hasil")
                                            .setMessage("Peserta dinyatakan "+hasil+" ujian ini dengan nilai "+nilai)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent movea = new Intent(TestPraktekAll.this, LoginPeserta.class);
                                                    startActivity(movea);
                                                    finish();
                                                }

                                            })
                                            .setCancelable(false)
                                            .show();
                                }
                            }.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(TestPraktekAll.this, "Submit Gagal",
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                    else {
                            Toast.makeText(TestPraktekAll.this, "Submit Gagal",
                                Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(TestPraktekAll.this, "Submit Gagal",
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }


    }

}
