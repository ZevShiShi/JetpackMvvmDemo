package com.qljm.mvvmdemo.ui.adapter

import android.widget.ImageView
import com.blankj.utilcode.util.ObjectUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.makeramen.roundedimageview.RoundedImageView
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.data.NewsDetailsBean
import java.util.*


/**
 * 三小图item
 */
class ContentProvider3 : BaseItemProvider<NewsDetailsBean, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_content_type_3
    }

    override fun viewType(): Int {
        return NewsDetailsBean.TYPE_5
    }

    override fun convert(helper: BaseViewHolder, data: NewsDetailsBean, position: Int) {
//        val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
        val ivMoreImg1 = helper.getView<RoundedImageView>(R.id.ivMoreImg1)
        val ivMoreImg2 = helper.getView<RoundedImageView>(R.id.ivMoreImg2)
        val ivMoreImg3 = helper.getView<RoundedImageView>(R.id.ivMoreImg3)
        val imageViewList: MutableList<ImageView> = mutableListOf()
        imageViewList.add(ivMoreImg1)
        imageViewList.add(ivMoreImg2)
        imageViewList.add(ivMoreImg3)
        helper.setText(R.id.tvTitle, data.title)
        helper.setText(R.id.tvFrom, data.newspaperOfficeName)
        if (ObjectUtils.isNotEmpty(data.bannerUrl)) {
            var imgList: MutableList<String> = ArrayList<String>()
            if (data.bannerUrl.size > 3) {
                for (i in 0..2) {
                    imgList.add(data.bannerUrl[i])
                }
            } else {
                imgList.addAll(data.bannerUrl)
            }

            if (imgList.size <= 3) {
                for ((i, url) in imgList.withIndex()) {
                    Glide.with(mContext)
                        .load(url)
                        .into(imageViewList[i])
                }
            }
        }
    }

}