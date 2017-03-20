package com.lyh.fieldofview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.lyh.fieldofview.InteressantFactory;
import com.lyh.fieldofview.R;
import com.lyh.fieldofview.Register;
import com.lyh.fieldofview.api.RelatedApi;
import com.lyh.fieldofview.model.Header;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.provider.related.Card;
import com.lyh.fieldofview.provider.related.HeaderItem;
import com.lyh.fieldofview.provider.related.RelatedHeader;
import com.lyh.fieldofview.rx.ErrorAction;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2017/3/20.
 */

public class RelatedActivity extends RxAppCompatActivity{

    public static final String ID = "id";

    private MultiTypeAdapter adapter;
    private Items items = new Items();
    private RelatedApi relatedApi;

    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finishAfterTransition());

        RecyclerView list = (RecyclerView) findViewById(R.id.related_list);

        adapter = new MultiTypeAdapter(items);
        list.setAdapter(adapter);

        Register.registerRelatedItem(adapter, this);

        id = getIntent().getIntExtra(ID, id);
        relatedApi = InteressantFactory.getRetrofit().createApi(RelatedApi.class);

        loadRelated();
    }

    private void loadRelated() {
        relatedApi.related(id)
                .compose(bindToLifecycle())
                .filter(related -> related != null)
                .filter(related -> related.itemList != null)
                .map(related -> related.itemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addData, ErrorAction.errorAction(this));
    }

    private void addData(List<ItemList> itemLists) {
        for (ItemList item : itemLists) {
            Header header = item.data.header;
            if (header != null) {
                if (header.description != null) {
                    items.add(new HeaderItem(item.data.header, true));
                } else {
                    items.add(new RelatedHeader(item.data.header, true));
                }
                items.add(new Card(item));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
