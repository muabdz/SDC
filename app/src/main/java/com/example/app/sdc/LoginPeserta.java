package com.example.app.sdc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.sdc.Utils.ApiUtils;
import com.example.app.sdc.Utils.AuthService;
import com.example.app.sdc.Utils.SessionManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginPeserta extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    String message, p_id, nama, usernamePenguji;
    Boolean status;
    int cate;
    public static Context context;
    AuthService mAuthAPIService;
    ProgressDialog progressDialog;
    private GoogleApiClient client;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginPeserta.context = getApplicationContext();
        setContentView(R.layout.activity_login_peserta);
        sessionManager = new SessionManager(this);
        final Activity activity = this;
        Button T_scanpeserta = (Button) findViewById(R.id.b_scanPeserta);
        TextView b_loginkode = (TextView) findViewById(R.id.Bantuan);
        TextView penguji = (TextView) findViewById(R.id.penguji);
        progressDialog = new ProgressDialog(LoginPeserta.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Mohon Tunggu");
        penguji.setText(sessionManager.getUid());


        b_loginkode.setOnClickListener(this);


        T_scanpeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                integrator.initiateScan();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static Context getAppContext() {
        return LoginPeserta.context;
    }
    public void loginHandler(String kodePeserta) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", kodePeserta);

        ApiUtils au = new ApiUtils();
        mAuthAPIService = au.getAuthAPIService();

        Call<ResponseBody> response = mAuthAPIService.loginPeserta(kodePeserta);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.isSuccessful()) {
                    try {

                        JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                        message = jsonObject.getString("message");
                        status = jsonObject.getBoolean("status");
                        if (!status) {
                            Toast.makeText(LoginPeserta.this, message,
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


                            int jumsol = 0;
                            int soalPraktek = 0;
                            for (int i = 0; i < jsonSoal.length(); i++) {
                                jsonObject = jsonSoal.getJSONObject(i);
                                int kategori = jsonObject.getInt("kategori");
                                int id = jsonObject.getInt("id");
                                String soal = jsonObject.getString("soal");
                                if (id < 7) {
                                    sessionManager.setSessionSikap(soal, id);
                                } else {
                                    soalPraktek++;
                                    jumsol++;
                                    sessionManager.setQuestion(soalPraktek, jumsol, soal, kategori, id);
                                }
                                sessionManager.setJumlahTotal(jsonSoal.length());
                            }


                            new CountDownTimer(1000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    // You don't need anything here
                                }

                                public void onFinish() {
                                    Toast.makeText(LoginPeserta.this, message,
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Intent movea = new Intent(LoginPeserta.this, TestPraktekAll.class);
                                    startActivity(movea);
                                    finish();
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginPeserta.this, "Kode Peserta Salah",
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Logout")
                        .setMessage("Apakah anda yakin ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.show();
                                logoutHandler();
                            }

                        })
                        .setNegativeButton("Tidak", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "GAGAL", Toast.LENGTH_LONG).show();
            } else {

                progressDialog.show();
                loginHandler(result.getContents());
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Bantuan:
//                Intent kodePendaftaran = new Intent(this, LoginPesertaKode.class);
//                startActivity(kodePendaftaran);
                gagalScan();
                break;

        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("LoginPeserta Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void logoutHandler() {
        Map<String, Object> jsonParams = new ArrayMap<>();
        usernamePenguji = sessionManager.getUid();
        jsonParams.put("username", usernamePenguji);

        mAuthAPIService = new ApiUtils().getAuthAPIService();

        Call<ResponseBody> response = mAuthAPIService.logoutPost(usernamePenguji);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {
                if (rawResponse.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(rawResponse.body().string());
                        message = jsonObject.getString("message");
                        status = jsonObject.getBoolean("status");
                        if (!status) {
                            Toast.makeText(LoginPeserta.this, message,
                                    Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else {
                            new CountDownTimer(1000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    // You don't need anything here
                                }

                                public void onFinish() {
                                    sessionManager.removeUid();
                                    Toast.makeText(LoginPeserta.this, message,
                                            Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    Intent movea = new Intent(LoginPeserta.this, LoginPengujiKode.class);
                                    startActivity(movea);
                                    finish();
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginPeserta.this, "Petugas Gagal Logout",
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginPeserta.this, "Terjadi Kesalahan",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void gagalScan(){
        LinearLayout linearLayout = new LinearLayout(LoginPeserta.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                50,
                LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);

        final EditText etKodePeserta = new EditText(LoginPeserta.this);
        etKodePeserta.setHint("Kode Peserta");
        linearLayout.addView(etKodePeserta);

        AlertDialog.Builder info = new AlertDialog.Builder(LoginPeserta.this);
        info.setMessage("Masukkan Kode Peserta").setCancelable(true).setPositiveButton("OK", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try{
                    String kodePeserta = etKodePeserta.getText().toString();
                    progressDialog.show();
                    loginHandler(kodePeserta);
                }catch (NumberFormatException e){
                    AlertDialog.Builder infoo = new AlertDialog.Builder(LoginPeserta.this);
                    infoo.setMessage("Masukkan kode dengan benar").setCancelable(false).setPositiveButton("Coba Lagi", new AlertDialog.OnClickListener(){
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
        dialog.setTitle("Gagal Scan");
        dialog.show();

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
}
