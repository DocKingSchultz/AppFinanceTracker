package com.example.appfinance.model;

public class Category {
    private int ID;  //for database

    private String name;

    public Category()
    {

    }
    public Category(int ID, String name)
    {
        this.ID = ID;
        this.name = name;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
