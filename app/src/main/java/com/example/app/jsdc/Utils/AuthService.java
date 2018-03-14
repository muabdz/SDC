package com.example.app.jsdc.Utils;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Mu'adz on 3/14/2018.
 */

public interface AuthService {
    @POST("/signin/{uid}")
    Call<ResponseBody> testloginPost(@Path("uid") String postfix);
}
