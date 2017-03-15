package com.lyh.fieldofview;

import android.app.Activity;

import com.lyh.fieldofview.model.Category;
import com.lyh.fieldofview.model.ItemList;
import com.lyh.fieldofview.provider.daily.CategoryViewProvider;
import com.lyh.fieldofview.provider.daily.DailyViewProvider;
import com.lyh.fieldofview.provider.related.Card;
import com.lyh.fieldofview.provider.related.CardViewProvider;
import com.lyh.fieldofview.provider.related.HeaderItem;
import com.lyh.fieldofview.provider.related.HeaderViewProvider;
import com.lyh.fieldofview.provider.related.RelatedHeader;
import com.lyh.fieldofview.provider.related.RelatedHeaderViewProvider;
import com.lyh.fieldofview.provider.video.FooterForward;
import com.lyh.fieldofview.provider.video.FooterForwardViewProvider;
import com.lyh.fieldofview.provider.video.VideoViewProvider;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by lyh on 2017/3/15.
 */

public class Register {

    public  static void registerItem(MultiTypeAdapter adapter, Activity activity){
        adapter.register(Category.class,new CategoryViewProvider());
        adapter.register(ItemList.class,new DailyViewProvider(activity));
    }

    public static void registerRelatedItem(MultiTypeAdapter adapter, Activity context) {
        registerCommonItem(adapter, context);
    }

    public static void registerFindItem(MultiTypeAdapter adapter, Activity context) {
        adapter.register(FooterForward.class, new FooterForwardViewProvider());
        adapter.register(Category.class, new CategoryViewProvider());
        adapter.register(ItemList.class, new VideoViewProvider(context));
        registerCommonItem(adapter, context);
    }

    public static void registerAuthorItem(MultiTypeAdapter adapter, Activity context) {
        adapter.register(Category.class, new CategoryViewProvider());
        adapter.register(ItemList.class, new VideoViewProvider(context));
        registerCommonItem(adapter, context);
    }

    private static void registerCommonItem(MultiTypeAdapter adapter, Activity context) {
        adapter.register(HeaderItem.class, new HeaderViewProvider());
        adapter.register(Card.class, new CardViewProvider(context));
        adapter.register(RelatedHeader.class, new RelatedHeaderViewProvider());
    }
}
