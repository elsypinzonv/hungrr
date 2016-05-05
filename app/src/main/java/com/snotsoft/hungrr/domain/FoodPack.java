package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by luisburgos on 4/05/16.
 */
public class FoodPack {

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("elements")
    private ArrayList<Element> elements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
}
