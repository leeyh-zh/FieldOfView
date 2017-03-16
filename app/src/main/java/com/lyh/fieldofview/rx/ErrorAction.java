package com.lyh.fieldofview.rx;

import android.content.Context;
import android.widget.Toast;

import rx.functions.Action1;

/**
 * Created by lyh on 2017/3/16.
 */

public class ErrorAction {
    private ErrorAction() {
        throw new AssertionError("No instances.");
    }

    public static Action1<Throwable> errorAction(final Context context) {
        return throwable -> {
            throwable.printStackTrace();
            Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        };
    }
}
