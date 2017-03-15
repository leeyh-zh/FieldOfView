package com.lyh.fieldofview.provider.daily;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lyh.fieldofview.common.Holder;
import com.lyh.fieldofview.model.ItemList;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by lyh on 2017/3/15.
 */

public class DailyViewProvider extends ItemViewProvider<ItemList,Holder> {
    public DailyViewProvider(Activity activity) {
    }

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return null;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull ItemList itemList) {

    }
}
