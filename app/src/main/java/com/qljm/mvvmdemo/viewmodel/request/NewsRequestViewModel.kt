package com.qljm.mvvmdemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ObjectUtils
import com.qljm.mvvmdemo.data.MediaNewsListData
import com.qljm.mvvmdemo.http.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class NewsRequestViewModel : BaseViewModel() {

    var newsListResult = MutableLiveData<ResultState<MediaNewsListData>>()

    fun getNewsList(page: Int, lastIndex: String="",newsIds: String="") {
        request(
            {
                val map = hashMapOf(
                    "channelId" to "19",
                    "areaId" to "",
                    "pageNum" to page.toString(),
                    "pageSize" to "10",
                    "lastIndex" to lastIndex,
                    "version" to AppUtils.getAppVersionName()
                )
                if (ObjectUtils.isNotEmpty(newsIds)) {
                    map["newsIds"] = newsIds
                }
                apiService.getNewList(map)
            }, newsListResult
        )
    }


}