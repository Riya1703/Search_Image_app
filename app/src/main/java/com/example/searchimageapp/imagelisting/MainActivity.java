package com.example.searchimageapp.imagelisting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.searchimageapp.R;
import com.example.searchimageapp.imagedetails.ImageDetailsActivity;
import com.example.searchimageapp.network.response.GetImagesResponse;
import com.example.searchimageapp.network.response.ImageSearchBaseResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ImagesAdapter.ImagesAdapterListener {
    private EditText etSearch;
    private ImageButton ibSearch;
    private RecyclerView rvImages;
    private ArrayList<GetImagesResponse> listImages;
    private ImagesAdapter imagesAdapter;
    private ImageSearchViewModel viewModel;
    private PaginationOnScrollListenerLinearLayout paginationScrollListener;
    private GridLayoutManager gridLayoutManager;
    private boolean isFirstLoad = true;
    private int currentPage = 1;
    private static final int pageSize = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsNInitListeners();
    }

    private void findViewsNInitListeners() {
        viewModel = ViewModelProviders.of(this).get(ImageSearchViewModel.class);
        etSearch = findViewById(R.id.et_search);
        ibSearch = findViewById(R.id.ib_search);
        ibSearch.setOnClickListener(this);
        rvImages = findViewById(R.id.rv_images);
        listImages = new ArrayList<>();

        gridLayoutManager = new GridLayoutManager(this, 3);
        rvImages.setLayoutManager(gridLayoutManager);
        rvImages.setItemAnimator(new DefaultItemAnimator());
        imagesAdapter = new ImagesAdapter(this, listImages,this);
        rvImages.setAdapter(imagesAdapter);
        paginationScrollListener = new PaginationOnScrollListenerLinearLayout(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                listImages.add(null);
                imagesAdapter.notifyItemInserted(listImages.size() - 1);
                getImages(etSearch.getText().toString());
            }
        };
        rvImages.addOnScrollListener(paginationScrollListener);
    }

    private void getImages(String name) {
        viewModel.getImages(name, currentPage).observe(this, new Observer<ImageSearchBaseResponse>() {
            @Override
            public void onChanged(ImageSearchBaseResponse imageSearchBaseResponse) {
                if (imageSearchBaseResponse.getSuccess()
                        && imageSearchBaseResponse.getData() != null
                        && imageSearchBaseResponse.getData().size() > 0)
                {
                    if (!isFirstLoad)
                    {
                        listImages.remove(listImages.size() - 1);
                        imagesAdapter.notifyItemRemoved(listImages.size());
                    }
                    imagesAdapter.addAll(imageSearchBaseResponse.getData());

                    currentPage++;

                    isFirstLoad = false;

                    paginationScrollListener.setLoaded();

                    /*if (listImages.size()>= imageSearchBaseResponse.getData().size())
                        paginationScrollListener.setLastPage();*/
                }
                else
                    Toast.makeText(MainActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_search:
                if (!TextUtils.isEmpty(etSearch.getText().toString().trim()))
                    getImages(etSearch.getText().toString());
                else
                    Toast.makeText(this, "Please enter name to search image.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(int position, GetImagesResponse imageSearch) {
        Intent intent = new Intent(MainActivity.this, ImageDetailsActivity.class);
        intent.putExtra("TAG", imageSearch);
        startActivity(intent);
    }
}
