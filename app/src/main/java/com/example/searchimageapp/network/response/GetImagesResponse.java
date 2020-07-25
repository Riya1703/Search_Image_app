package com.example.searchimageapp.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetImagesResponse implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("datetime")
    @Expose
    private Integer datetime;
    @SerializedName("cover_width")
    @Expose
    private Integer coverWidth;
    @SerializedName("cover_height")
    @Expose
    private Integer coverHeight;
    @SerializedName("account_url")
    @Expose
    private String accountUrl;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("topic_id")
    @Expose
    private Integer topicId;
    @SerializedName("images_count")
    @Expose
    private Integer imagesCount;
    @SerializedName("in_gallery")
    @Expose
    private Boolean inGallery;
    @SerializedName("images")
    @Expose
    private List<ImageSearch> images = null;

    protected GetImagesResponse(Parcel in) {
        id = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            datetime = null;
        } else {
            datetime = in.readInt();
        }
        if (in.readByte() == 0) {
            coverWidth = null;
        } else {
            coverWidth = in.readInt();
        }
        if (in.readByte() == 0) {
            coverHeight = null;
        } else {
            coverHeight = in.readInt();
        }
        accountUrl = in.readString();
        link = in.readString();
        if (in.readByte() == 0) {
            commentCount = null;
        } else {
            commentCount = in.readInt();
        }
        topic = in.readString();
        if (in.readByte() == 0) {
            topicId = null;
        } else {
            topicId = in.readInt();
        }
        if (in.readByte() == 0) {
            imagesCount = null;
        } else {
            imagesCount = in.readInt();
        }
        byte tmpInGallery = in.readByte();
        inGallery = tmpInGallery == 0 ? null : tmpInGallery == 1;
        images = in.createTypedArrayList(ImageSearch.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        if (datetime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(datetime);
        }
        if (coverWidth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(coverWidth);
        }
        if (coverHeight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(coverHeight);
        }
        dest.writeString(accountUrl);
        dest.writeString(link);
        if (commentCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(commentCount);
        }
        dest.writeString(topic);
        if (topicId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(topicId);
        }
        if (imagesCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imagesCount);
        }
        dest.writeByte((byte) (inGallery == null ? 0 : inGallery ? 1 : 2));
        dest.writeTypedList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GetImagesResponse> CREATOR = new Creator<GetImagesResponse>() {
        @Override
        public GetImagesResponse createFromParcel(Parcel in) {
            return new GetImagesResponse(in);
        }

        @Override
        public GetImagesResponse[] newArray(int size) {
            return new GetImagesResponse[size];
        }
    };

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

    public Integer getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(Integer coverWidth) {
        this.coverWidth = coverWidth;
    }

    public Integer getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(Integer coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(Integer imagesCount) {
        this.imagesCount = imagesCount;
    }

    public Boolean getInGallery() {
        return inGallery;
    }

    public void setInGallery(Boolean inGallery) {
        this.inGallery = inGallery;
    }

    public List<ImageSearch> getImages() {
        return images;
    }

    public void setImages(List<ImageSearch> imageSearches) {
        this.images = imageSearches;
    }
}
