SimpleRecyclerAdapter
================

Writing a RecyclerView adapter is a lot of boiler-plate code that is doing the same thing in most cases. This library provides you a universal SimpleRecyclerAdapter, which you can use directly. 
You can bind any list of arbitrary objects.
The adapter was implemented with performance in mind. No new objects are created during the binding process (except for the first visible rows and then the objects are reused).

How to implement
--------
Very easy, to create a new RecyclerViewAdapter, you only need to make an instance of our SimpleRecyclerAdapter:

```java
mRecyclerAdapter = new SimpleRecyclerAdapter<>(mOnClickListener, new SimpleRecyclerAdapter.CreateViewHolder<MyDataObject>() {
        @Override
        public SettableViewHolder<MyDataObject> onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new MyDataViewHolder(BasicAdapterActivity.this, R.layout.item_mydata, parent);
        }
});
```
That's it. You only create an instance of your ViewHolder object ([MyDataViewHolder](https://github.com/inloop/SimpleRecyclerAdapter/blob/master/sample/src/main/java/eu/inloop/simplerecycleradapter/sample/adapter/viewholder/basic/MyDataViewHolder.java) in case of this example) and provide a click listener.

The ViewHolder itself is a standard ViewHolder - see [example](https://github.com/inloop/SimpleRecyclerAdapter/blob/master/sample/src/main/java/eu/inloop/simplerecycleradapter/sample/adapter/viewholder/basic/MyDataViewHolder.java). It has to extend [SettableViewHolder] (https://github.com/inloop/SimpleRecyclerAdapter/blob/master/library/src/main/java/eu/inloop/simplerecycleradapter/SettableViewHolder.java).

There is also support for multiple clickable areas (see [AdvancedDataViewHolder example](https://github.com/inloop/SimpleRecyclerAdapter/blob/master/sample/src/main/java/eu/inloop/simplerecycleradapter/sample/adapter/viewholder/advanced/AdvancedDataViewHolder.java)), and multiple view-types ([example](https://github.com/inloop/SimpleRecyclerAdapter/blob/docu/sample/src/main/java/eu/inloop/simplerecycleradapter/sample/activity/AdvancedAdapterActivity.java)).

Download
--------

Grab via Gradle:
```groovy
compile 'eu.inloop:simplerecycleradapter:0.2.1'
```
