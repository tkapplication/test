package com.example.khalid.thiker.model;

/**
 * Created by khalid on 11/18/2017.
 */

public class Wall {

    public Wall() {


    }

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Wall(String id, String text, String token) {
        this.id = id;
        this.text = text;
        this.token = token;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String text;
    String token;
}
