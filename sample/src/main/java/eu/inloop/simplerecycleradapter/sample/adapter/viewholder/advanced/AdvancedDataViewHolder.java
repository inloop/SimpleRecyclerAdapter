package eu.inloop.simplerecycleradapter.sample.adapter.viewholder.advanced;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import eu.inloop.simplerecycleradapter.SettableViewHolder;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.MyDataObject;
import eu.inloop.simplerecycleradapter.sample.adapter.model.WrappedMyDataObject;


public class AdvancedDataViewHolder extends SettableViewHolder<WrappedMyDataObject> {

    private TextView mTitle;
    private TextView mText;
    private Button mBtnMore;

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
        mBtnMore = (Button) itemView.findViewById(R.id.btn_more);
        mBtnMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(WrappedMyDataObject data) {
        MyDataObject dataObject = data.getDataObject();
        mTitle.setText(dataObject.getTitle());
        mText.setText(dataObject.getText());
    }

    @Nullable
    @Override
    public List<? extends View> getInnerClickableAreas() {
        return Collections.singletonList(mBtnMore);
    }
}
