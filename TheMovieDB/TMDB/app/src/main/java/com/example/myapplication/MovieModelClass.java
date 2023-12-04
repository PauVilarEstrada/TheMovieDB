package com.example.myapplication;

public class MovieModelClass {
    private String id;
    private String name;
    private String img;
    private String releaseDate;
    private double popularity;

    private String synopsis;

    public MovieModelClass(String id, String name, String img, String releaseDate, double popularity, String synopsis) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.synopsis = synopsis;
    }

    public MovieModelClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
