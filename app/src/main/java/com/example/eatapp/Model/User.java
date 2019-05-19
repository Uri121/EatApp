package com.example.eatapp.Model;

public class User {

    private String Name;
    private String Password;
    private String Phone;
    private String isStaff;

    public String getIsStaff() {
        return isStaff;
    }

    public void setStaff(String staff) {
        isStaff = staff;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public User(String name, String password, String staff) {
        Name = name;
        Password = password;
        isStaff = staff;

    }

    public User(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
