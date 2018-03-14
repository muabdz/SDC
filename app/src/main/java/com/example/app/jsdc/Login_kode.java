package com.example.app.jsdc;

import android.app.ProgressDialog;
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

import org.json.JSONException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kode);
        Button masukpeserta = (Button) findViewById(R.id.b_loginPeserta);
        etPeserta = (EditText) findViewById(R.id.No_daftar);

        progressDialog = new ProgressDialog(Login_kode.this);
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.show();

        masukpeserta.setOnClickListener(this);
    }

    public void loginHandler() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", etPeserta.getText().toString());

        mAuthAPIService = ApiUtils.getAuthAPIService();

        Call<ResponseBody> response = mAuthAPIService.testloginPost(etPeserta.getText().toString());

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.isSuccessful()) {
                    try {

                        JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                        //TODO: Sampe sini...{"message": "Petugas Berhasil Login", "status": true, "username":"STAFF1"}
                        username = jsonObject.getString("username");
                        message = jsonObject.getString("message");
                        status = jsonObject.getString("status");



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
                    Toast.makeText(Login_kode.this, "Password / Username Salah",
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
                loginHandler();
                break;

        }
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
