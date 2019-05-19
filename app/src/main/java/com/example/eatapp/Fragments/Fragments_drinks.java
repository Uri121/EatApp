package com.example.eatapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatapp.R;
import com.example.eatapp.Sqlite.DataBase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;


public class Fragments_drinks extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mTitleTextView;
    private CheckBox mColaCBox, mColaZeroCBox;
    private ImageView mTitleImageView, imageViewCola, imageViewZero;
    private String mDrink;
    private final int price = 10;
    private FloatingActionButton mDrinkButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragments_drinks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragments_drinks.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragments_drinks newInstance(String param1, String param2) {
        Fragments_drinks fragment = new Fragments_drinks();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragments__drinks, container, false);

        Bundle bundle = getArguments();
        mColaCBox = view.findViewById(R.id.coca_cola);
        imageViewCola = view.findViewById(R.id.coca_cola_image);
        imageViewZero = view.findViewById(R.id.coca_cola_zero_image);
        mColaZeroCBox = view.findViewById(R.id.coca_cola_zero);
        mDrinkButton = view.findViewById(R.id.bt_drink_cart);
        mTitleImageView = view.findViewById(R.id.image_title_drinks);
        mTitleTextView = view.findViewById(R.id.txt_title_drinks);

        mTitleTextView.setText(bundle.getString("title"));

        setImage();
        setImages();

        mDrinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new DataBase(getContext()).AddToCart(mDrink,price, null, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "New Item Added", Toast.LENGTH_SHORT).show();

            }
        });

        mColaCBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mDrink = (mColaCBox.getText().toString());
                    mColaZeroCBox.setEnabled(false);

                }
                else
                {
                    mDrink = (mColaCBox.getText().toString());
                    mColaZeroCBox.setEnabled(true);

                }
            }
        });

        mColaZeroCBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mDrink = (mColaZeroCBox.getText().toString());
                    mColaCBox.setEnabled(false);

                }
                else
                {
                    mDrink = (mColaZeroCBox.getText().toString());
                    mColaCBox.setEnabled(true);
                }
            }
        });
        return view;
    }

    private void setImages() {

        Picasso.get().load(R.drawable.soda).into(imageViewCola);
        Picasso.get().load(R.drawable.soda_zero).into(imageViewZero);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setImage() {
        switch (mTitleTextView.getText().toString()){
            case "Shawarma":
                mTitleImageView.setBackgroundResource(R.drawable.shwarma);
                break;
            case "Skewers" :
                mTitleImageView.setBackgroundResource(R.drawable.kebab);
                break;
    /*        case "Salads":
                mTitleImageView.setBackgroundResource(R.drawable.salad);
                break;*/
            case  "Drinks":
                mTitleImageView.setBackgroundResource(R.drawable.soft_drinks);
                break;
            case  "French Fries":
                mTitleImageView.setBackgroundResource(R.drawable.chips);
                break;
        }
    }
}
