package com.lyh.fieldofview.interesting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.lyh.fieldofview.MainActivity;
import com.lyh.fieldofview.model.Interesting;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.rx.ErrorAction;
import com.lyh.fieldofview.rx.RxScroller;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by lyh on 2017/3/21.
 */

public class ShareListFragment extends ItemFragment {
    private static final String SHARE = "shareCount";

    private InterestingAdapter adapter;

    private List<ItemList> shareList = new ArrayList<>();

    private boolean related;
    private boolean relatedHeader;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final int categoryId = getArguments().getInt(MainActivity.CATEGORY_ID);
        related = getArguments().getBoolean(InterestingActivity.RELATED_VIDEO);
        relatedHeader = getArguments().getBoolean(InterestingActivity.RELATED_HEADER_VIDEO);

        adapter = new InterestingAdapter(context, shareList);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);

        loadData(categoryId, SHARE);

        RxRecyclerView.scrollStateChanges(list)
                .compose(bindToLifecycle())
                .compose(RxScroller.scrollTransformer(layoutManager,
                        adapter, shareList))
                .subscribe(newState -> {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        start += 10;
                        loadData(categoryId, SHARE);
                    }
                });
    }

    private void loadData(int categoryId, String strategy) {
        Observable<Interesting> result;
        if (related) {
            result = interestingApi.related(start, categoryId, strategy);
        } else if (relatedHeader) {
            result = interestingApi.relatedHeader(start, categoryId, strategy);
        } else {
            result = interestingApi.getInteresting(start, categoryId, strategy);
        }

        result.compose(bindToLifecycle())
                .compose(interestingTransformer)
                .doOnNext(itemLists -> shareList.addAll(itemLists))
                .subscribe(itemLists -> {
                    adapter.notifyDataSetChanged();
                }, ErrorAction.errorAction(context));
    }
}
