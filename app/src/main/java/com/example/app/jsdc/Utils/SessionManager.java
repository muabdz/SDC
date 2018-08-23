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
    private SharedPreferences prefs, pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
        pref = cntx.getSharedPreferences("androidhive-welcome", 0);
        editor = pref.edit();
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
        hostIp = prefs.getString("hostIp","192.168.43.220");
        return hostIp;
    }

    public String getHostPort() {
        hostPort = prefs.getString("hostPort","3500");
        return hostPort;
    }

    public void setQuestion(int nomor, int jumlah, String question, int kategori, int id){
        prefs.edit().putInt(String.valueOf(nomor), id).apply();
        prefs.edit().putInt(String.valueOf(nomor)+"kategori", kategori).apply();
        prefs.edit().putString(String.valueOf(id)+"id", question).apply();
        prefs.edit().putInt("jumlah", jumlah).apply();
    }

//    public int getSesi(int num){
//        int sesi = prefs.getInt(String.valueOf(num)+"sesi", 2);
//        return sesi;
//    }

    public String getQuestion(int id){
        question = prefs.getString(String.valueOf(id)+"id","error");
        return question;
    }

    public int getQuestionId(int num){
        num = prefs.getInt(String.valueOf(num), 0);
        return num;
    }

    public int getJumlahSoal(){
        jumlahSoal = prefs.getInt("jumlah", 0);
        return jumlahSoal;
    }

    public void setData(String p_id, String nama, int cate){
        prefs.edit().putString("p_id", p_id).apply();
        prefs.edit().putString("nama", nama).apply();
        prefs.edit().putInt("cate", cate).apply();
    }

    public String getNama(){
        String nama = prefs.getString("nama", "");
        return nama;
    }

    public String getPId(){
        String p_id = prefs.getString("p_id", "");
        return p_id;
    }

    public Integer getCate(){
        int cate = prefs.getInt("cate", 0);
        return cate;
    }

    public void setStartTime(String time){
        prefs.edit().putString("startTime", time).apply();
    }

    public String getStartTime(){
        String startTime = prefs.getString("startTime", "");
        return startTime;
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
        prefs.edit().remove("startTime").apply();
    }

    public void removeSessionJumlahSoal(){
        prefs.edit().remove("jumlah").apply();
    }

    public void setSessionSikap(String sikap, int id){
        prefs.edit().putInt(String.valueOf(id)+"idsikap", id).apply();
        prefs.edit().putString(String.valueOf(id)+"sikap", sikap).apply();
    }

    public int getIdSikap(int id){
        int idSikap = prefs.getInt(id+"idsikap", 0);
        return idSikap;
    }

    public void removeSessionSikap(int id){
        prefs.edit().remove(String.valueOf(id)+"sikap").apply();
        prefs.edit().remove(String.valueOf(id)+"idsikap").apply();
    }

    public void setJumlahTotal(int jumlah){
        prefs.edit().putInt("jumlahTotal", jumlah).apply();
    }

    public int getJumlahTotal(){
        int jumlah = prefs.getInt("jumlahTotal", 0);
        return jumlah;
    }
    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean("IsFirstTimeLaunch", isFirstTime);
        editor.commit();
    }

}
