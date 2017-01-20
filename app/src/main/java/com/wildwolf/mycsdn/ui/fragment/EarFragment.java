package com.wildwolf.mycsdn.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wildwolf.mycsdn.R;
import com.wildwolf.mycsdn.data.EarData;
import com.wildwolf.mycsdn.parsenter.EarPresenter;
import com.wildwolf.mycsdn.ui.activity.EarActivity;
import com.wildwolf.mycsdn.ui.adapter.EarAdapter;
import com.wildwolf.mycsdn.ui.adapter.baseadapter.OnItemClickListeners;
import com.wildwolf.mycsdn.ui.adapter.baseadapter.OnLoadMoreListener;
import com.wildwolf.mycsdn.ui.adapter.baseadapter.ViewHolder;
import com.wildwolf.mycsdn.ui.view.EarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ${wild00wolf} on 2016/11/25.
 */
public class EarFragment extends BaseMvpFragment<EarView, EarPresenter> implements EarView, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 1;
    private String mSubtype;
    private int mTempPageCount = 2;

    private EarAdapter earAdapter;

    private boolean isLoadMore;

    @Bind(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static EarFragment newInstance(String subtype) {
        EarFragment fragment = new EarFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected EarPresenter initPresenter() {
        return new EarPresenter();
    }

    @Override
    protected void fetchData() {
        mPresenter.getEarData(mSubtype, PAGE_COUNT);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    protected void initView() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        earAdapter = new EarAdapter(mActivity, new ArrayList<EarData>(), true);
        earAdapter.setLoadingView(R.layout.load_loading_layout);
        earAdapter.setOnItemClickListener(new OnItemClickListeners<EarData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, EarData data, int position) {
                Intent intent = new Intent(mActivity, EarActivity.class);
                intent.putExtra("ear_item_data", data);
                startActivity(intent);
            }
        });

        earAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (PAGE_COUNT == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                fetchData();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(earAdapter);

    }

    @Override
    protected void initData() {
        if (getArguments() == null) {
            return;
        }
        mSubtype = getArguments().getString(SUB_TYPE);
    }

    @Override
    public void onSuccess(List<EarData> data) {
        Log.e("TAG--name", data.get(0).getName());
        Log.e("TAG--url ", data.get(0).getImgUrl());
        Log.e("TAG--sub", data.get(0).getSubtype());
        Log.e("TAG--a", data.get(0).getArticleCount());
        Log.e("TAG--r", data.get(0).getReadCount());
        Log.e("TAG--href", data.get(0).getHref());

        if (isLoadMore) {
            if (data.size() == 0) {
                earAdapter.setLoadEndView(R.layout.load_end_layout);
            } else {
                earAdapter.setLoadMoreData(data);
                mTempPageCount++;
            }
        } else {
            earAdapter.setNewData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            earAdapter.setLoadFailedView(R.layout.load_failed_layout);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        fetchData();
    }
}