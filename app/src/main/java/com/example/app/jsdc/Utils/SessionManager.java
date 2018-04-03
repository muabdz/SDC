package com.example.app.jsdc.Utils;

/**
 * Created by Mu'adz on 3/14/2018.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by faisal on 3/10/18.
 */

public class SessionManager {
    String hostIp, hostPort, question, uid;
    int jumlahSoal;
    private SharedPreferences prefs;

    public SessionManager(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setSession(String hostIp, String hostPort) {
        prefs.edit().putString("hostIp", hostIp).apply();
        prefs.edit().putString("hostPort", hostPort).apply();
    }

    public void removeSession() {
        prefs.edit().remove("hostIp").apply();
        prefs.edit().remove("hostPort").apply();
    }


    public String getHostIp() {
        hostIp = prefs.getString("hostIp","45.77.246.7");
        return hostIp;
    }

    public String getHostPort() {
        hostPort = prefs.getString("hostPort","8080"); //gua rasa dari sini
        return hostPort;
    }

    public void setQuestion(int nomor, int jumlah, String question, int sesi, int id){
        prefs.edit().putInt(String.valueOf(nomor), id).apply();
        prefs.edit().putInt(String.valueOf(nomor)+"sesi", sesi).apply();
        prefs.edit().putString(String.valueOf(id)+"id", question).apply();
        prefs.edit().putInt("num", jumlah).apply();
    }

    public int getSesi(int num){
        int sesi = prefs.getInt(String.valueOf(num)+"sesi", 2);
        return sesi;
    }


    public String getQuestion(int id){
        question = prefs.getString(String.valueOf(id)+"id","error");
        return question;
    }

    public int getQuestionId(int num){
        num = prefs.getInt(String.valueOf(num), 0);
        return num;
    }

    public int getJumlahSoal(){
        jumlahSoal = prefs.getInt("num", 0);
        return jumlahSoal;
    }

    public void setData(String p_id, String nama, int cate){
        prefs.edit().putString("p_id", p_id).apply();
        prefs.edit().putString("nama", nama).apply();
        prefs.edit().putInt("cate", cate).apply();
    }

    public void setUid(String username){
        prefs.edit().putString("uid", username).apply();
    }

    public String getUid(){
        uid = prefs.getString("uid", "error");
        return uid;
    }

    public void removeSessionSoal(int nomor, int id){
        prefs.edit().remove(String.valueOf(nomor)).apply();
        prefs.edit().remove(String.valueOf(nomor)+"sesi").apply();
        prefs.edit().remove(String.valueOf(id)+"id").apply();
    }

    public void removeSessionPeserta(){
        prefs.edit().remove("p_id").apply();
        prefs.edit().remove("nama").apply();
        prefs.edit().remove("cate").apply();
    }

    public void removeSessionJumlahSoal(){
        prefs.edit().remove("num").apply();
    }
}
