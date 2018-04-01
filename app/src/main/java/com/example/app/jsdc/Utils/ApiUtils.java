package com.example.app.jsdc.Utils;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public class ApiUtils {
    //static SessionManager sessionManager;
    //static String hostPort = sessionManager.getHostPort();
    //static String hostIp = sessionManager.getHostIp();
    //private static final String BASE_URL = "http://"+ hostIp + ":" + hostPort + "/";
    public static final String BASE_URL = "http://45.77.246.7:8080/";

    private ApiUtils(){

    }

    public static AuthService getAuthAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }


}
