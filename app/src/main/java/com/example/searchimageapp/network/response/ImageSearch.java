package com.example.searchimageapp.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageSearch implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("datetime")
    @Expose
    private Integer datetime;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("animated")
    @Expose
    private Boolean animated;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("in_gallery")
    @Expose
    private Boolean inGallery;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("comment_count")
    @Expose
    private Object commentCount;

    protected ImageSearch(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            datetime = null;
        } else {
            datetime = in.readInt();
        }
        type = in.readString();
        byte tmpAnimated = in.readByte();
        animated = tmpAnimated == 0 ? null : tmpAnimated == 1;
        if (in.readByte() == 0) {
            width = null;
        } else {
            width = in.readInt();
        }
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readInt();
        }
        byte tmpInGallery = in.readByte();
        inGallery = tmpInGallery == 0 ? null : tmpInGallery == 1;
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (datetime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(datetime);
        }
        dest.writeString(type);
        dest.writeByte((byte) (animated == null ? 0 : animated ? 1 : 2));
        if (width == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(width);
        }
        if (height == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(height);
        }
        dest.writeByte((byte) (inGallery == null ? 0 : inGallery ? 1 : 2));
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageSearch> CREATOR = new Creator<ImageSearch>() {
        @Override
        public ImageSearch createFromParcel(Parcel in) {
            return new ImageSearch(in);
        }

        @Override
        public ImageSearch[] newArray(int size) {
            return new ImageSearch[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Integer getDatetime() {
        return datetime;
    }

    public void setDatetime(Integer datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAnimated() {
        return animated;
    }

    public void setAnimated(Boolean animated) {
        this.animated = animated;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getInGallery() {
        return inGallery;
    }

    public void setInGallery(Boolean inGallery) {
        this.inGallery = inGallery;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Object getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Object commentCount) {
        this.commentCount = commentCount;
    }
}
