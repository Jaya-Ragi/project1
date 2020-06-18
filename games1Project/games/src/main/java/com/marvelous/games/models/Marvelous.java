package com.marvelous.games.models;

public class Marvelous {

    private String name;

    private String max_power;

    public Marvelous(String name, String max_power,Integer firstTime) {
        this.name = name;
        this.max_power = max_power;
    }

    public Marvelous(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMax_power() {
        return max_power;
    }

    public void setMax_power(String max_power) {
        this.max_power = max_power;
    }
}
