package com.example.eatapp.Activites;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eatapp.Fragments.Fragments_drinks;
import com.example.eatapp.Fragments.Fragments_Shislik;
import com.example.eatapp.Fragments.Fragments_shawarma;
import com.example.eatapp.R;

public class AfterMenu extends AppCompatActivity implements Fragments_shawarma.OnFragmentInteractionListener, Fragments_Shislik.OnFragmentInteractionListener, Fragments_drinks.OnFragmentInteractionListener {


    private Bundle bundle;// gets the putextra from home activity
    private String mTilte;// the name of the title in this page
    private int pos;
    private Fragment fragment;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_menu);

        bundle = getIntent().getExtras();
        mTilte = (String) bundle.getSerializable("list");
        pos = bundle.getInt("pos");
        if (pos == 0) {
            if (fragment == null) {
                fragment = new Fragments_shawarma();
                Bundle bundle1 = new Bundle();// passing the title of the page to the fragment
                bundle1.putString("title", mTilte);
                fragment.setArguments(bundle1);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.box_fragment, fragment).commit();
            }
        } else if (pos == 1) {
            if (fragment == null) {
                fragment = new Fragments_Shislik();
                Bundle bundle1 = new Bundle();// passing the title of the page to the fragment
                bundle1.putString("title", mTilte);
                fragment.setArguments(bundle1);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.box_fragment, fragment).commit();
            }
        } else if (pos == 2) {
            if (fragment == null) {
                fragment = new Fragments_drinks();
                Bundle bundle1 = new Bundle();// passing the title of the page to the fragment
                bundle1.putString("title", mTilte);
                fragment.setArguments(bundle1);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.box_fragment, fragment).commit();
            }
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

