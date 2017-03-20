package com.lyh.fieldofview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.lyh.fieldofview.InteressantFactory;
import com.lyh.fieldofview.MainActivity;
import com.lyh.fieldofview.R;
import com.lyh.fieldofview.Register;
import com.lyh.fieldofview.api.InterestingApi;
import com.lyh.fieldofview.base.ToolbarActivity;
import com.lyh.fieldofview.model.Category;
import com.lyh.fieldofview.model.CategoryInfo;
import com.lyh.fieldofview.model.Data;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.model.SectionList;
import com.lyh.fieldofview.provider.related.Card;
import com.lyh.fieldofview.provider.related.HeaderItem;
import com.lyh.fieldofview.provider.related.RelatedHeader;
import com.lyh.fieldofview.provider.video.FooterForward;
import com.lyh.fieldofview.rx.ErrorAction;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2017/3/20.
 */

public class FindInterestingActivity extends ToolbarActivity{

    private static final String HORIZONTAL_SCROLL_CARD_SECTION = "horizontalScrollCardSection";
    private static final String VIDEO_LIST_SECTION = "videoListSection";
    private static final String AUTHOR_SECTION = "authorSection";

    private MultiTypeAdapter adapter;

    private Items items = new Items();
    private InterestingApi interestingApi;

    private CategoryInfo categoryInfo;
    private int id;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_find_interesting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(MainActivity.TITLE);
        ab.setTitle(title.substring(1));

        id = getIntent().getExtras().getInt(MainActivity.CATEGORY_ID);

        interestingApi = InteressantFactory.getRetrofit().createApi(InterestingApi.class);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);

        adapter = new MultiTypeAdapter(items);
        list.setAdapter(adapter);

        Register.registerFindItem(adapter, this);

        findVideos();
    }
    private void findVideos() {
        interestingApi.findVideo(id)
                .compose(bindToLifecycle())
                .filter(find -> find != null)
                .filter(find -> find.sectionList != null)
                .doOnNext(find -> this.categoryInfo = find.categoryInfo)
                .map(find -> find.sectionList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addData, ErrorAction.errorAction(this));
    }

    private void addData(List<SectionList> sectionLists) {
        for (SectionList sectionList : sectionLists) {
            if (sectionList.type.equals(HORIZONTAL_SCROLL_CARD_SECTION)) {
                Data data = sectionList.itemList.get(0).data;
                items.add(new RelatedHeader(data.header, false));
                items.add(new Card(sectionList.itemList.get(0)));
            } else if (sectionList.type.equals(VIDEO_LIST_SECTION)) {
                items.add(new Category(sectionList.header.data.text));
                addVideo(sectionList.itemList);
                items.add(new FooterForward(categoryInfo.id, sectionList.footer.data.text));
            } else if (sectionList.type.equals(AUTHOR_SECTION)) {
                addAuthorItem(sectionList.itemList);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void addVideo(List<ItemList> itemLists) {
        for (ItemList item : itemLists) {
            items.add(item);
        }
    }

    private void addAuthorItem(List<ItemList> itemLists) {
        for (ItemList item : itemLists) {
            items.add(new HeaderItem(item.data.header, true));
            items.add(new Card(item));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.search_action) {
            toSearch(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
