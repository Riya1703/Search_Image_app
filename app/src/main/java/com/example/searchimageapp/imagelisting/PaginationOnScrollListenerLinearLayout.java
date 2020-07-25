package com.example.searchimageapp.imagelisting;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Pagination on scroll listener linear layout.
 */
public abstract class PaginationOnScrollListenerLinearLayout extends RecyclerView.OnScrollListener {

    /**
     * The Layout manager.
     */
    LinearLayoutManager layoutManager;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private boolean isLastPage;

    /**
     * Supporting {@link LinearLayoutManager}
     *
     * @param layoutManager the layout manager
     */
    public PaginationOnScrollListenerLinearLayout(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    /**
     * Supporting {@link GridLayoutManager}
     *
     * @param layoutManager the layout manager
     */
    public PaginationOnScrollListenerLinearLayout(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = layoutManager.getItemCount();
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        if (!isLastPage && !loading && totalItemCount < (lastVisibleItem + visibleThreshold)) {

            loadMoreItems();
            loading=true;
        }
    }

    /**
     * Sets loaded.
     */
    public void setLoaded() {
        loading = false;
    }

    /**
     * Sets last page.
     */
    public void setLastPage()
    {
        isLastPage=true;
    }

    public void resetLastPage()
    {
        isLastPage=false;
    }

    /**
     * Load more items.
     */
    protected abstract void loadMoreItems();



}