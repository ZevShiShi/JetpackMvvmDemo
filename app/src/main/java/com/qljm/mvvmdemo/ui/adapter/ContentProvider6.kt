package com.qljm.mvvmdemo.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ObjectUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.data.NewsDetailsBean
import com.qljm.mvvmdemo.utils.TimeUtils


/**
 * 小图视频item
 */
class ContentProvider6 : BaseItemProvider<NewsDetailsBean, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_small_video_layout //item_live_layout
    }

    override fun viewType(): Int {
        return NewsDetailsBean.TYPE_7
    }

    override fun convert(helper: BaseViewHolder, item: NewsDetailsBean, position: Int) {
        val tvLiveTitle = helper.getView<TextView>(R.id.tv_live_title)
        val tvCreateOfficeName = helper.getView<TextView>(R.id.tvscMediaName)
        val ivImage = helper.getView<ImageView>(R.id.iv_image)
        val tvIslive = helper.getView<TextView>(R.id.tvIslive)
        helper.setVisible(R.id.lines, true)

        tvLiveTitle?.text = item.title
        tvCreateOfficeName.text = item.newspaperOfficeName
        if (item.timeLong?.toInt() > 0)
            tvIslive.visibility = View.VISIBLE
        tvIslive.text = TimeUtils.Metamerism(item.timeLong)

        if (ObjectUtils.isNotEmpty(item.bannerUrl)) {
            Glide.with(mContext)
                .load(item.bannerUrl[0])
                .into(ivImage)
        }
    }

}