package eu.inloop.simplerecycleradapter;

import android.support.annotation.NonNull;
import android.view.View;

public interface ItemClickListener<T> {
    void onItemClick(@NonNull T item, @NonNull SettableViewHolder<T> viewHolder, @NonNull View view);
}