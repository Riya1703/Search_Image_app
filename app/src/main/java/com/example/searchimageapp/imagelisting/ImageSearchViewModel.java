package com.example.searchimageapp.imagelisting;

import android.app.Application;

import com.example.searchimageapp.Utils.SingleLiveEvent;
import com.example.searchimageapp.imagelisting.ImageSearchRepository;
import com.example.searchimageapp.network.response.ImageSearchBaseResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ImageSearchViewModel extends AndroidViewModel {

    public ImageSearchViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<ImageSearchBaseResponse> getImages(String imageName, Integer currentPage){
        return ImageSearchRepository.getInstance().getImages(imageName, currentPage);
    }

}
