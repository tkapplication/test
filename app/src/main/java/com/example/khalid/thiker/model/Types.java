package com.example.khalid.thiker.model;

/**
 * Created by khalid on 11/30/2017.
 */

public class Types {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Types() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String id;
    String name;

    public Types(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    String image;
}
