package eu.inloop.simplerecycleradapter;

import android.support.annotation.NonNull;
import android.view.View;

public interface ItemLongClickListener<T> {
    boolean onItemLongClick(@NonNull T item, @NonNull SettableViewHolder<T> viewHolder, @NonNull View view);
}