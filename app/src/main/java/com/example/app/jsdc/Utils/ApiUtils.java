package com.example.app.jsdc.Utils;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public class ApiUtils {
    static SessionManager sessionManager;
    static String hostPort = sessionManager.getHostPort();
    static String hostIp = sessionManager.getHostIp();

    static String BASE_URL = "http://"+ hostIp + ":" + hostPort + "/";
    private ApiUtils(){

    }

    public static AuthService getAuthAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }


}
