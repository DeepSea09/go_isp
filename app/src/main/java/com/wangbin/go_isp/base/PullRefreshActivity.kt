package com.wangbin.go_isp.base

import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener

/**
 * @author wanglong
 */
abstract class PullRefreshActivity : BaseActivity() , OnRefreshListener ,OnLoadMoreListener{

    override fun onLoadMore(refreshLayout: RefreshLayout?) {

    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {

    }
    fun setRefreshListener(refresh: SmartRefreshLayout, isLoad: Boolean) {
        refresh.setOnRefreshListener(this)
        if (isLoad) {
            refresh.setOnLoadMoreListener(this)
        }
    }

    fun stopRefresh(refresh: SmartRefreshLayout,isLoad: Boolean) {
        refresh.finishRefresh()
        if (isLoad) {
            refresh.finishLoadMore()
        }
    }

}