package com.qljm.mvvmdemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.qljm.mvvmdemo.api.apiService
import com.qljm.mvvmdemo.data.LoginBean
import com.qljm.mvvmdemo.data.LoginReq
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class LoginRequestViewModel : BaseViewModel() {

    //自动脱壳过滤处理请求结果，自动判断结果是否成功
    var loginResult = MutableLiveData<ResultState<LoginBean>>()


    fun login(username: String, password: String) {
        request({
            apiService.loginPwd(LoginReq("86", username, password, ""))
        }, loginResult, true, "等待中")
    }
}



