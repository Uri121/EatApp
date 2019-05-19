package com.example.eatapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.eatapp.Model.Order;
import com.example.eatapp.R;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class CartAdapter  extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Order> list;
    public Context mContext;

    public CartAdapter(List<Order> myData, Context context) {
        this.list = myData;
        this.mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final Order order = list.get(listPosition);
        holder.bind(order);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (order.getBreadType() != null) {
                    boolean expanded = order.isExpanded();
                    // Change the state
                    order.setExpanded(!expanded);
                    // Notify the adapter that item has changed
                    notifyItemChanged(listPosition);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void RemoveItem(int pos)
    {
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,list.size());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPrice;
        TextView subItem, subBread;
        ImageButton arrow;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.cart_item_name);
            this.textViewPrice = itemView.findViewById(R.id.cart_item_price);
            this.subItem = itemView.findViewById(R.id.text_add_ones);
            this.subBread = itemView.findViewById(R.id.text_bread);
            this.arrow = itemView.findViewById(R.id.expand_icon);
        }

        // Method in ViewHolder class
        private void bind(Order order) {

            textViewName.setText(order.getMeatType());
            textViewPrice.setText(order.getPrice() + " Nis");

            //check if the order card should be expandable
            if (order.getBreadType()!= null)
            {
                arrow.setImageResource(R.drawable.arrow_down);
                subBread.setText(order.getBreadType() + ":");

                // Get the state
                boolean expanded = order.isExpanded();

                // Set the visibility based on state
                subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
                subBread.setVisibility(expanded ? View.VISIBLE : View.GONE);
                if (expanded)
                {
                    animateExpand();
                }
                else {
                    animateCollapse();
                }
                StringBuilder builder = new StringBuilder();
                for (String details : order.getAddOnes()) {
                    builder.append(details + "\n");
                }

                subItem.setText(builder.toString());
            }
            else {
                subItem.setVisibility(View.GONE);
                subBread.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);
            }
        }


        private void animateExpand() {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }

        private void animateCollapse() {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            arrow.setAnimation(rotate);
        }
    }
}
