package eu.inloop.simplerecycleradapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class SettableViewHolder<R> extends RecyclerView.ViewHolder {

    public SettableViewHolder(View itemView) {
        super(itemView);
    }

    public SettableViewHolder(@NonNull Context context, @LayoutRes int layoutRes, @NonNull ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(layoutRes, parent, false));
    }

    public abstract void setData(R data);

    public boolean isClickable() {
        return true;
    }

    @Nullable
    public List<? extends View> getInnerClickableAreas() {
        return null;
    }

}