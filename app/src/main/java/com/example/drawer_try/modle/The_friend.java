package com.example.drawer_try.modle;

public class The_friend {

    String name = "";

    String last_connect = "";

    String love_movies = "";// the size

    String follower = "";

    @Override
    public String toString() {
        return "The_friend{" +
                "name='" + name + '\'' +
                ", last_connect='" + last_connect + '\'' +
                ", love_movies='" + love_movies + '\'' +
                ", follower='" + follower + '\'' +
                '}';
    }

    public The_friend() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_connect() {
        return last_connect;
    }

    public void setLast_connect(String last_connect) {
        this.last_connect = last_connect;
    }

    public String getLove_movies() {
        return love_movies;
    }

    public void setLove_movies(String love_movies) {
        this.love_movies = love_movies;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
