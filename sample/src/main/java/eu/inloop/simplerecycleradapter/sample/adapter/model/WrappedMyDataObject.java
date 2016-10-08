package eu.inloop.simplerecycleradapter.sample.adapter.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class WrappedMyDataObject {

    public static final int ITEM_TYPE_NORMAL = 1;
    public static final int ITEM_TYPE_HEADER = 2;

    private final MyDataObject mDataObject;
    private final String mHeaderTitle;
    private final int mType;

    private WrappedMyDataObject(int type, @Nullable MyDataObject dataObject, @Nullable String headerTitle) {
        mDataObject = dataObject;
        mHeaderTitle = headerTitle;
        mType = type;
    }

    public static WrappedMyDataObject initDataItem(@NonNull MyDataObject dataObject) {
        return new WrappedMyDataObject(ITEM_TYPE_NORMAL, dataObject, null);
    }

    public static WrappedMyDataObject initHeaderItem(@NonNull String headerTitle) {
        return new WrappedMyDataObject(ITEM_TYPE_HEADER, null, headerTitle);
    }

    public int getType() {
        return mType;
    }

    public MyDataObject getDataObject() {
        return mDataObject;
    }

    public String getHeaderTitle() {
        return mHeaderTitle;
    }
}
