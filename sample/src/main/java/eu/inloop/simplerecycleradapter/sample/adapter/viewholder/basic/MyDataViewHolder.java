package eu.inloop.simplerecycleradapter.sample.adapter.viewholder.basic;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.inloop.simplerecycleradapter.SettableViewHolder;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.MyDataObject;


public class MyDataViewHolder extends SettableViewHolder<MyDataObject> {

    private TextView mTitle;
    private TextView mDescription;

    public MyDataViewHolder(View itemView) {
        super(itemView);
        init();
    }

    public MyDataViewHolder(@NonNull Context context, @LayoutRes int layoutRes, @NonNull ViewGroup parent) {
        super(context, layoutRes, parent);
        init();
    }

    private void init() {
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mDescription = (TextView) itemView.findViewById(R.id.description);
    }

    @Override
    public void setData(@NonNull MyDataObject data) {
        mTitle.setText(data.getTitle());
        mDescription.setText(data.getText());
    }
}
