package com.qljm.mvvmdemo.ui.adapter

import com.blankj.utilcode.util.ObjectUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.makeramen.roundedimageview.RoundedImageView
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.data.NewsDetailsBean


/**
 * 右侧小图
 * titleTagType  标签类型  0 无 1 置顶  2 推荐 3 广告 4 原创
 */
class ContentProvider1 : BaseItemProvider<NewsDetailsBean, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_content_type_1
    }

    override fun viewType(): Int {
        return NewsDetailsBean.TYPE_2
    }

    override fun convert(helper: BaseViewHolder, data: NewsDetailsBean, position: Int) {
        val ivContent = helper.getView<RoundedImageView>(R.id.ivContent)
        helper.setText(R.id.tvTitle, data.title)
        helper.setText(R.id.tvFrom, data.newspaperOfficeName)
        if (ObjectUtils.isNotEmpty(data.bannerUrl)) {
            Glide.with(mContext)
                .load(data.bannerUrl[0])
                .into(ivContent)
        }
    }

}