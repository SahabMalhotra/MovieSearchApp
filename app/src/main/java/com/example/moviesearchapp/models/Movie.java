package com.example.moviesearchapp.models;

public class Movie {
    private String title;
    private String year;
    private String rating;
    private String description;
    private String posterUrl;
    private String genre;
    private String runtime;

    public Movie(String title, String year, String rating, String description, String posterUrl, String genre, String runtime) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.description = description;
        this.posterUrl = posterUrl;
        this.genre = genre;
        this.runtime = runtime;
    }

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public String getPosterUrl() { return posterUrl; }
    public String getGenre(){return genre;}
    public String getRuntime(){return runtime;}
}
