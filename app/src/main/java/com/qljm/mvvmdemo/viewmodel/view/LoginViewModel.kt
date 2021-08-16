package com.qljm.mvvmdemo.viewmodel.view

import android.view.View
import androidx.databinding.ObservableInt
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.FloatObservableField
import me.hgj.jetpackmvvm.callback.databind.IntObservableField
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

class LoginViewModel : BaseViewModel() {

    var username = StringObservableField()
    var password = StringObservableField()
    var textSize = IntObservableField()


    var clearVisible = object : ObservableInt(username) {
        override fun get(): Int {
            return if (username.get().isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }


    var passwordVisible = object : ObservableInt(password) {
        override fun get(): Int {
            return if (password.get().isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

}