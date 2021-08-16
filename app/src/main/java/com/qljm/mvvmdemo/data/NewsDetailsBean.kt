package com.qljm.mvvmdemo.data

import java.io.Serializable


data class NewsDetailsBean(
    var typeNew: Int,
    var bannerUrl: MutableList<String>,
    val id: String = "",
    val title: String = "",
    val type: Int = 0,
    val contentType: Int = 0,
    val subhead: String = "",
    val newsBanner: String = "",
    val newspaperOfficeName: String = "",
    val newspaperOfficeId: Int = 0,
    val channels: String = "",
    val videoUrl: String = "",
    val titleTagType: String = "",
    val timeLong: String = "",
    var channelId: String = "",
    var showLine: Boolean = true,
    var showRecommend: Boolean = true
) : Serializable {
    companion object {
        // 1:单张大图资讯 2:单张小图资讯 3:无图资讯 4:大图视频 5:多图资讯 6:直播 7小视屏 8直播回放 9信息流广告
        const val TYPE_1: Int = 1
        const val TYPE_2: Int = 2
        const val TYPE_3: Int = 3
        const val TYPE_4: Int = 4
        const val TYPE_5: Int = 5
        const val TYPE_6: Int = 6
        const val TYPE_7: Int = 7
        const val TYPE_8: Int = 8
        const val TYPE_9: Int = 9
    }

    constructor(type: Int) : this(typeNew = type, bannerUrl = mutableListOf<String>())

    override fun toString(): String {
        return "NewsDetailsBean(id='$id', title='$title', type=$type, contentType=$contentType, typeNew=$typeNew, subhead='$subhead', newsBanner='$newsBanner', bannerUrl=$bannerUrl, newspaperOfficeName='$newspaperOfficeName', newspaperOfficeId=$newspaperOfficeId, channels='$channels', videoUrl='$videoUrl', titleTagType='$titleTagType', timeLong='$timeLong', showLine=$showLine, showRecommend=$showRecommend)"
    }
}



