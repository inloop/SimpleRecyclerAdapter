package eu.inloop.simplerecycleradapter.sample.adapter.viewholder.advanced;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.inloop.simplerecycleradapter.SettableViewHolder;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.WrappedMyDataObject;


public class HeaderViewHolder extends SettableViewHolder<WrappedMyDataObject> {

    private TextView mTitle;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        init();
    }

    public HeaderViewHolder(@NonNull Context context, @LayoutRes int layoutRes, @NonNull ViewGroup parent) {
        super(context, layoutRes, parent);
        init();
    }

    private void init() {
        mTitle = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void setData(WrappedMyDataObject data) {
        mTitle.setText(data.getHeaderTitle());
    }

    @Override
    public boolean isClickable() {
        return false;
    }
}
