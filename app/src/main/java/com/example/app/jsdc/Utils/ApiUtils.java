package com.example.app.jsdc.Utils;

import com.example.app.jsdc.LoginPenguji;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public class ApiUtils {
    static SessionManager sessionManager;
    static String hostPort;
    static String hostIp;
    //private static String BASE_URL = "http://"+ hostIp + ":" + hostPort + "/";
    //public static final String BASE_URL = "http://45.77.246.7:8080/";

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
