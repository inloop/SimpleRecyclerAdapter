package eu.inloop.simplerecycleradapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class SimpleRecyclerAdapter<T, VH extends SettableViewHolder<T>> extends RecyclerView.Adapter<VH> {

    @SuppressWarnings("WeakerAccess")
    public static abstract class CreateViewHolder<T, VH> {
        protected abstract VH onCreateViewHolder(final ViewGroup parent, final int viewType);

        protected long getItemId(final T item, final int position) {
            return RecyclerView.NO_ID;
        }

        protected int getItemViewType(final int position) {
            return 0;
        }

        protected void modifyViewHolder(final T item, final VH viewHolder, final int adapterPosition) {
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
    public void onBindViewHolder(final VH holder, final int position) {
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
    public long getItemId(final int position) {
        return mCreateViewHolderListener.getItemId(mItems.get(position), position);
    }

    @Override
    public int getItemViewType(final int position) {
        return mCreateViewHolderListener.getItemViewType(position);
    }

    public void addItem(final T item) {
        mItems.add(item);
    }

    public void addItem(final int index, final T item) {
        mItems.add(index, item);
    }

    public void addItems(final List<T> items) {
        mItems.addAll(items);
    }

    public void addItems(final int index, final List<T> items) {
        mItems.addAll(index, items);
    }

    public void swapItem(final int firstIndex, final int secondIndex) {
        Collections.swap(mItems, firstIndex, secondIndex);
    }
    
    @Nullable
    public T replaceItem(final int index, final T item) {
        return mItems.set(index, item);
    }

    public void removeItem(final int index) {
        mItems.remove(index);
    }

    public void removeItem(final T object) {
        mItems.remove(object);
    }

    public int removeItemById(final long id) {
        for (int i = 0; i < mItems.size(); i++) {
            if (getItemId(i) == id) {
                removeItem(i);
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
        mItems.clear();
    }

    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    public T getItem(final int position) {
        return mItems.get(position);
    }

    @NonNull
    public List<T> getItems() {
        return Collections.unmodifiableList(mItems);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    
    public void sortItems(@NonNull final Comparator<T> comparator) {
        Collections.sort(mItems, comparator);
    }
}
