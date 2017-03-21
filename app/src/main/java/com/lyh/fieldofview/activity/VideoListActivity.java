package com.lyh.fieldofview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.lyh.fieldofview.InteressantFactory;
import com.lyh.fieldofview.R;
import com.lyh.fieldofview.api.InterestingApi;
import com.lyh.fieldofview.base.ToolbarActivity;
import com.lyh.fieldofview.interesting.InterestingAdapter;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.rx.ErrorAction;
import com.lyh.fieldofview.rx.RxScroller;
import com.lyh.fieldofview.utils.SlideInDownAnimator;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2017/3/21.
 */

public class VideoListActivity extends ToolbarActivity {

    private InterestingAdapter adapter;
    private InterestingApi api;

    private List<ItemList> items = new ArrayList<>();

    private int start;
    private int id;
    private boolean trending;
    private boolean newest;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView list = (RecyclerView) findViewById(R.id.list);

        api = InteressantFactory.getRetrofit().createApi(InterestingApi.class);

        id = getIntent().getExtras().getInt("id");
        trending = getIntent().getBooleanExtra("trending", false);
        newest = getIntent().getBooleanExtra("newest", false);

        if (trending) ab.setTitle("Trending");
        else if (newest) ab.setTitle("Newest");

        adapter = new InterestingAdapter(this, items);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new SlideInDownAnimator());

        list.setAdapter(adapter);

        RxRecyclerView.scrollStateChanges(list)
                .compose(bindToLifecycle())
                .compose(RxScroller.scrollTransformer(layoutManager, adapter, items))
                .subscribe(newState -> {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        start += 10;
                        loadVideos();
                    }
                });
    }

    private void loadVideos() {
        loadVideos(false);
    }

    private void loadVideos(boolean clean) {
        String strategy = null;
        if (trending) strategy = "mostPopular";
        else if (newest) strategy = "date";

        api.videoList(id, start, strategy)
                .compose(bindToLifecycle())
                .filter(interesting -> interesting != null)
                .filter(interesting -> interesting.itemList != null)
                .map(interesting -> interesting.itemList)
                .doOnNext(itemLists -> {
                    if (clean) items.clear();
                })
                .doOnNext(itemLists -> items.addAll(itemLists))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itemLists ->
                                adapter.notifyItemRangeInserted(start, items.size()),
                        ErrorAction.errorAction(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
