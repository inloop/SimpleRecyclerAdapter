package eu.inloop.simplerecycleradapter;

import android.support.annotation.NonNull;

public interface ItemActionListener<T, VH> {
    void onItemClick(@NonNull T item, @NonNull VH viewHolder, int id);
}