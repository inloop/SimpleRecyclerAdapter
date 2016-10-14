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
public class SimpleRecyclerAdapter<T> extends RecyclerView.Adapter<SettableViewHolder<T>> {

    public static abstract class CreateViewHolder<T> {
        @NonNull
        protected abstract SettableViewHolder<T> onCreateViewHolder(final ViewGroup parent, final int viewType);

        protected long getItemId(@NonNull final T item, final int position) {
            return RecyclerView.NO_ID;
        }

        protected int getItemViewType(final int position) {
            return 0;
        }

        protected void modifyViewHolder(@NonNull final T item, @NonNull final SettableViewHolder<T> viewHolder, final int adapterPosition) {
        }
    }

    @NonNull
    private final List<T> mItems;

    @Nullable
    private final OnItemClickListener mBindViewClickListener;

    @Nullable
    private OnItemLongClickListener mBindViewLongClickListener;

    @Nullable
    private final ItemClickListener<T> mClickListener;

    @Nullable
    private ItemLongClickListener<T> mLongClickListener;

    @NonNull
    private final CreateViewHolder<T> mCreateViewHolderListener;

    public SimpleRecyclerAdapter(final @Nullable ItemClickListener<T> onClickListener,
                                 final @NonNull CreateViewHolder<T> createViewHolderListener) {
        this(onClickListener, createViewHolderListener, false);
    }

    public SimpleRecyclerAdapter(final @Nullable ItemClickListener<T> onClickListener,
                                 final @NonNull CreateViewHolder<T> createViewHolderListener,
                                 final boolean hasStableIds) {
        mItems = new ArrayList<>();
        mClickListener = onClickListener;
        mCreateViewHolderListener = createViewHolderListener;
        if (onClickListener != null) {
            mBindViewClickListener = new OnItemClickListener();
        } else {
            mBindViewClickListener = null;
        }
        setHasStableIds(hasStableIds);
    }

    public void setLongClickListener(@Nullable ItemLongClickListener<T> longClickListener) {
        if (mLongClickListener != null && mBindViewLongClickListener == null) {
            mBindViewLongClickListener = new OnItemLongClickListener();
        }
        this.mLongClickListener = longClickListener;
    }

    @Override
    public SettableViewHolder<T> onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return mCreateViewHolderListener.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final SettableViewHolder<T> holder, final int position) {
        final T item = mItems.get(position);

        holder.setData(item);

        mCreateViewHolderListener.modifyViewHolder(item, holder, position);

        if (mClickListener != null || mLongClickListener != null) {
            if (holder.isClickable()) {
                setClickListener(holder.itemView, item, holder);
            } else {
                holder.itemView.setOnClickListener(null);
                holder.itemView.setOnLongClickListener(null);
            }

            final List<? extends View> clickableAreas = holder.getCachedClickableAreas();
            if (clickableAreas != null) {
                for (final View area : clickableAreas) {
                    setClickListener(area, item, holder);
                }
            }
        }
    }

    private void setClickListener(@NonNull final View view, @NonNull final T item, @NonNull final SettableViewHolder<T> holder) {
        @SuppressWarnings("unchecked")
        TagWrapper<T> tagWrapper = (TagWrapper<T>) view.getTag();
        if (tagWrapper == null) {
            tagWrapper = new TagWrapper<>(item, holder);
        } else {
            tagWrapper.item = item;
            tagWrapper.viewholder = holder;
        }
        view.setTag(tagWrapper);

        // Only set a listener if someone is actually listening
        if (mClickListener != null) {
            view.setOnClickListener(mBindViewClickListener);
        }

        if (mLongClickListener != null) {
            view.setOnLongClickListener(mBindViewLongClickListener);
        }
    }

    private static class TagWrapper <T> {
        T item;
        SettableViewHolder<T> viewholder;

        public TagWrapper(T item, SettableViewHolder<T>  viewholder) {
            this.item = item;
            this.viewholder = viewholder;
        }
    }

    private final class OnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View view) {
            if (mClickListener != null) {
                @SuppressWarnings("unchecked")
                final TagWrapper<T> tagWrapper = (TagWrapper<T>) view.getTag();
                mClickListener.onItemClick(tagWrapper.item, tagWrapper.viewholder, view);
            }
        }
    }

    private final class OnItemLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) {
                @SuppressWarnings("unchecked")
                final TagWrapper<T> tagWrapper = (TagWrapper<T>) view.getTag();
                return mLongClickListener.onItemLongClick(tagWrapper.item, tagWrapper.viewholder, view);
            }
            return false;
        }
    }

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

    @NonNull
    public T removeItem(@IntRange(from = 0) final int index) {
        return removeItem(index, false);
    }

    @NonNull
    public T removeItem(@IntRange(from = 0) final int index, boolean notifyRemoved) {
        T removedItem = mItems.remove(index);
        if (notifyRemoved) notifyItemRemoved(index);
        return removedItem;
    }

    public int removeItem(@NonNull final T object) {
        return removeItem(object, false);
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
