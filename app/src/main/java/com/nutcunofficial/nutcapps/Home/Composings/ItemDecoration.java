package com.nutcunofficial.nutcapps.Home.Composings;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.util.Dp2PixelConverter;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private final int margin;

    public ItemDecoration(int margin) {
        this.margin = Dp2PixelConverter.convertDpToPixel(margin);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(margin,margin,margin,margin);
    }
}
