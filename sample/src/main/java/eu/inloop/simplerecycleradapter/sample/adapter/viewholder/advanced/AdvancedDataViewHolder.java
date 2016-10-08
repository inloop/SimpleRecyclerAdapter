package eu.inloop.simplerecycleradapter.sample.adapter.viewholder.advanced;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.inloop.simplerecycleradapter.SettableViewHolder;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.MyDataObject;
import eu.inloop.simplerecycleradapter.sample.adapter.model.WrappedMyDataObject;


public class AdvancedDataViewHolder extends SettableViewHolder<WrappedMyDataObject> {

    private TextView mTitle;
    private TextView mText;

    public AdvancedDataViewHolder(View itemView) {
        super(itemView);
        init();
    }

    public AdvancedDataViewHolder(@NonNull Context context, @LayoutRes int layoutRes, @NonNull ViewGroup parent) {
        super(context, layoutRes, parent);
        init();
    }

    private void init() {
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mText = (TextView) itemView.findViewById(R.id.description);
    }

    @Override
    public void setData(WrappedMyDataObject data) {
        MyDataObject dataObject = data.getDataObject();
        mTitle.setText(dataObject.getTitle());
        mText.setText(dataObject.getText());
    }

}
