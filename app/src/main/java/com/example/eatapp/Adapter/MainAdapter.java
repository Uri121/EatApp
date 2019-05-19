package com.example.eatapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatapp.Activites.AfterMenu;

import com.example.eatapp.MenuItems.MenuItems;
import com.example.eatapp.R;

import java.util.ArrayList;

//the adpater of the home activity filling the recyclerview with menu items
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<MenuItems> mDataset;
    private OnItemClickListener mListener;
    public Context mContext;

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textViewName;
        ImageView imageView;

        // creates the afterMenu activity
        public ViewHolder(final View itemView, final OnItemClickListener listener, final Context context, final ArrayList<MenuItems> list) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.menu_name);
            this.imageView = itemView.findViewById(R.id.menu_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                            Intent intent = new Intent(context, AfterMenu.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("list", list.get(pos).getmName());
                            bundle.putInt("pos",pos);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                }
            });

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(ArrayList<MenuItems> myDataset, Context context) {
        this.mDataset = myDataset;
        this.mContext= context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

        //view.setOnClickListener(Home.myOnClickListener);

        ViewHolder myViewHolder = new ViewHolder(view, mListener, mContext,mDataset);
        return myViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        holder.textViewName.setText(mDataset.get(listPosition).getmName());
        holder.imageView.setImageResource(mDataset.get(listPosition).getmImage());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
