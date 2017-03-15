package com.lyh.fieldofview;

import android.app.Activity;
import android.view.View;

import com.lyh.fieldofview.model.ItemList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * @author zsj
 */

public class IntentManager {

    public static void flyToMovieDetail(final Activity context,
                                        final ItemList item, final View view) {
        Picasso.with(context).load(item.data.cover.detail)
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
//                        Intent intent = new Intent(context, MovieDetailActivity.class);
//                        intent.putExtra(PROVIDER_ITEM, item);
//                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
//                                context,
//                                Pair.create(view, context.getString(R.string.transition_shot)),
//                                Pair.create(view, context.getString(R.string.transition_shot_background))
//                        );
//                        context.startActivity(intent, options.toBundle());
                    }

                    @Override
                    public void onError() {
//                        Intent intent = new Intent(context, MovieDetailActivity.class);
//                        intent.putExtra(PROVIDER_ITEM, item);
//                        context.startActivity(intent);
                    }
                });
    }
}
