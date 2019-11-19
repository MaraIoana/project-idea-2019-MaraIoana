package com.example.mapp;

public class Movie {
    private String title;
    private int year;
    private int rating;
    private String description;

    public Movie(String title, int year, int rating, String description) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.description = description;
    }

    public Movie() {
        this.title = "";
        this.year = 0;
        this.rating = 0;
        this.description = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
