SimpleRecyclerAdapter
================

Writing a RecyclerView adapter is a lot of boiler-plate code that is doing the same thing in most cases. This library provides you an universal SimpleRecyclerAdapter, which you can use directly. 
You can bind any list of arbitrary objects.
The adapter was implemented with performance in mind. No new objects are created during the binding process (except for the first visible rows and then the objects are reused).

How to implement
--------
Very easy, to create a new RecyclerViewAdapter, you only need to make an instance of our SimpleRecyclerAdapter (you don't need to extend this class):

```java
mRecyclerAdapter = new SimpleRecyclerAdapter<>(mOnClickListener, new SimpleRecyclerAdapter.CreateViewHolder<MyDataObject>() {
        @Override
        public SettableViewHolder<MyDataObject> onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new MyDataViewHolder(BasicAdapterActivity.this, R.layout.item_mydata, parent);
        }
});
```
Then create a standard ViewHolder, extending [SettableViewHolder](https://github.com/inloop/SimpleRecyclerAdapter/blob/master/library/src/main/java/eu/inloop/simplerecycleradapter/SettableViewHolder.java). You only need to implement the setData() method.

```java
public class MyDataViewHolder extends SettableViewHolder<MyDataObject> {

    // constructor omitted

    @Override
    public void setData(@NonNull MyDataObject data) {
        mTitle.setText(data.getTitle());
        mDescription.setText(data.getText());
    }
}
```

There is also support for multiple clickable areas (see [AdvancedDataViewHolder example](https://github.com/inloop/SimpleRecyclerAdapter/blob/master/sample/src/main/java/eu/inloop/simplerecycleradapter/sample/adapter/viewholder/advanced/AdvancedDataViewHolder.java)), multiple view-types, long-press.

Advanced use-case
--------
Take a look at the sample [AdvancedAdapterActivity](https://github.com/inloop/SimpleRecyclerAdapter/blob/master/sample/src/main/java/eu/inloop/simplerecycleradapter/sample/activity/AdvancedAdapterActivity.java). 


<kbd><img src="/website/adapter_advanced.gif" height="500px" title="Advanced showcase" /></kbd>

The code to achieve this:

```java
@Override
public void onItemClick(@NonNull WrappedMyDataObject item, @NonNull SettableViewHolder<WrappedMyDataObject> viewHolder, @NonNull View view) {
    if (item.getType() == WrappedMyDataObject.ITEM_TYPE_NORMAL) {
        MyDataObject dataObject = item.getDataObject();
        int itemPos = viewHolder.getAdapterPosition();

        switch (view.getId()) {
            case R.id.btn_more:
                setTitle("Action clicked on item: " + dataObject.getTitle());
                break;
            case R.id.btn_remove:
                mRecyclerAdapter.removeItem(item, true);
                break;
            case R.id.btn_move_up:
                mRecyclerAdapter.swapItem(itemPos, Math.max(0, itemPos - 1), true);
                break;
            case R.id.btn_move_down:
                int maxIndex = mRecyclerAdapter.getItemCount() - 1;
                mRecyclerAdapter.swapItem(itemPos, Math.min(maxIndex, itemPos + 1), true);
                break;
            default:
                //Actual item click
                setTitle("Last clicked item: " + dataObject.getTitle());
                break;
        }
    }
}

```

Download
--------

Grab via Gradle:
```groovy
compile 'eu.inloop:simplerecycleradapter:0.3.0'
```
