package com.qljm.mvvmdemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.app.ext.showMessage
import com.qljm.mvvmdemo.base.BaseFragment
import com.qljm.mvvmdemo.databinding.FragmentLoginBinding
import com.qljm.mvvmdemo.viewmodel.request.LoginRequestViewModel
import com.qljm.mvvmdemo.viewmodel.view.LoginViewModel
import me.hgj.jetpackmvvm.ext.parseState

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private val requestViewModel: LoginRequestViewModel by viewModels()


    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(requestViewModel)
        mDatabind.viewModel = mViewModel
        mDatabind.click = ProxyClick()

        mViewModel.textSize.set(ConvertUtils.sp2px(24f))

    }


    override fun createObserver() {
        requestViewModel.loginResult.observe(viewLifecycleOwner, Observer { resultState ->
            parseState(resultState, {
                LogUtils.e("createObserver=====token===${it.token}")
            }, {
                LogUtils.e("createObserver=====error====${it.errCode}==${it.errorMsg}===${it.throwable}")
            })
        })
    }

    inner class ProxyClick {

        fun clear() {
            mViewModel.username.set("")
            mViewModel.password.set("")
        }

        fun login() {
            when {
                mViewModel.username.get().isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                else -> requestViewModel.login(
                    mViewModel.username.get(),
                    mViewModel.password.get()
                )
            }
        }

        fun goRegister() {
            ToastUtils.showShort("去注册把")
        }
    }

}