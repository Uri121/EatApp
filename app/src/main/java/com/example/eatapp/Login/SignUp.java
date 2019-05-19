package com.example.eatapp.Login;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Binder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eatapp.Model.User;
import com.example.eatapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;


public class SignUp extends AppCompatActivity {

    private MaterialEditText mEditName;
    private MaterialEditText mEditPhone;
    private MaterialEditText mEditPassword;
    private Button mBtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEditPassword=findViewById(R.id.editPassword);
        mEditPhone=findViewById(R.id.editPhone);
        mEditName=findViewById(R.id.editName);
        mBtSignUp=findViewById(R.id.inSignUp);
        Typeface myFont = Typeface.createFromAsset(this.getAssets(),"fonts/gitchgitch-Bold-Italic.otf");
        mBtSignUp.setText("Sign Up");
        mBtSignUp.setTypeface(myFont);
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        final DatabaseReference table_user= firebaseDatabase.getReference("User");

        mBtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpUser(table_user);
            }
        });
    }

    private void signUpUser(final DatabaseReference table_user) {
        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
        mDialog.setMessage("Waiting for server.....");
        mDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String phone = mEditPhone.getText().toString();
                String name = mEditName.getText().toString();
                String pass = mEditPassword.getText().toString();
                if (phone.length() == 10 ) {
                    if (name.length() > 0)
                    {
                        if (pass.length() > 0)
                        {
                            //check if the user phone is on the database
                            if (dataSnapshot.child(mEditPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Phone number already in the system ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                mDialog.dismiss();
                                User user;

                                if (mEditPhone.getText().toString().equals("0525057937"))
                                {
                                    user = new User(mEditName.getText().toString(), mEditPassword.getText().toString(),"true");
                                }
                                else
                                {
                                    user = new User(mEditName.getText().toString(), mEditPassword.getText().toString(),"false");
                                }
                                table_user.child(mEditPhone.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }  else {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "u must enter a password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        mDialog.dismiss();
                        Toast.makeText(SignUp.this, "u must enter a name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else
                {
                    mDialog.dismiss();
                    Toast.makeText(SignUp.this, "Phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
