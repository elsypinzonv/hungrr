package com.snotsoft.hungrr.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Elsy on 21/04/2016.
 */
public class Menu {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("sections")
    private ArrayList<Section> sections;

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

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
}
