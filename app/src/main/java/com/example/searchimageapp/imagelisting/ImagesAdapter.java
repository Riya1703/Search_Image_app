package com.example.searchimageapp.imagelisting;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.searchimageapp.R;
import com.example.searchimageapp.network.response.GetImagesResponse;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ArrayList<GetImagesResponse> listImages;
    private LayoutInflater mLayoutInflater;
    private  ImagesAdapterListener imagesAdapterListener;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    public ImagesAdapter(Context mContext, ArrayList<GetImagesResponse> listImages, ImagesAdapterListener imagesAdapterListener) {
        this.mContext = mContext;
        this.listImages = listImages;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.imagesAdapterListener = imagesAdapterListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case ITEM:
                View view = mLayoutInflater.inflate(R.layout.item_image, parent, false);
                viewHolder= new ImageAdapterViewHolder(view);
                break;

            case LOADING:
                View viewLoad = mLayoutInflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoad);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                ImageAdapterViewHolder imageAdapterViewHolder = (ImageAdapterViewHolder) holder;
                GetImagesResponse imageSearch = listImages.get(position);

                if (imageSearch.getImages() != null && imageSearch.getImages().size() > 0 && imageSearch.getImages().get(0).getLink() != null){
                    Glide.with(mContext).load(imageSearch.getImages().get(0).getLink()).error(R.drawable.ic_launcher_foreground)
                            .placeholder(R.drawable.ic_launcher_foreground).into(imageAdapterViewHolder.ivImage);
                }
                break;

            case LOADING:
                LoadingViewHolder progressBarViewHolder = (LoadingViewHolder) holder;
                progressBarViewHolder.pbLoading.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(mContext, R.color.rippleColor), PorterDuff.Mode.MULTIPLY);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listImages.get(position) != null ? ITEM : LOADING;
    }

    public void add(GetImagesResponse item) {
        listImages.add(item);
        notifyItemInserted(listImages.size());
    }

    public void addAll(ArrayList<GetImagesResponse> listImages) {
        for (GetImagesResponse item : listImages) {
            add(item);
        }
    }

    public class ImageAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivImage;

        ImageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            findViewsNInitListeners(itemView);
        }

        private void findViewsNInitListeners(View itemView) {
            ivImage = itemView.findViewById(R.id.iv_image_tile);
            ivImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_image_tile) {
                imagesAdapterListener.onItemClick(getAdapterPosition(), listImages.get(getAdapterPosition()));
            }
        }
    }

    protected static class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar pbLoading;

        LoadingViewHolder(View itemView) {
            super(itemView);
            pbLoading = itemView.findViewById(R.id.pb_loading);
        }
    }

    public interface ImagesAdapterListener
    {
        void onItemClick(int position, GetImagesResponse imageSearch);
    }
}
