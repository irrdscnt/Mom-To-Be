package com.example.momtobe.models;

public class Fitness {
    private int id;
    private String photo;
    private String description;
    private int trimester;
    private int week;

    public Fitness(int id, String photo, String description, int trimester, int week) {
        this.id = id;
        this.photo = photo;
        this.description = description;
        this.trimester = trimester;
        this.week = week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTrimester() {
        return trimester;
    }

    public void setTrimester(int trimester) {
        this.trimester = trimester;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}
