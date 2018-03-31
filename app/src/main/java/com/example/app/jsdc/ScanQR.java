package com.example.app.jsdc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.jsdc.Utils.ApiUtils;
import com.example.app.jsdc.Utils.AuthService;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ScanQR extends AppCompatActivity {
    String username, status, message;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        final Activity activity = this;
        Button T_scanpenguji = (Button) findViewById(R.id.b_scanpenguji);
        progressDialog = new ProgressDialog(ScanQR.this);
        progressDialog.setMessage("Mohon Tunggu");

        T_scanpenguji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setCaptureActivity(captureActivityPortrait.class);
                integrator.initiateScan();
            }
        });
    }
    public void activityTransition(){
        Intent movea = new Intent(ScanQR.this, LoginPenguji.class);
        startActivity(movea);
    }

    public void loginHandler(String kodePenguji) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", kodePenguji);

        mAuthAPIService = ApiUtils.getAuthAPIService();

        Call<ResponseBody> response = mAuthAPIService.testloginPost(kodePenguji);

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
                                Toast.makeText(ScanQR.this, message,
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                Intent movea = new Intent(ScanQR.this, LoginPenguji.class);
                                startActivity(movea);
                                finish();
                            }
                        }.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ScanQR.this, "Password / Username Salah",
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan_penguji, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.configIP:
                Intent configIp = new Intent(this, ConfigIP.class);
                startActivity(configIp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

