package com.example.app.jsdc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.jsdc.Utils.ApiUtils;
import com.example.app.jsdc.Utils.AuthService;
import com.example.app.jsdc.Utils.SessionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginKodePetugas extends AppCompatActivity implements View.OnClickListener{
    String username, message, ipValue, portValue, uid, time;
    boolean status;
    private static Context context;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    SessionManager sessionManager, sm;
    EditText etKodePetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kode_petugas);
        progressDialog = new ProgressDialog(LoginKodePetugas.this);
        progressDialog.setMessage("Mohon Tunggu");
        Button bMasukPetugas = (Button) findViewById(R.id.b_MasukKodePetugas);
        bMasukPetugas.setOnClickListener(this);
        etKodePetugas = (EditText) findViewById(R.id.NoKodePetugas);
    }

    public void loginHandler(String kodePenguji) {
        time = kodePenguji.substring(0,10);
        uid = kodePenguji.substring(10);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("date", time);
        jsonParams.put("username", uid);
        mAuthAPIService = new ApiUtils().getAuthAPIService();
        sessionManager = new SessionManager(this);

        Call<ResponseBody> response = mAuthAPIService.testloginPost(uid, time);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.isSuccessful()) {
                    try {

                        JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                        status = jsonObject.getBoolean("status");
                        message = jsonObject.getString("message");
                        if (!status){
                            Toast.makeText(LoginKodePetugas.this, message,
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else {
                            username = jsonObject.getString("username");


                            new CountDownTimer(1000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    // You don't need anything here
                                }

                                public void onFinish() {
                                    sessionManager.setUid(username);
                                    Toast.makeText(LoginKodePetugas.this, message,
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Intent movea = new Intent(LoginKodePetugas.this, LoginPenguji.class);
                                    startActivity(movea);
                                    finish();
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginKodePetugas.this, "Kode Penguji Salah",
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                loginHandler(result.getContents());
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.b_MasukKodePetugas:
                if (etKodePetugas.getText().toString().length()==0){
                    etKodePetugas.setError("Kode Masih Kosong");
                    //Toast.makeText(this, "Kode Petugas Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            else {
                    progressDialog.show();
                    loginHandler(etKodePetugas.getText().toString());
                    break;
                }
        }
    }
}
