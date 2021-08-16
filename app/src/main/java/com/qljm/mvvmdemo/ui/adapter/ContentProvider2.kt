package com.qljm.mvvmdemo.ui.adapter

import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.data.NewsDetailsBean


/**
 * 纯文字item
 */
class ContentProvider2 : BaseItemProvider<NewsDetailsBean, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_content_type_2
    }

    override fun viewType(): Int {
        return NewsDetailsBean.TYPE_3
    }

    override fun convert(helper: BaseViewHolder, data: NewsDetailsBean, position: Int) {
        helper.setText(R.id.tvTitle, data.title)
        helper.setText(R.id.tvFrom, data.newspaperOfficeName)
        if (ObjectUtils.isNotEmpty(data.titleTagType)) {
            if (data.titleTagType == "1") {
                helper.setGone(R.id.tvTop, true)
            } else {
                helper.setGone(R.id.tvTop, false)
            }
        } else {
            helper.setGone(R.id.tvTop, false)
        }
    }
}