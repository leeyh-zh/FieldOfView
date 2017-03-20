package com.lyh.fieldofview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.lyh.fieldofview.InteressantFactory;
import com.lyh.fieldofview.R;
import com.lyh.fieldofview.Register;
import com.lyh.fieldofview.api.AuthorApi;
import com.lyh.fieldofview.base.ToolbarActivity;
import com.lyh.fieldofview.model.Category;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.provider.related.Card;
import com.lyh.fieldofview.provider.related.HeaderItem;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2017/3/20.
 */

public class VideoAuthorActivity extends ToolbarActivity {

    private static final String LEFT_TEXT_HEADER = "leftAlignTextHeader";
    private static final String BRIEF_CARD = "briefCard";
    private static final String VIDEO_COLLECT_BRIEF = "videoCollectionWithBrief";

    private MultiTypeAdapter adapter;

    private Items items = new Items();
    private AuthorApi authorApi;

    private int start = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.video_author_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab.setTitle("Author");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.author_list);

        adapter = new MultiTypeAdapter(items);

        Register.registerAuthorItem(adapter, this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        RxRecyclerView.scrollStateChanges(recyclerView)
                .compose(bindToLifecycle())
                .subscribe(newState -> {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        start += 10;
                        loadData();
                    }
                });

        authorApi = InteressantFactory.getRetrofit().createApi(AuthorApi.class);

        loadData();
    }

    private void loadData() {
        authorApi.authors(start)
                .compose((bindToLifecycle()))
                .filter(videoAuthor -> videoAuthor != null)
                .map(videoAuthor -> videoAuthor.itemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addData, Throwable::printStackTrace);
    }

    private void addData(List<ItemList> itemLists) {
        for (ItemList item : itemLists) {
            if (item.type.equals(LEFT_TEXT_HEADER)) {
                items.add(new Category(item.data.text));
            } else if (item.type.equals(BRIEF_CARD)) {
                items.add(new HeaderItem(item.data, null, false));
            } else if (item.type.equals(VIDEO_COLLECT_BRIEF)) {
                items.add(new HeaderItem(item.data.header, false));
                items.add(new Card(item));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
