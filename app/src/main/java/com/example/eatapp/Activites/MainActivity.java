package com.example.eatapp.Activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eatapp.Login.SignIn;
import com.example.eatapp.Login.SignUp;
import com.example.eatapp.R;


public class MainActivity extends Activity {

    private Button mBtSignUp;
    private Button mBtSignIn;
    private Typeface myFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtSignIn=findViewById(R.id.SignIn);
        mBtSignUp=findViewById(R.id.SignUp);

        mBtSignUp.setText("Sign Up");
        mBtSignIn.setText("Sign In");

        mBtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });

        mBtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(signIn);
            }
        });
    }
}
