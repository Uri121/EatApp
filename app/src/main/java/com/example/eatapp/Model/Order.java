package com.example.eatapp.Model;

import java.util.List;

public class Order {

    private String Title;
    private String BreadType;
    private List<String> AddOnes;
    public String Ordernumber;
    private int Price;
    private boolean expanded;


    public Order(String meatType, String breadType, List<String> addOnes, int price) {
        Title = meatType;
        BreadType = breadType;
        AddOnes = addOnes;
        Price = price;

    }
    public Order(){}

    public String getId() {
        return Ordernumber;
    }

    public void setId(String id) {
        Ordernumber = id;
    }

    public String getMeatType() {
        return Title;
    }

    public void setMeatType(String meatType) {
        Title = meatType;
    }

    public String getBreadType() {
        return BreadType;
    }

    public void setBreadType(String breadType) {
        BreadType = breadType;
    }

    public List<String> getAddOnes() {
        return AddOnes;
    }

    public void setAddOnes(List<String> addOnes) {
        AddOnes = addOnes;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
