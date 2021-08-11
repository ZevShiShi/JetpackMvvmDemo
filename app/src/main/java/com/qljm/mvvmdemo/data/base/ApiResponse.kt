package com.qljm.mvvmdemo.data.base

import me.hgj.jetpackmvvm.network.BaseResponse

data class ApiResponse<T>(var code: Int, var msg: String, var data: T) : BaseResponse<T>() {
    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg

    override fun isSucces() = code == 0

}