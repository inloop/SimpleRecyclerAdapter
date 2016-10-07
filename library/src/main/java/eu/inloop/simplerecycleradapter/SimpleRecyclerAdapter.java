package eu.inloop.simplerecycleradapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleRecyclerAdapter<T, VH extends SettableViewHolder<T>> extends RecyclerView.Adapter<VH> {

    public interface CreateViewHolder<VH> {
        VH onCreateViewHolder(ViewGroup parent, int viewType);

        int getItemId(int position);

        int getItemViewType(int position);
    }

    public interface ModifyViewHolder<T, VH> {
        void modifyViewHolder(T item, VH viewHolder, int adapterPosition);
    }

    private List<T> mItems;
    private ItemActionListener<T, VH> mActionListener;
    private CreateViewHolder<VH> mCreateViewHolderListener;
    private ModifyViewHolder<T, VH> mModifyViewHolderListener;

    public SimpleRecyclerAdapter(@Nullable ItemActionListener<T, VH> actionListener,
                                 @NonNull CreateViewHolder<VH> createViewHolderListener) {
        mItems = new ArrayList<>();
        mActionListener = actionListener;
        mCreateViewHolderListener = createViewHolderListener;
        setHasStableIds(true);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return mCreateViewHolderListener.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final T item = mItems.get(position);

        holder.setData(item);

        if (mModifyViewHolderListener != null) {
            mModifyViewHolderListener.modifyViewHolder(item, holder, position);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActionListener != null && holder.isClickable()) {
                    mActionListener.onItemClick(item, holder, -1);
                }
            }
        });
    }

    public void setModifyViewHolderListener(@Nullable ModifyViewHolder<T, VH> listener) {
        mModifyViewHolderListener = listener;
    }

    @Override
    public long getItemId(int position) {
        return mCreateViewHolderListener.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mCreateViewHolderListener.getItemViewType(position);
    }

    public void addItem(T item) {
        mItems.add(item);
    }

    public void addItem(int index, T item) {
        mItems.add(index, item);
    }

    public void addItems(List<T> items) {
        addItems(items, false);
    }

    public void addItems(List<T> items, boolean notifyDataSetChanged) {
        mItems.addAll(items);
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public void removeItem(int index) {
        mItems.remove(index);
    }

    public int removeItemById(long id) {
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

    public void clear(boolean notifyDataSetChanged) {
        mItems.clear();
        if (notifyDataSetChanged) {
            notifyDataSetChanged();
        }
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(mItems);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}