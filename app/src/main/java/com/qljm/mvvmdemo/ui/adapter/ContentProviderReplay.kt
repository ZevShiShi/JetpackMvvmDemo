package com.qljm.mvvmdemo.ui.adapter

import com.qljm.mvvmdemo.data.NewsDetailsBean

class ContentProviderReplay: ContentProvider5() {

    override fun viewType(): Int {
        return NewsDetailsBean.TYPE_8
    }
}