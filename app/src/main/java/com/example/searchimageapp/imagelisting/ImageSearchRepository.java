package com.example.searchimageapp.imagelisting;

import com.example.searchimageapp.Utils.SingleLiveEvent;
import com.example.searchimageapp.network.RestClient;
import com.example.searchimageapp.network.response.ImageSearchBaseResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageSearchRepository {
    private final static String ERROR_TOAST_MSG="Unable to fetch all the information from server. Please try after some time.";
    private static ImageSearchRepository mInstance;
    private SingleLiveEvent<ImageSearchBaseResponse> liveDataImages = new SingleLiveEvent<>();

    public static ImageSearchRepository getInstance() {
        if (mInstance == null) {
            mInstance = new ImageSearchRepository();
        }
        return mInstance;
    }

    private ImageSearchRepository() {
    }

    public SingleLiveEvent<ImageSearchBaseResponse> getImages(String imageName, Integer currentPage){
        RestClient.getAPIInterface().getImages(currentPage, imageName).enqueue(new Callback<ImageSearchBaseResponse>() {
            @Override
            public void onResponse(Call<ImageSearchBaseResponse> call, Response<ImageSearchBaseResponse> response) {
                if (response.isSuccessful() && response.errorBody() == null){
                    liveDataImages.setValue(response.body());
                }
                else {
                    if (response.errorBody() != null){
                        try{
                            JSONObject jsonError = new JSONObject(response.errorBody().string());
                            liveDataImages.setValue(new ImageSearchBaseResponse(new Exception(jsonError.getString("message"))));
                        }catch (Exception e){
                            liveDataImages.setValue(new ImageSearchBaseResponse(new Exception(ERROR_TOAST_MSG)));
                        }
                    }else{
                        liveDataImages.setValue(new ImageSearchBaseResponse(new Exception(ERROR_TOAST_MSG)));
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageSearchBaseResponse> call, Throwable t) {
                liveDataImages.setValue(new ImageSearchBaseResponse(t));
            }
        });

        return liveDataImages;
    }
}
