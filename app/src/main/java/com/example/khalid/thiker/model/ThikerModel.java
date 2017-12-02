package com.example.khalid.thiker.model;

/**
 * Created by khalid on 11/12/2017.
 */

public class ThikerModel {
    String id;

    public String getFadel() {
        return fadel;
    }

    public void setFadel(String fadel) {
        this.fadel = fadel;
    }

    String fadel;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String text;

    public ThikerModel() {


    }

    public ThikerModel(String id, String text, String count, String type,String fadel) {
        this.id = id;
        this.text = text;
        this.count = count;
        this.type = type;
        this.fadel = fadel;

    }


    String count;
}
