package com.example.app.jsdc;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.jsdc.Utils.ApiUtils;
import com.example.app.jsdc.Utils.AuthService;
import com.example.app.jsdc.Utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Login_kode extends AppCompatActivity implements View.OnClickListener {
    String username, status, message;
    EditText etPeserta;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kode);
        Button masukpeserta = (Button) findViewById(R.id.b_loginPeserta);
        etPeserta = (EditText) findViewById(R.id.No_daftar);
        sessionManager = new SessionManager(this);
        TextView penguji = (TextView) findViewById(R.id.pengujiKode);
        penguji.setText(sessionManager.getUid());

        progressDialog = new ProgressDialog(Login_kode.this);
        progressDialog.setMessage("Mohon Tunggu");

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
                        JSONArray jsonSoal = jsonObject.getJSONArray("soal");
                        JSONArray jsonData = jsonObject.getJSONArray("data");
                        String p_id = jsonData.getString(0);
                        String nama = jsonData.getString(1);
                        int cate = jsonData.getInt(2);
                        sessionManager.setData(p_id, nama, cate);

                        message = jsonObject.getString("message");
                        status = jsonObject.getString("status");

                        for (int i = 0; i<jsonSoal.length(); i++){
                            jsonObject = jsonSoal.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            int nomor = jsonObject.getInt("nomor");
                            String soal = jsonObject.getString("soal");
                            sessionManager.setQuestion(id, nomor, soal);
                        }


                        new CountDownTimer(1000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                // You don't need anything here
                            }

                            public void onFinish() {
                                progressDialog.dismiss();
                                Intent login = new Intent(Login_kode.this, TestPeserta.class);
                                startActivity(login);
                                finish();

                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Login_kode.this, "Nomor Pendaftaran salah",
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //Intent login = new Intent(this, TestPeserta.class);
    //startActivity(login);


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_loginPeserta:
                progressDialog.show();
                loginHandler(etPeserta.getText().toString());
                break;

        }
    }
}
