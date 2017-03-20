package com.lyh.fieldofview.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/**
 * Created by lyh on 2017/3/20.
 */

public class InsetsFrameLayout extends FrameLayout {
    public InsetsFrameLayout(@NonNull Context context) {
        super(context, null);
    }

    public InsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public InsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                int r = windowInsets.getSystemWindowInsetRight();

                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight() + r,
                        getPaddingBottom());
                setOnApplyWindowInsetsListener(null);
                return windowInsets.consumeSystemWindowInsets();
            }
        });
    }
}
