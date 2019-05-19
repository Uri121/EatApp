package com.example.eatapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eatapp.R;
import com.example.eatapp.Sqlite.DataBase;

import org.json.JSONException;

import java.util.ArrayList;


public class Fragments_Shislik extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mTitleTextView;
    private ImageView mTitleImageView;
    private CheckBox mCbPita, mCbLafa;
    private String mBreadType = " ";
    private FloatingActionButton mAddItemToOrder;
    private String mMeatType = " ";

    private ArrayList<String> list = new ArrayList<>();//names of the add ones
    private ArrayList<String> listMeat = new ArrayList<>();//names of the Meat
    private ArrayList<String> mAddOnes = new ArrayList<>();// the custumer chossen add ones


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragments_Shislik() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragments_Shislik.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragments_Shislik newInstance(String param1, String param2) {
        Fragments_Shislik fragment = new Fragments_Shislik();
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
        View view = inflater.inflate(R.layout.fragment_fragments__shislik, container, false);

        Bundle bundle = getArguments();
        mAddItemToOrder = view.findViewById(R.id.bt_cart);
        mTitleTextView = view.findViewById(R.id.txt_title);
        mTitleImageView = view.findViewById(R.id.image_title);
        mCbLafa = view.findViewById(R.id.checkbox_lafa);
        mCbPita = view.findViewById(R.id.checkbox_pita);
        mTitleTextView.setText(bundle.getString("title"));

        setImage();
        fillList();
        fillListMeat();

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getContext(), R.layout.checkbox_meat, listMeat);
        ListView listViewMeat = view.findViewById(R.id.list_meat);
        listViewMeat.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewMeat.setAdapter(adapter);

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.checkbox, list);
        ListView listView = view.findViewById(R.id.list_item);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selecetedItem = ((TextView)view).getText().toString();

                if (mAddOnes.contains(selecetedItem)){
                    mAddOnes.remove(selecetedItem);
                    Toast.makeText(getActivity(), "Item Been Removed", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAddOnes.add(selecetedItem);
                    Toast.makeText(getActivity(), "Item Been Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

      listViewMeat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view).getText().toString();
                if (mMeatType.equals(" "))
                {
                    mMeatType=selectedItem;
                }
            }
        });

        mAddItemToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price;
                if (mBreadType == "Pita"){
                    price=35;
                }
                else {
                    price = 45;
                }
                if (mBreadType == " ") {
                    Toast.makeText(getContext(), "You must choose a bread type", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        new DataBase(getContext()).AddToCart(mMeatType,price, mAddOnes, mBreadType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "New Item Added", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mCbPita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    mCbLafa.setEnabled(false);
                    mBreadType = mCbPita.getText().toString();
                }
                else
                {
                    mCbLafa.setEnabled(true);
                    mBreadType = " ";
                }
            }
        });

        mCbLafa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    mCbPita.setEnabled(false);
                    mBreadType = mCbLafa.getText().toString();
                }
                else
                {
                    mCbPita.setEnabled(true);
                    mBreadType = " ";
                }
            }
        });

        return view;
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
        /*    case "Salads":
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

    private void fillList()
    {
        list.add("Hommos");
        list.add("Chips");
        list.add("Salad");
        list.add("Thina");
        list.add("Red cabbage");
        list.add("White cabbage");
    }


    private void fillListMeat()
    {
        listMeat.add("Kebab");
        listMeat.add("Spring Chicken");
        listMeat.add("Red Meat");
        listMeat.add("chicken Liver");
        listMeat.add("chicken Heart");
        listMeat.add("Almonds");
    }
}
