package com.example.eatapp.MenuItems;

import com.example.eatapp.R;

import java.io.Serializable;
import java.util.ArrayList;

// the menu item that goes into the home activity
public class MenuItems implements Serializable {

    private String mName;
    private int mImage;

    public MenuItems(String mName, int mImage) {
        this.mName = mName;
        this.mImage = mImage;
    }
    public MenuItems(){}

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public ArrayList<MenuItems> SetListItem()
    {
        ArrayList<MenuItems> list= new ArrayList<>();
        list.add(new MenuItems("Shawarma", R.drawable.shwarma));
        list.add(new MenuItems("Skewers", R.drawable.kebab));
        list.add(new MenuItems("Drinks", R.drawable.soft_drinks));
        /*list.add(new MenuItems("Salads", R.drawable.salad));*/
        list.add(new MenuItems("French Fries", R.drawable.chips));

        return list;
    }

}
