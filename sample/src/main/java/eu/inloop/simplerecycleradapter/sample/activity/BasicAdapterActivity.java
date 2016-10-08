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
import eu.inloop.simplerecycleradapter.SimpleRecyclerAdapter;
import eu.inloop.simplerecycleradapter.sample.R;
import eu.inloop.simplerecycleradapter.sample.adapter.model.MyDataObject;
import eu.inloop.simplerecycleradapter.sample.adapter.viewholder.basic.MyDataViewHolder;

public class BasicAdapterActivity extends AppCompatActivity implements ItemActionListener<MyDataObject, MyDataViewHolder> {

    private RecyclerView mRecyclerView;
    private SimpleRecyclerAdapter<MyDataObject, MyDataViewHolder> mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_basic).setVisibility(View.GONE);
        findViewById(R.id.btn_advanced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(BasicAdapterActivity.this, AdvancedAdapterActivity.class));
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        initAdapter();
        initData();
    }

    private void initAdapter() {
        mRecyclerAdapter = new SimpleRecyclerAdapter<>(this, new SimpleRecyclerAdapter.CreateViewHolder<MyDataObject, MyDataViewHolder>() {
            @Override
            public MyDataViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
                return new MyDataViewHolder(BasicAdapterActivity.this, R.layout.item_mydata, parent);
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void initData() {
        mRecyclerAdapter.addItem(new MyDataObject("Item 1", "abc"));
        mRecyclerAdapter.addItem(new MyDataObject("Item 2", "def"));
        mRecyclerAdapter.addItem(new MyDataObject("Item 3", "ghi"));
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(@NonNull MyDataObject item, @NonNull MyDataViewHolder viewHolder, @NonNull View view) {
        setTitle("Last clicked item: " + item.getTitle());
    }
}
