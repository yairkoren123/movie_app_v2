package com.example.drawer_try.modle;

public class The_movies {



    boolean adult;


    String title;

    String vote_average;

    String release_date;

    String overview;

    String original_language;

    String vote_count;

    String image;

    String image_sec;

    String id;

    public The_movies(String title) {
        this.title = title;
    }

    // constructors
    public The_movies() {
    }

    @Override
    public String toString() {
        return "The_movies{" +
                "adult=" + adult +
                ", title='" + title + '\'' +
                ", vote_average='" + vote_average + '\'' +
                ", release_date='" + release_date + '\'' +
                ", overview='" + overview + '\'' +
                ", original_language='" + original_language + '\'' +
                ", vote_count='" + vote_count + '\'' +
                ", image='" + image + '\'' +
                ", image_sec='" + image_sec + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


    public String getImage_sec() {
        return image_sec;
    }

    public void setImage_sec(String image_sec) {
        this.image_sec = image_sec;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter and Setter
    public boolean isAdult() {
        return adult;
    }



    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        if (vote_average == null){
            vote_average = "Empty";
        }
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        if (release_date == null){
            release_date = "Empty";
        }
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        if (overview == null){
            overview = "Empty";
        }
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {

        if (original_language == null){
            original_language = "Empty";
        }
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getVote_count() {
        if (vote_count == null){
            vote_count = "Empty";
        }
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }


    // id


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
