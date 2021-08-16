package com.qljm.mvvmdemo.data

import java.io.Serializable


data class MediaNewsListData(
    val qureyTime: String,
    val currPage: Int,
    val totalPage: Int,
    val pageSize: Int,
    val totalCount: Long,
    val lastIndex: String,
    var list: MutableList<NewsDetailsBean>
) : Serializable

