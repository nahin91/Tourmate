package com.tbz.practice.tourmateexample1;

import java.util.ArrayList;

/**
 * Created by USER on 26-Dec-16.
 */

public class Events extends ArrayList<Events> {
    private int id;
    private String eventName;
    private String startPlace;
    private String destination;
    private String date;
    private String budget;
    private int userId;

    public Events(int id, String eventName, String startPlace, String destination, String date, String budget, int userId) {
        this.id = id;
        this.eventName = eventName;
        this.startPlace = startPlace;
        this.destination = destination;
        this.date = date;
        this.budget = budget;
        this.userId = userId;
    }

    public Events(String eventName, String startPlace, String destination, String date, String budget, int userId) {
        this.eventName = eventName;
        this.startPlace = startPlace;
        this.destination = destination;
        this.date = date;
        this.budget = budget;
        this.userId = userId;
    }

    public Events(String eventName, String startPlace, String destination, String date, String budget) {
        this.eventName = eventName;
        this.startPlace = startPlace;
        this.destination = destination;
        this.date = date;
        this.budget = budget;
    }

    public Events(String addBudget){
        this.budget=addBudget;
    }

    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
