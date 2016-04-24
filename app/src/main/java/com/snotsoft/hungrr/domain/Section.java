package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Elsy on 21/04/2016.
 */
public class Section {

    @SerializedName("name")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("elements")
    private ArrayList<Element> elements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public static class Element {

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("currency")
        private String currency;

        @SerializedName("image")
        private String image;

        @SerializedName("price")
        private String price;
    }

}
