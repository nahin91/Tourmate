package com.tbz.practice.tourmateexample1.Weather;

/**
 * Created by TYCOHANX on 12/21/2016.
 */

public class ForeCasts {


    private Double minTemp;
    private Double maxTemp;
    private String Description;
    private String icon;
    private String date;
    private String speed;
    private String humidty;
    private String pressure;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHumidty() {
        return humidty;
    }

    public void setHumidty(String humidty) {
        this.humidty = humidty;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
