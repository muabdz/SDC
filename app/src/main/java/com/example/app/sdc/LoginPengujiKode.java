package com.example.app.sdc;

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
import android.widget.Toast;

import com.example.app.sdc.Utils.ApiUtils;
import com.example.app.sdc.Utils.AuthService;
import com.example.app.sdc.Utils.SessionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginPengujiKode extends AppCompatActivity implements View.OnClickListener {
    String username, message, ipValue, portValue;

    boolean status;
    private static Context context;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    EditText etKodePetugas, etPassPetugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_penguji_kode);
        LoginPengujiKode.context = getApplicationContext();
        progressDialog = new ProgressDialog(LoginPengujiKode.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Mohon Tunggu");
        Button bMasukPetugas = (Button) findViewById(R.id.b_MasukKodePetugas);
        bMasukPetugas.setOnClickListener(this);
        etKodePetugas = (EditText) findViewById(R.id.NoKodePetugas);
        etPassPetugas = (EditText) findViewById(R.id.passwordPetugas);
    }

    public static Context getAppContext() {
        return LoginPengujiKode.context;
    }

    public void loginHandler(String usernamePenguji, String passPenguji) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", usernamePenguji);
            jsonBody.put("password", passPenguji);

            mAuthAPIService = new ApiUtils().getAuthAPIService();
            sessionManager = new SessionManager(this);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (jsonBody).toString());

            Call<ResponseBody> response = mAuthAPIService.testloginPost(body);

            response.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                    if (rawResponse.isSuccessful()) {
                        try {

                            JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                            status = jsonObject.getBoolean("status");
                            message = jsonObject.getString("message");
                            if (!status) {
                                Toast.makeText(LoginPengujiKode.this, message,
                                        Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                username = jsonObject.getString("username");

                                new CountDownTimer(5000, 5000) {

                                    public void onTick(long millisUntilFinished) {
                                        // You don't need anything here
                                    }

                                    public void onFinish() {
                                        sessionManager.setUid(username);
                                        Toast.makeText(LoginPengujiKode.this, message,
                                                Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        Intent movea = new Intent(LoginPengujiKode.this, LoginPeserta.class);
                                        startActivity(movea);
                                        finish();
                                    }
                                }.start();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(LoginPengujiKode.this, "Username Atau Password Tidak Sesuai",
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(LoginPengujiKode.this, "Username Atau Password Tidak Sesuai",
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
            Toast.makeText(LoginPengujiKode.this, "Username Atau Password Tidak Sesuai",
                    Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show();
//            } else {
//                progressDialog.show();
//                loginHandler(result.getContents());
//            }
//
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_MasukKodePetugas:
                if (etKodePetugas.getText().toString().length() == 0) {
                    etKodePetugas.setError("Username kosong");
                } else if (etPassPetugas.getText().toString().length() == 0) {
                    etPassPetugas.setError("Password kosong");
                } else {
                    progressDialog.show();
                    loginHandler(etKodePetugas.getText().toString(), etPassPetugas.getText().toString());
                    break;
                }
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
                sessionManager = new SessionManager(this);
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
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    public void ipConfig() {
        LinearLayout linearLayout = new LinearLayout(LoginPengujiKode.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);

        final EditText ConfigIpInput = new EditText(LoginPengujiKode.this);
        ConfigIpInput.setHint("Contoh IP: 192.168.43.220");
        String ipNow = sessionManager.getHostIp();
        ConfigIpInput.setText(ipNow);
        linearLayout.addView(ConfigIpInput);

        final EditText ConfigPortInput = new EditText(LoginPengujiKode.this);
        ConfigPortInput.setHint("Contoh Port: 3500");
        ConfigPortInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        String portNow = sessionManager.getHostPort();
        ConfigPortInput.setText(portNow);
        linearLayout.addView(ConfigPortInput);

        AlertDialog.Builder info = new AlertDialog.Builder(LoginPengujiKode.this);
        info.setMessage("Ubah IP dan Port:").setCancelable(true).setPositiveButton("Simpan", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try {
                    ipValue = ConfigIpInput.getText().toString();
                    portValue = ConfigPortInput.getText().toString();
                    sessionManager.setSession(ipValue, portValue);
                    Toast.makeText(LoginPengujiKode.this,
                            "Konfigurasi disimpan\n http://" + ipValue + ":" + portValue + "/", Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException e) {
                    AlertDialog.Builder infoo = new AlertDialog.Builder(LoginPengujiKode.this);
                    infoo.setMessage("Masukkan nilai IP dan Port").setCancelable(false).setPositiveButton("Coba Lagi", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog1 = infoo.create();
                    dialog1.setTitle("Error");
                    dialog1.show();
                }
            }
        }).setNegativeButton("Batal", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog2, int id) {
                dialog2.cancel();
            }
        });
        AlertDialog dialog = info.create();
        dialog.setView(linearLayout);
        dialog.setTitle("IP Config");
        dialog.show();

    }

}
