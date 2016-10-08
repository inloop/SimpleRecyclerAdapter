package eu.inloop.simplerecycleradapter.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import eu.inloop.simplerecycleradapter.ItemActionListener;
import eu.inloop.simplerecycleradapter.SettableViewHolder;
import eu.inloop.simplerecycleradapter.SimpleRecyclerAdapter;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.MyDataObject;
import eu.inloop.simplerecycleradapter.sample.adapter.model.WrappedMyDataObject;
import eu.inloop.simplerecycleradapter.sample.adapter.viewholder.advanced.AdvancedDataViewHolder;
import eu.inloop.simplerecycleradapter.sample.adapter.viewholder.advanced.HeaderViewHolder;

public class AdvancedAdapterActivity extends AppCompatActivity implements ItemActionListener<WrappedMyDataObject, SettableViewHolder> {

    private RecyclerView mRecyclerView;
    private SimpleRecyclerAdapter<WrappedMyDataObject, ? extends SettableViewHolder> mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_advanced).setVisibility(View.GONE);
        findViewById(R.id.btn_basic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AdvancedAdapterActivity.this, BasicAdapterActivity.class));
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        initAdapter();
        initData();
    }

    @SuppressWarnings("unchecked")
    private void initAdapter() {
        mRecyclerAdapter = new SimpleRecyclerAdapter<>(this,
                new SimpleRecyclerAdapter.CreateViewHolder<WrappedMyDataObject, SettableViewHolder>() {
                    @Override
                    protected SettableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        switch (viewType) {
                            case WrappedMyDataObject.ITEM_TYPE_NORMAL:
                                return new AdvancedDataViewHolder(AdvancedAdapterActivity.this, R.layout.item_mydata, parent);
                            case WrappedMyDataObject.ITEM_TYPE_HEADER:
                                return new HeaderViewHolder(AdvancedAdapterActivity.this, R.layout.item_header, parent);
                            default:
                                throw new AssertionError("Wrong view type");
                        }
                    }

                    @Override
                    protected int getItemViewType(int position) {
                        return mRecyclerAdapter.getItem(position).getType();
                    }
                });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void initData() {
        mRecyclerAdapter.addItem(WrappedMyDataObject.initHeaderItem("Header 1"));
        mRecyclerAdapter.addItem(WrappedMyDataObject.initDataItem(new MyDataObject("Item 1", "Desc")));
        mRecyclerAdapter.addItem(WrappedMyDataObject.initDataItem(new MyDataObject("Item 2", "Desc")));
        mRecyclerAdapter.addItem(WrappedMyDataObject.initHeaderItem("Header 2"));
        mRecyclerAdapter.addItem(WrappedMyDataObject.initDataItem(new MyDataObject("Item 3", "Desc")));
        mRecyclerAdapter.addItem(WrappedMyDataObject.initDataItem(new MyDataObject("Item 4", "Desc")));
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(@NonNull WrappedMyDataObject item, @NonNull SettableViewHolder viewHolder, @NonNull View view) {
        if (item.getType() == WrappedMyDataObject.ITEM_TYPE_NORMAL) {
            MyDataObject dataObject = item.getDataObject();

            if (view.getId() == R.id.btn_more) {
                setTitle("Action clicked on item: " + dataObject.getTitle());
            } else {
                setTitle("Last clicked item: " + dataObject.getTitle());
            }
        }
    }
}
