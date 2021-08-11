package com.qljm.mvvmdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.base.BaseActivity
import com.qljm.mvvmdemo.databinding.ActivityMainBinding
import com.qljm.mvvmdemo.viewmodel.view.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun layoutId()=R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
    }
}