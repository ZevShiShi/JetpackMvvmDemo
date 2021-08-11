package com.qljm.mvvmdemo.app

import android.content.Context
import me.hgj.jetpackmvvm.base.BaseApp

class BaseApplication : BaseApp() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}