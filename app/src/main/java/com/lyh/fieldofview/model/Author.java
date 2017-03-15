package com.lyh.fieldofview.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyh on 2017/3/15.
 */

public class Author implements Parcelable {

    public int id;
    public String icon;
    public String name;
    public String description;
    public int videoNum;

    protected Author(Parcel in) {
        id = in.readInt();
        icon = in.readString();
        name = in.readString();
        description = in.readString();
        videoNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(videoNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
