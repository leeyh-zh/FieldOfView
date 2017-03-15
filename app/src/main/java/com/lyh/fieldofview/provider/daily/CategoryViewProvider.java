package com.lyh.fieldofview.provider.daily;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyh.fieldofview.R;
import com.lyh.fieldofview.model.Category;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by lyh on 2017/3/15.
 */

public class CategoryViewProvider extends ItemViewProvider<Category, CategoryViewProvider.DateHolder> {
    @NonNull
    @Override
    protected CategoryViewProvider.DateHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_category_title, parent, false);
        return new DateHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewProvider.DateHolder holder, @NonNull Category category) {
        holder.category.setText(category.categoryTitle);
    }

    class DateHolder extends RecyclerView.ViewHolder {
        TextView category;

        public DateHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
