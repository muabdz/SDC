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
    String hostIp, hostPort;
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
        hostPort = prefs.getString("hostPort","8080");
        return hostPort;
    }
}
