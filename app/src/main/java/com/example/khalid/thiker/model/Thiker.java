package com.example.khalid.thiker.model;

/**
 * Created by khalid on 11/11/2017.
 */

public class Thiker {
    String id;

    public Thiker() {


    }

    public Thiker(String id, String text, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String text;
    String date;
}
