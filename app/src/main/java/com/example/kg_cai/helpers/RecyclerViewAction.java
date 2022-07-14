package com.example.kg_cai.helpers;

public interface RecyclerViewAction {
    void onViewClicked(int clickedViewId, int clickedItemPosition);
    void onViewLongClicked(int clickedViewId, int clickedItemPosition);
}
