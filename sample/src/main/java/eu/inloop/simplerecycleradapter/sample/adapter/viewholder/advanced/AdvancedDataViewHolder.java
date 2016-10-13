package eu.inloop.simplerecycleradapter.sample.adapter.viewholder.advanced;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import eu.inloop.simplerecycleradapter.SettableViewHolder;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.MyDataObject;
import eu.inloop.simplerecycleradapter.sample.adapter.model.WrappedMyDataObject;


public class AdvancedDataViewHolder extends SettableViewHolder<WrappedMyDataObject> {

    private TextView mTitle;
    private TextView mText;
    private ViewGroup mActions;
    private Button mBtnMore, mBtnRemove, mBtnUp, mBtnDown;

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
        mBtnRemove = (Button) itemView.findViewById(R.id.btn_remove);
        mBtnUp = (Button) itemView.findViewById(R.id.btn_move_up);
        mBtnDown = (Button) itemView.findViewById(R.id.btn_move_down);
        mActions = (ViewGroup) itemView.findViewById(R.id.actions);
        mActions.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(@NonNull WrappedMyDataObject data) {
        MyDataObject dataObject = data.getDataObject();
        mTitle.setText(dataObject.getTitle());
        mText.setText(dataObject.getText());
    }

    @Nullable
    @Override
    public List<? extends View> getInnerClickableAreas() {
        return Arrays.asList(mBtnUp, mBtnDown, mBtnRemove, mBtnMore);
    }
}
