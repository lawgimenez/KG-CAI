package com.example.kg_cai;

public interface RecyclerViewAction {
    void onViewClicked(int clickedViewId, int clickedItemPosition);
    void onViewLongClicked(int clickedViewId, int clickedItemPosition);
}
