package com.example.eatapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eatapp.Activites.Home;
import com.example.eatapp.Model.LoginUser;
import com.example.eatapp.Model.User;
import com.example.eatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;


public class SignIn extends AppCompatActivity {

    private EditText mEditPhone;
    private EditText mEditPassword;
    private Button mBtSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEditPhone = (MaterialEditText)findViewById(R.id.editPhone);
        mEditPassword = (MaterialEditText)findViewById(R.id.editPassword);
        mBtSignIn = findViewById(R.id.inSignIn);
        Typeface myFont = Typeface.createFromAsset(this.getAssets(),"fonts/gitchgitch-Bold-Italic.otf");
        mBtSignIn.setText("Sign In");
        mBtSignIn.setTypeface(myFont);
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        final DatabaseReference table_user= firebaseDatabase.getReference("User");

        mBtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInUser(table_user);
            }
        });
    }

    private void signInUser(DatabaseReference table_user) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Waiting for server.....");
        mDialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //check if user exist in database

                String phone = mEditPhone.getText().toString();
                String pass = mEditPassword.getText().toString();
                if (phone.length()== 10)
                {

                    if (pass.length() > 0)
                    {
                        if (dataSnapshot.child(mEditPhone.getText().toString()).exists()) {
                            //get user info
                            mDialog.dismiss();
                            User user = dataSnapshot.child(mEditPhone.getText().toString()).getValue(User.class);
                            user.setPhone(mEditPhone.getText().toString());
                            if (user.getPassword().equals(mEditPassword.getText().toString())) {
                                mDialog.dismiss();
                                //Toast.makeText(SignIn.this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
                                LoginUser.mCurrentUser = user;
                                Intent intent = new Intent(SignIn.this, Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exist in the database ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        mDialog.dismiss();
                        Toast.makeText(SignIn.this, "must enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "Phone Number Must have 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
