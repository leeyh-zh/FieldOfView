package com.lyh.fieldofview.provider.daily;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.RxView;
import com.lyh.fieldofview.IntentManager;
import com.lyh.fieldofview.R;
import com.lyh.fieldofview.common.Holder;
import com.lyh.fieldofview.model.ItemList;

import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by lyh on 2017/3/15.
 */

public class DailyViewProvider extends ItemViewProvider<ItemList, Holder> {

    private static final String VIDEO_TAG = "video";

    private Activity activity;

    public DailyViewProvider(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    protected Holder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new Holder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, @NonNull ItemList itemList) {

        if (itemList.type.contains(VIDEO_TAG)) {
            holder.movieContent.setVisibility(View.VISIBLE);
            holder.movieAlbum.setOriginalSize(16, 9);
            Glide.with(holder.movieAlbum.getContext())
                    .load(itemList.data.cover.detail)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.movieAlbum);
            holder.movieDesc.setText(itemList.data.title);

            if (itemList.data.author != null) {
                holder.tag.setVisibility(View.VISIBLE);
                holder.tag.setText(itemList.data.author.name);
            } else {
                holder.tag.setVisibility(View.GONE);
            }
        } else {
            holder.movieContent.setVisibility(View.GONE);
        }

        RxView.clicks(holder.movieContent)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> fly(holder.movieAlbum, itemList));
    }

    private void fly(View view, ItemList itemList) {
        IntentManager.flyToMovieDetail(activity, itemList, view);
    }
}
