package eu.inloop.simplerecycleradapter;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SimpleRecyclerAdapter<T, VH extends SettableViewHolder<T>> extends RecyclerView.Adapter<VH> {

    @SuppressWarnings("WeakerAccess")
    public static abstract class CreateViewHolder<T, VH> {
        @NonNull
        protected abstract VH onCreateViewHolder(final ViewGroup parent, final int viewType);

        protected long getItemId(@NonNull final T item, final int position) {
            return RecyclerView.NO_ID;
        }

        protected int getItemViewType(final int position) {
            return 0;
        }

        protected void modifyViewHolder(@NonNull final T item, @NonNull final VH viewHolder, final int adapterPosition) {
        }
    }

    @NonNull
    private final List<T> mItems;

    @Nullable
    private final ItemActionListener<T, VH> mActionListener;

    @NonNull
    private final CreateViewHolder<T, VH> mCreateViewHolderListener;

    public SimpleRecyclerAdapter(final @Nullable ItemActionListener<T, VH> actionListener,
                                 final @NonNull CreateViewHolder<T, VH> createViewHolderListener) {
        this(actionListener, createViewHolderListener, false);
    }

    public SimpleRecyclerAdapter(final @Nullable ItemActionListener<T, VH> actionListener,
                                 final @NonNull CreateViewHolder<T, VH> createViewHolderListener,
                                 final boolean hasStableIds) {
        mItems = new ArrayList<>();
        mActionListener = actionListener;
        mCreateViewHolderListener = createViewHolderListener;
        setHasStableIds(hasStableIds);
    }

    @Override
    public VH onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return mCreateViewHolderListener.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        final T item = mItems.get(position);

        holder.setData(item);

        mCreateViewHolderListener.modifyViewHolder(item, holder, position);

        if (mActionListener != null) {
            if (holder.isClickable()) {
                setClickListener(holder.itemView, item, holder);
            } else {
                holder.itemView.setOnClickListener(null);
            }

            final List<? extends View> clickableAreas = holder.getCachedClickableAreas();
            if (clickableAreas != null) {
                for (final View area : clickableAreas) {
                    setClickListener(area, item, holder);
                }
            }
        }
    }

    private void setClickListener(@NonNull final View view, @NonNull final T item, @NonNull final VH holder) {
        @SuppressWarnings("unchecked")
        TagWrapper<T,VH> tagWrapper = (TagWrapper<T, VH>) view.getTag();
        if (tagWrapper == null) {
            tagWrapper = new TagWrapper<>(item, holder);
        } else {
            tagWrapper.item = item;
            tagWrapper.viewholder = holder;
        }
        view.setTag(tagWrapper);
        view.setOnClickListener(mOnClickListener);
    }

    private static class TagWrapper <T, VH> {
        T item;
        VH viewholder;

        public TagWrapper(T item, VH viewholder) {
            this.item = item;
            this.viewholder = viewholder;
        }
    }

    @NonNull
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            if (mActionListener != null) {
                @SuppressWarnings("unchecked")
                final TagWrapper<T, VH> tagWrapper = (TagWrapper<T, VH>) view.getTag();
                mActionListener.onItemClick(tagWrapper.item, tagWrapper.viewholder, view);
            }
        }
    };

    @Override
    public long getItemId(@IntRange(from = 0) final int position) {
        return mCreateViewHolderListener.getItemId(mItems.get(position), position);
    }

    @Override
    public int getItemViewType(@IntRange(from = 0) final int position) {
        return mCreateViewHolderListener.getItemViewType(position);
    }

    public void addItem(@NonNull final T item) {
        addItem(item, false);
    }

    public void addItem(@NonNull final T item, boolean notifyInserted) {
        mItems.add(item);
        if (notifyInserted) notifyItemInserted(mItems.size() - 1);
    }

    public void addItem(@IntRange(from = 0) final int index, @NonNull final T item) {
        addItem(index, item, false);
    }

    public void addItem(@IntRange(from = 0) final int index, @NonNull final T item, boolean notifyInserted) {
        mItems.add(index, item);
        if (notifyInserted) notifyItemInserted(index);
    }

    public void addItems(@NonNull final List<T> items) {
        addItems(items, false);
    }

    public void addItems(@NonNull final List<T> items, boolean notifyInserted) {
        mItems.addAll(items);
        if (notifyInserted) notifyItemRangeInserted(mItems.size() - items.size() - 1, items.size());
    }

    public void addItems(@IntRange(from = 0) final int index, @NonNull final List<T> items) {
        addItems(index, items, false);
    }

    public void addItems(@IntRange(from = 0) final int index, @NonNull final List<T> items, boolean notifyInserted) {
        mItems.addAll(index, items);
        if (notifyInserted) notifyItemRangeInserted(index, items.size());
    }

    public void swapItem(@IntRange(from = 0) final int firstIndex, @IntRange(from = 0) final int secondIndex) {
        swapItem(firstIndex, secondIndex, false);
    }

    public void swapItem(@IntRange(from = 0) final int firstIndex,
                         @IntRange(from = 0) final int secondIndex, boolean notifyMoved) {
        Collections.swap(mItems, firstIndex, secondIndex);
        if (notifyMoved) notifyItemMoved(firstIndex, secondIndex);
    }

    @Nullable
    public T replaceItem(@IntRange(from = 0) final int index, @NonNull final T item) {
        return replaceItem(index, item, false);
    }

    @Nullable
    public T replaceItem(@IntRange(from = 0) final int index, @NonNull final T item, boolean notifyChanged) {
        T prevItem = mItems.set(index, item);
        if (notifyChanged) notifyItemChanged(index);
        return prevItem;
    }

    public boolean replaceItems(@NonNull final List<T> items) {
        return replaceItems(items, false);
    }

    public boolean replaceItems(@NonNull final List<T> items, boolean notifyDataSetChanged) {
        mItems.clear();
        boolean added = mItems.addAll(items);
        if (notifyDataSetChanged) notifyDataSetChanged();
        return added;
    }

    public void removeItem(@IntRange(from = 0) final int index) {
        removeItem(index, false);
    }

    @NonNull
    public T removeItem(@IntRange(from = 0) final int index, boolean notifyRemoved) {
        T removedItem = mItems.remove(index);
        if (notifyRemoved) notifyItemRemoved(index);
        return removedItem;
    }

    public void removeItem(@NonNull final T object) {
        removeItem(object, false);
    }

    public int removeItem(@NonNull final T object, boolean notifyRemoved) {
        int itemIndex = mItems.indexOf(object);
        if (itemIndex != -1) {
            mItems.remove(itemIndex);
            if (notifyRemoved) notifyItemRemoved(itemIndex);
        }
        return itemIndex;
    }

    public int removeItemById(final long id) {
        return removeItemById(id, false);
    }

    public int removeItemById(final long id, boolean notifyRemoved) {
        for (int i = 0; i < mItems.size(); i++) {
            if (getItemId(i) == id) {
                removeItem(i, notifyRemoved);
                return i;
            }
        }
        return -1;
    }

    public boolean hasItemWithId(final long id) {
        for (int i = 0; i < mItems.size(); i++) {
            if (getItemId(i) == id) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        clear(false);
    }

    public void clear(boolean notifyDataSetChanged) {
        mItems.clear();
        if (notifyDataSetChanged) notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    @NonNull
    public T getItem(@IntRange(from = 0) final int position) {
        return mItems.get(position);
    }

    @NonNull
    public List<T> getItems() {
        return Collections.unmodifiableList(mItems);
    }

    @Override
    @IntRange(from = 0)
    public int getItemCount() {
        return mItems.size();
    }

    public void sortItems(@NonNull final Comparator<T> comparator) {
        sortItems(comparator, false);
    }

    public void sortItems(@NonNull final Comparator<T> comparator, boolean notifyDataSetChanged) {
        Collections.sort(mItems, comparator);
        if (notifyDataSetChanged) notifyDataSetChanged();
    }
}
