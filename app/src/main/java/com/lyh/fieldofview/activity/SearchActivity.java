package com.lyh.fieldofview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lyh.fieldofview.InteressantFactory;
import com.lyh.fieldofview.R;
import com.lyh.fieldofview.base.ToolbarActivity;
import com.lyh.fieldofview.interesting.TagAdapter;
import com.lyh.fieldofview.rx.ErrorAction;
import com.lyh.fieldofview.utils.SearchApi;
import com.lyh.fieldofview.utils.TagLayoutManager;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2017/3/15.
 */

public class SearchActivity extends ToolbarActivity implements View.OnClickListener{

    public static final String KEYWORD = "keyword";
    private RecyclerView list;
    private EditText searchEdit;

    private TagAdapter adapter;
    private List<String> tags = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = (RecyclerView) findViewById(R.id.recycler_view);
        searchEdit = (EditText) findViewById(R.id.search_edit);
        ImageButton clearButton = (ImageButton) findViewById(R.id.clear_btn);
        clearButton.setOnClickListener(this);

        adapter = new TagAdapter(tags);
        list.setLayoutManager(new TagLayoutManager());
        list.setAdapter(adapter);

        loadTrendingTag();

        keyListener();

        adapter.setOnItemClickListener(position -> startResultActivity(tags.get(position)));
    }

    private void keyListener() {
        searchEdit.setOnKeyListener((v, keyCode, event) -> {
            //onKey will call twice, First: ACTION_DOWN, Second:ACTION_UP
            if (event.getAction() != KeyEvent.ACTION_DOWN) return true;

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (searchEdit.getText().toString().isEmpty()) {
                    Toast.makeText(SearchActivity.this,
                            "Keyword must not empty!", Toast.LENGTH_SHORT).show();
                } else {
                    search(searchEdit.getText().toString());
                }
            } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish();
            }
            return false;
        });
    }

    private void search(String key) {
        startResultActivity(key);
    }

    private void startResultActivity(String keyword) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(KEYWORD, keyword);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void loadTrendingTag() {
        SearchApi trendingApi = InteressantFactory.getRetrofit()
                .createApi(SearchApi.class);

        trendingApi.getTrendingTag()
                .compose(bindToLifecycle())
                .filter(data -> data != null)
                .doOnNext(data -> tags.addAll(data))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    adapter.notifyDataSetChanged();
                }, ErrorAction.errorAction(this));
    }

    @Override
    public void onClick(View view) {
        searchEdit.setText(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
