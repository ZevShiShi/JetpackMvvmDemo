package com.qljm.mvvmdemo.api

import com.qljm.mvvmdemo.data.LoginBean
import com.qljm.mvvmdemo.data.LoginReq
import com.qljm.mvvmdemo.data.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    //密码登录
    @POST("/app/loginOrReg/login")
    suspend fun loginPwd(@Body data: LoginReq): ApiResponse<LoginBean>
}