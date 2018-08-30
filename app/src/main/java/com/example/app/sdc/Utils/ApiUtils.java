package com.example.app.sdc.Utils;

import com.example.app.sdc.LoginPenguji;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public class ApiUtils {
    static SessionManager sessionManager;
    static String hostPort;
    static String hostIp;

    public ApiUtils(){
        sessionManager = new SessionManager(LoginPenguji.getAppContext());
        this.hostPort = sessionManager.getHostPort();
        this.hostIp = sessionManager.getHostIp();
    }

    public static AuthService getAuthAPIService() {
        String BASE_URL = "http://"+ hostIp + ":" + hostPort + "/";
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }


}
