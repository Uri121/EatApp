package com.example.eatapp.Activites;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatapp.Adapter.CartAdapter;
import com.example.eatapp.Model.LoginUser;
import com.example.eatapp.Model.Order;
import com.example.eatapp.Model.Requset;
import com.example.eatapp.R;
import com.example.eatapp.Sqlite.DataBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DataBase dataBase = new DataBase(this);

    private FirebaseDatabase database;
    private DatabaseReference Requsets;

    private TextView mTotalPrice;
    private Button mMakeOrder;
    private List<Order> cart = new ArrayList<>();
    private CartAdapter cartAdapter;
    private ColorDrawable SwipeColorBackground;
    private Drawable DeleteIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        Requsets = database.getReference("Requests");
        SwipeColorBackground = new ColorDrawable(Color.parseColor("#AA281D"));
        DeleteIcon = ContextCompat.getDrawable(this,R.drawable.ic_delete_black_24dp);

        recyclerView = findViewById(R.id.list_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mTotalPrice = findViewById(R.id.total_price);
        mMakeOrder = findViewById(R.id.make_order);

        mMakeOrder.setText("Make Order");

        //getting the lis items that the user ordered
        try {
            cart = dataBase.GetList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cartAdapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(cartAdapter);

        checkTotalSum();

        //button onclick
        mMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!cart.isEmpty()) {
                    showAlertDialog();
                }
                else {
                    Toast.makeText(Cart.this, "Cart Is Empty Go Order Something!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Swipe left to delete item from cart
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            //draw the red background after swiping
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                View view = viewHolder.itemView;
                int iconMargin = (view.getHeight()- DeleteIcon.getIntrinsicHeight()) / 2;

                if (dX > 0)
                {
                    SwipeColorBackground.setBounds(view.getLeft(), view.getTop(),(int)dX, view.getBottom());
                    DeleteIcon.setBounds(view.getLeft()+iconMargin,view.getTop()+iconMargin,view.getLeft()+iconMargin+DeleteIcon.getIntrinsicHeight(),
                            view.getBottom()-iconMargin);
                }
                else {
                    SwipeColorBackground.setBounds(view.getRight()+(int)dX, view.getTop(),view.getRight(),view.getBottom());
                    DeleteIcon.setBounds(view.getRight() - iconMargin - DeleteIcon.getIntrinsicHeight(),view.getTop() + iconMargin,view.getRight() - iconMargin,
                            view.getBottom() - iconMargin);
                }

                SwipeColorBackground.draw(c);
                c.save();
                if (dX > 0)
                {
                    c.clipRect(view.getLeft(), view.getTop(),(int)dX, view.getBottom());
                }
                else {
                    c.clipRect(view.getRight() + (int)dX, view.getTop(), view.getRight(), view.getBottom());
                }
                DeleteIcon.draw(c);
                c.restore();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                    int deletedIndex = viewHolder.getAdapterPosition();
                    String id = cart.get(deletedIndex).getId();
                    dataBase.DeleteItem(id);
                    cartAdapter.RemoveItem(deletedIndex);
                    checkTotalSum();

            }
        }).attachToRecyclerView(recyclerView);

    }
    // making alert dialog for the user to enter his address and send his order to firebase
    private void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Cart.this);
        dialog.setTitle("One More Step!");
        dialog.setMessage("Enter Your Address: ");

        final EditText editText = new EditText(Cart.this);
        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editText.setLayoutParams(lp);
        dialog.setView(editText);
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        dialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Requset requset = new Requset(
                        LoginUser.mCurrentUser.getPhone(),
                        LoginUser.mCurrentUser.getName(),
                        editText.getText().toString(),
                        mTotalPrice.getText().toString(),
                        cart

                );

                Requsets.child(String.valueOf(System.currentTimeMillis())).setValue(requset);
                dataBase.CleanCart();
                Toast.makeText(Cart.this,"Thank You, Order was send..", Toast.LENGTH_LONG).show();

                finish();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //calculate total price
    private void checkTotalSum() {
        int total = 0;

        for (Order order : cart) {
            total += (order.getPrice());
        }

        mTotalPrice.setText(Integer.toString(total));

    }

}
