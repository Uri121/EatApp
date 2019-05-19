package com.example.eatapp.Activites;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.eatapp.MenuItems.MenuItems;
import com.example.eatapp.Adapter.MainAdapter;
import com.example.eatapp.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayout;
    private MainAdapter mAdapter;
    private ArrayList<MenuItems> mList;
    private MenuItems menuItems = new MenuItems();
    private FloatingActionButton mCartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);

        mList = menuItems.SetListItem();

        mCartButton= findViewById(R.id.home_cart_button);
        mLayout= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayout);
        mAdapter = new MainAdapter(mList,this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                mList.get(pos);
                mAdapter.notifyItemChanged(pos);
            }
        });

        mCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Cart.class);
                startActivity(intent);
//                finish();
            }
        });
    }
}
