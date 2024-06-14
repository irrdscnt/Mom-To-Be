package com.example.momtobe.models;

public class User {
    private String email;
    private String name;
    private int pregnancyWeeks;
    private String phoneNumber;
    private String password;

    public User(String email, String name, int pregnancyWeeks, String phoneNumber, String password) {
        this.email = email;
        this.name = name;
        this.pregnancyWeeks = pregnancyWeeks;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPregnancyWeeks() {
        return pregnancyWeeks;
    }

    public void setPregnancyWeeks(int pregnancyWeeks) {
        this.pregnancyWeeks = pregnancyWeeks;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", pregnancyWeeks=" + pregnancyWeeks +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
