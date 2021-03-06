package com.joaoneto.testeandroidv2.loginScreen.api

import com.joaoneto.testeandroidv2.loginScreen.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {
    @FormUrlEncoded
    @POST("login")
    fun signIn(
        @Field("user") user: String,
        @Field("password") password: String
    ): Call<LoginResponseModel>
}