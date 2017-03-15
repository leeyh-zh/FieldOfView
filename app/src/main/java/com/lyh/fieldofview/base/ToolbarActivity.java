package com.lyh.fieldofview.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.lyh.fieldofview.R;
import com.lyh.fieldofview.activity.SearchActivity;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by lyh on 2017/3/15.
 */

public abstract class ToolbarActivity extends RxAppCompatActivity {
    public Toolbar toolbar;
    public ActionBar ab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int setLayoutId();

    public void toSearch(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        startActivity(intent);
    }
}
