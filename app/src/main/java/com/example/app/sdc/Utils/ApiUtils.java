package com.example.app.sdc.Utils;

import android.content.Context;

import com.example.app.sdc.LoginPenguji;
import com.example.app.sdc.LoginPengujiKode;
import com.example.app.sdc.LoginPeserta;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public class ApiUtils {
    private String hostPort;
    private String hostIp;

    public ApiUtils(){
        Context ctx = LoginPengujiKode.getAppContext();
        if (ctx==null){
            ctx = LoginPeserta.getAppContext();
        }
        SessionManager sessionManager = new SessionManager(ctx);
        hostPort = sessionManager.getHostPort();
        hostIp = sessionManager.getHostIp();
        if (hostIp ==null){
            hostIp = "192.168.43.220";
        }
        if (hostPort==null){
            hostPort = "3500";
        }
    }

    public AuthService getAuthAPIService() {
        String BASE_URL = "http://"+ hostIp + ":" + hostPort + "/";
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }


}
