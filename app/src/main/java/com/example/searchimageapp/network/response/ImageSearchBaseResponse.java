package com.example.searchimageapp.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageSearchBaseResponse {
    private Throwable error;

    @SerializedName("data")
    @Expose
    private ArrayList<GetImagesResponse> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public ImageSearchBaseResponse(Throwable error) {
        this.error = error;
    }

    public ArrayList<GetImagesResponse> getData() {
        return data;
    }

    public void setData(ArrayList<GetImagesResponse> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
