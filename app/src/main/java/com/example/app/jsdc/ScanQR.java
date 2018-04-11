package com.example.app.jsdc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class ScanQR extends AppCompatActivity implements View.OnClickListener {
    String username, message, ipValue, portValue, uid, time;
    boolean status;
    private static Context context;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    SessionManager sessionManager, sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        ScanQR.context = getApplicationContext();
        final Activity activity = this;
        TextView TombolMasukKodePetugas = (TextView) findViewById(R.id.scanGagalPenguji);
        TombolMasukKodePetugas.setOnClickListener(this);
        Button T_scanpenguji = (Button) findViewById(R.id.b_scanpenguji);
        progressDialog = new ProgressDialog(ScanQR.this);
        progressDialog.setCanceledOnTouchOutside(false);
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

    public static Context getAppContext() {
        return ScanQR.context;
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
                            Toast.makeText(ScanQR.this, message,
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
                                    Toast.makeText(ScanQR.this, message,
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Intent movea = new Intent(ScanQR.this, LoginPenguji.class);
                                    startActivity(movea);
                                    finish();
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ScanQR.this, "Kode Penguji Salah",
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ScanQR.this, "Login Gagal",
                        Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

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
                sm = new SessionManager(this);
                ipConfig();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Keluar")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    public void ipConfig(){
        LinearLayout linearLayout = new LinearLayout(ScanQR.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);

        final EditText ConfigIpInput = new EditText(ScanQR.this);
        ConfigIpInput.setHint("Contoh IP: 192.168.100.50");
        String ipNow = sm.getHostIp();
        ConfigIpInput.setText(ipNow);
        linearLayout.addView(ConfigIpInput);

        final EditText ConfigPortInput = new EditText(ScanQR.this);
        ConfigPortInput.setHint("Contoh Port: 7777");
        ConfigPortInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        String portNow = sm.getHostPort();
        ConfigPortInput.setText(portNow);
        linearLayout.addView(ConfigPortInput);

        AlertDialog.Builder info = new AlertDialog.Builder(ScanQR.this);
        info.setMessage("Ubah IP dan Port:").setCancelable(true).setPositiveButton("Simpan", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try{
                    ipValue = ConfigIpInput.getText().toString();
                    portValue = ConfigPortInput.getText().toString();
                    sm.setSession(ipValue, portValue);
                    Toast.makeText(ScanQR.this,
                            "Konfigurasi disimpan\n http://"+ ipValue + ":" + portValue + "/", Toast.LENGTH_SHORT).show();

                }catch (NumberFormatException e){
                    AlertDialog.Builder infoo = new AlertDialog.Builder(ScanQR.this);
                    infoo.setMessage("Masukkan nilai IP dan Port").setCancelable(false).setPositiveButton("Coba Lagi", new AlertDialog.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog1 = infoo.create();
                    dialog1.setTitle("Error");
                    dialog1.show();
                }
            }
        }).setNegativeButton("Batal", new AlertDialog.OnClickListener(){
            public void onClick(DialogInterface dialog2, int id){
                dialog2.cancel();
            }
        });
        AlertDialog dialog = info.create();
        dialog.setView(linearLayout);
        dialog.setTitle("IP Config");
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scanGagalPenguji:
                Intent masukLoginKodePetugas = new Intent(this, LoginKodePetugas.class);
                startActivity(masukLoginKodePetugas);
                break;
        }
    }
}

