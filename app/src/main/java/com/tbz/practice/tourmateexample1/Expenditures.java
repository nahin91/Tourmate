package com.tbz.practice.tourmateexample1;

import java.util.ArrayList;

/**
 * Created by USER on 14-Jan-17.
 */

public class Expenditures extends ArrayList<Expenditures>{
    private int id;
    private String title;
    private String cost;
    private String date ;
    private int event_id;

    public Expenditures(String title, String cost, String date, int event_id) {
        this.title = title;
        this.cost = cost;
        this.date = date;
        this.event_id = event_id;
    }

    public Expenditures(int id, String title, String cost, String date, int event_id) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.date = date;
        this.event_id = event_id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
