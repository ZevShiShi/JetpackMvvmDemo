package com.qljm.mvvmdemo.ui.adapter

import android.widget.ImageView
import com.blankj.utilcode.util.ObjectUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.data.NewsDetailsBean
import com.qljm.mvvmdemo.utils.TimeUtils


/**
 * 视频item
 */
open class ContentProvider5 : BaseItemProvider<NewsDetailsBean, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_content_type_5
    }

    override fun viewType(): Int {
        return NewsDetailsBean.TYPE_4
    }

    override fun convert(helper: BaseViewHolder, data: NewsDetailsBean, position: Int) {
        val ivContent = helper.getView<ImageView>(R.id.ivContent)
        helper.setText(R.id.tvTitle, data.title)
        helper.setText(R.id.tvFrom, data.newspaperOfficeName)
        helper.setText(R.id.timeLong, TimeUtils.Metamerism(data.timeLong))
        if (ObjectUtils.isNotEmpty(data.bannerUrl)) {
            Glide.with(mContext)
                .load(data.bannerUrl[0])
                .into(ivContent)
        }
    }



}