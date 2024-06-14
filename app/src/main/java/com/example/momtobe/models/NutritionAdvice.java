package com.example.momtobe.models;

public class NutritionAdvice {
    private int id;
    private String photo;
    private String description;
    private int trimester;

    public NutritionAdvice(int id, String photo, String description, int trimester) {
        this.id = id;
        this.photo = photo;
        this.description = description;
        this.trimester = trimester;
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
}