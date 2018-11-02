package com.example.app.sdc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.sdc.Utils.ApiUtils;
import com.example.app.sdc.Utils.AuthService;
import com.example.app.sdc.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginPesertaKode extends AppCompatActivity implements View.OnClickListener {
    String  message, p_id, nama;
    Boolean status;
    int cate;
    EditText etPeserta;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_peserta_kode);
        Button masukpeserta = (Button) findViewById(R.id.b_loginPeserta);
        etPeserta = (EditText) findViewById(R.id.No_daftar);
        sessionManager = new SessionManager(this);
        TextView penguji = (TextView) findViewById(R.id.pengujiKode);
        penguji.setText(sessionManager.getUid());

        progressDialog = new ProgressDialog(LoginPesertaKode.this);
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.setCanceledOnTouchOutside(false);

        masukpeserta.setOnClickListener(this);
    }

    public void loginHandler(String kodePeserta) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", kodePeserta);

        mAuthAPIService = new ApiUtils().getAuthAPIService();

        Call<ResponseBody> response = mAuthAPIService.loginPeserta(kodePeserta);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.isSuccessful()) {
                    try {

                        JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                        message = jsonObject.getString("message");
                        status = jsonObject.getBoolean("status");
                        if (!status){
                            Toast.makeText(LoginPesertaKode.this, message,
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else {
                            JSONArray jsonSoal = jsonObject.getJSONArray("soal");
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            p_id = jsonData.getString("p_id");
                            nama = jsonData.getString("nama");
                            cate = jsonData.getInt("cate");
//                            Date c = Calendar.getInstance().getTime();
//                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//                            String testTime = df.format(c);
//                            sessionManager.setStartTime(testTime);
                            sessionManager.setData(p_id, nama, cate);


                            int jumsol = 1;
                            for (int i = 0; i < jsonSoal.length(); i++) {
                                jsonObject = jsonSoal.getJSONObject(i);
                                int kategori = jsonObject.getInt("kategori");
                                int id = jsonObject.getInt("id");
                                String soal = jsonObject.getString("soal");
                                if (id < 7) {
                                    sessionManager.setSessionSikap(soal, id);
                                } else {
                                    jumsol++;
                                    sessionManager.setQuestion(i, jumsol, soal, kategori, id);
                                }
                                sessionManager.setJumlahTotal(jsonSoal.length());
                            }


                            new CountDownTimer(1000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    // You don't need anything here
                                }

                                public void onFinish() {
                                    Toast.makeText(LoginPesertaKode.this, message,
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Intent login = new Intent(LoginPesertaKode.this, TestPraktekAll.class);
                                    startActivity(login);
                                    finish();

                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginPesertaKode.this, "Nomor Pendaftaran salah",
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_loginPeserta:
                if(etPeserta.getText().toString().length()==0){
                    etPeserta.setError("Masukan Nomor Pendaftaran");
                }
                else {
                    progressDialog.show();
                    loginHandler(etPeserta.getText().toString());
                    break;
                }

        }
    }
}
