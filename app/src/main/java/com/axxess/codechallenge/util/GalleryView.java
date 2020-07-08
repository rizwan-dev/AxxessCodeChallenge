    package com.axxess.codechallenge.util;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;


public class GalleryView  implements Parcelable{
    private String id;
    private String title;
    private String imgUrl;

    public GalleryView(String id, String title, String imgUrl) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public GalleryView() {
    }

    public static DiffUtil.ItemCallback<GalleryView> DIFF_CALLBACK = new DiffUtil.ItemCallback<GalleryView>() {
        @Override
        public boolean areItemsTheSame(@NonNull GalleryView oldItem, @NonNull GalleryView newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull GalleryView oldItem, @NonNull GalleryView newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected GalleryView(Parcel in) {
        id = in.readString();
        title = in.readString();
        imgUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(imgUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GalleryView> CREATOR = new Creator<GalleryView>() {
        @Override
        public GalleryView createFromParcel(Parcel in) {
            return new GalleryView(in);
        }

        @Override
        public GalleryView[] newArray(int size) {
            return new GalleryView[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
