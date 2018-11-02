package com.example.app.sdc.Utils;

import com.example.app.sdc.LoginPenguji;
import com.example.app.sdc.LoginPengujiKode;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public class ApiUtils {
    private static String hostPort;
    private static String hostIp;

    public ApiUtils(){
        SessionManager sessionManager = new SessionManager(LoginPengujiKode.getAppContext());
        hostPort = sessionManager.getHostPort();
        hostIp = sessionManager.getHostIp();
    }

    public static AuthService getAuthAPIService() {
        String BASE_URL = "http://"+ hostIp + ":" + hostPort + "/";
        return RetrofitClient.getClient(BASE_URL).create(AuthService.class);
    }


}
