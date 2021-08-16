package com.qljm.mvvmdemo.ui.adapter

import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.qljm.mvvmdemo.data.NewsDetailsBean

class HomeMulItemAdapter(
    data: MutableList<NewsDetailsBean>?
) :
    MultipleItemRvAdapter<NewsDetailsBean, BaseViewHolder>(data) {

    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
//        LogUtils.d("initAdapter=======registerItemProvider========${mActivity!!}")
        mProviderDelegate.registerProvider(ContentProvider1())
        mProviderDelegate.registerProvider(ContentProvider2())
        mProviderDelegate.registerProvider(ContentProvider3())
        mProviderDelegate.registerProvider(ContentProvider4())
        mProviderDelegate.registerProvider(ContentProvider6())
        mProviderDelegate.registerProvider(ContentProvider5())
        mProviderDelegate.registerProvider(ContentProvider7())
        mProviderDelegate.registerProvider(ContentProviderReplay())
    }

    override fun getViewType(t: NewsDetailsBean?): Int {
        LogUtils.d("getViewType=========${t?.type}     ${t?.typeNew}")

        when (val type = t?.typeNew ?: t?.type) {
            NewsDetailsBean.TYPE_1 -> {
                return type
            }
            NewsDetailsBean.TYPE_2 -> {
                return type
            }
            NewsDetailsBean.TYPE_3 -> {
                return type
            }
            NewsDetailsBean.TYPE_4 -> {
                return type
            }
            NewsDetailsBean.TYPE_5 -> {
                return type
            }
            NewsDetailsBean.TYPE_6 -> {
                return type
            }
            NewsDetailsBean.TYPE_7 -> {
                return type
            }
            NewsDetailsBean.TYPE_8 -> {
                return type
            }
            NewsDetailsBean.TYPE_9 -> {
                return type
            }
        }
        return 1
    }
}