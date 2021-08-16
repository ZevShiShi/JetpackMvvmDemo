package com.qljm.mvvmdemo.api

import com.qljm.mvvmdemo.data.LoginBean
import com.qljm.mvvmdemo.data.MediaNewsListData
import com.qljm.mvvmdemo.data.base.ApiResponse
import com.qljm.mvvmdemo.data.req.LoginReq
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {
    //密码登录
    @POST("/app/loginOrReg/login")
    suspend fun loginPwd(@Body data: LoginReq): ApiResponse<LoginBean>

    @GET("/app/news/list")
    suspend fun getNewList(@QueryMap map: Map<String, String>): ApiResponse<MediaNewsListData>
}