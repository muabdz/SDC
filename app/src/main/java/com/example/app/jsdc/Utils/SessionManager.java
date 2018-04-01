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

    public void setQuestion(int id, int num, String question){
        prefs.edit().putInt(String.valueOf(num), id).apply();
        prefs.edit().putString(String.valueOf(id)+"id", question).apply();
        prefs.edit().putInt("num", num).apply();
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
}
