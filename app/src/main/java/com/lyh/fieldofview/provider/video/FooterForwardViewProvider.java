package com.lyh.fieldofview.provider.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.lyh.fieldofview.R;

import java.util.concurrent.TimeUnit;

import me.drakeet.multitype.ItemViewProvider;


/**
 * @author zsj
 */

public class FooterForwardViewProvider extends
        ItemViewProvider<FooterForward, FooterForwardViewProvider.Holder> {

    @NonNull
    @Override
    protected Holder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_forward_footer, parent, false);
        return new Holder(view);
    }

    @Override
    protected void onBindViewHolder(
            @NonNull Holder holder, @NonNull FooterForward footerForward) {

        holder.footerText.setText(footerForward.text);
        RxView.clicks(holder.footerText)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> toVideoList(holder.footerText.getContext(), footerForward.id));
    }

    private void toVideoList(Context context, int id) {
//        Intent intent = new Intent(context, VideoListActivity.class);
//        intent.putExtra("id", id);
//        intent.putExtra("trending", true);
//        context.startActivity(intent);
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView footerText;

        public Holder(View itemView) {
            super(itemView);
            footerText = (TextView) itemView.findViewById(R.id.footer_text);
        }
    }
}
