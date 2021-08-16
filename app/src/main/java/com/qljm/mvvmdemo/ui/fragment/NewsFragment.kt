package com.qljm.mvvmdemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.qljm.mvvmdemo.R
import com.qljm.mvvmdemo.base.BaseFragment
import com.qljm.mvvmdemo.databinding.NewsFragmentBinding
import com.qljm.mvvmdemo.ui.adapter.HomeMulItemAdapter
import com.qljm.mvvmdemo.viewmodel.request.NewsRequestViewModel
import com.qljm.mvvmdemo.viewmodel.view.NewsViewModel
import kotlinx.android.synthetic.main.news_fragment.*
import me.hgj.jetpackmvvm.ext.parseState

class NewsFragment : BaseFragment<NewsViewModel, NewsFragmentBinding>() {

    private val reqViewModel: NewsRequestViewModel by viewModels()

    private var currPage = 1
    private var totalPage = 0
    private var isRefresh = true
    private var lastIndex = ""
    private var mNewsIdsStr = ""
    private var mAdapter: HomeMulItemAdapter = HomeMulItemAdapter(mutableListOf())

    override fun layoutId() = R.layout.news_fragment

    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(reqViewModel)
        mDatabind.viewModel = mViewModel
        mAdapter.setOnLoadMoreListener({
            if (totalPage < 10) {
                mAdapter.loadMoreEnd()
            } else {
                reqViewModel.getNewsList(currPage, lastIndex, mNewsIdsStr)
            }
        }, rvNews)
        mAdapter.disableLoadMoreIfNotFullPage()
        mAdapter.setEnableLoadMore(true)
        mAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

            }
        rvNews.adapter = mAdapter
        reqViewModel.getNewsList(currPage)
    }

    override fun createObserver() {
        reqViewModel.newsListResult.observe(viewLifecycleOwner, Observer { resultStatus ->
            parseState(resultStatus, {
                val ids = StringBuilder()
                for ((i, bean) in it.list.withIndex()) {
                    ids.append(bean.id)
                    if (i != it.list.size - 1) {
                        ids.append(",")
                    }
                }
                mNewsIdsStr = ids.toString()
                lastIndex = it.lastIndex
                totalPage = it.list.size
                if (isRefresh) {
                    isRefresh = false
                    mAdapter.setNewData(it.list)
                } else {
                    mAdapter.addData(it.list)
                    mAdapter.loadMoreComplete()
                }
                currPage++
            }, {
                LogUtils.e("newsListResult=======${it.errorMsg}")
            })
        })

    }

}