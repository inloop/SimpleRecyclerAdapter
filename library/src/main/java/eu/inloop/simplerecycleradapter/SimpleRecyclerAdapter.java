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
    }

    @SuppressWarnings("WeakerAccess")
    public interface ModifyViewHolder<T, VH> {
        void modifyViewHolder(final T item, final VH viewHolder, final int adapterPosition);
    }

    @NonNull
    private final List<T> mItems;

    @Nullable
    private final ItemActionListener<T, VH> mActionListener;

    @NonNull
    private final CreateViewHolder<T, VH> mCreateViewHolderListener;

    @Nullable
    private ModifyViewHolder<T, VH> mModifyViewHolderListener;

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

        if (mModifyViewHolderListener != null) {
            mModifyViewHolderListener.modifyViewHolder(item, holder, position);
        }

        if (mActionListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (holder.isClickable()) {
                        mActionListener.onItemClick(item, holder, -1);
                    }
                }
            });
        }
    }

    public void setModifyViewHolderListener(final @Nullable ModifyViewHolder<T, VH> listener) {
        mModifyViewHolderListener = listener;
    }

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
        addItems(items, false);
    }

    public void addItems(final List<T> items, final boolean notifyDataSetChanged) {
        mItems.addAll(items);
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void removeItem(final int index) {
        mItems.remove(index);
    }

    public int removeItemById(final long id) {
        for (int i = 0; i < mItems.size(); i++) {
            if (getItemId(i) == id) {
                mItems.remove(i);
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        clear(false);
    }

    public void clear(final boolean notifyDataSetChanged) {
        mItems.clear();
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
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
}