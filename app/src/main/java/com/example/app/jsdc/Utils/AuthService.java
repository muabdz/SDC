package com.example.app.jsdc.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    @POST("/signin/{uid}/{time}")
    Call<ResponseBody> testloginPost(@Path("uid") String usernameId, @Path("time")String date);

    @POST("/peserta/{id}")
    Call<ResponseBody> loginPeserta(@Path("id") String idPeserta);

    @POST("/soal/{hasil}")
    Call<ResponseBody> submitPeserta(@Path("hasil")JSONObject hasil);

    @POST("/signout/{uid}")
    Call<ResponseBody> logoutPost(@Path("uid") String userId);

}
