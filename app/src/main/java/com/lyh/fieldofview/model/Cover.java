package com.lyh.fieldofview.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyh on 2017/3/15.
 */

public class Cover implements Parcelable {

    public String feed;
    public String detail;
    public String blurred;

    protected Cover(Parcel in) {
        feed = in.readString();
        detail = in.readString();
        blurred = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feed);
        dest.writeString(detail);
        dest.writeString(blurred);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel in) {
            return new Cover(in);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };
}
