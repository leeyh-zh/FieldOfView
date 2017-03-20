package com.lyh.fieldofview.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by lyh on 2017/3/20.
 */

public class ScreenUtils {
    private ScreenUtils(){}

    public static int getWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        Display display = manager.getDefaultDisplay();
        display.getRealSize(size);
        return size.x;
    }

    public static int getHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        Display display = manager.getDefaultDisplay();
        display.getRealSize(size);
        return size.y;
    }
}
