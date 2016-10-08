package eu.inloop.simplerecycleradapter;

import android.support.annotation.NonNull;
import android.view.View;

public interface ItemActionListener<T, VH> {
    void onItemClick(@NonNull T item, @NonNull VH viewHolder, @NonNull View view);
}