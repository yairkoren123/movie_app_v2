package com.example.drawer_try.singletonClass;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singup.ToData;

import java.util.ArrayList;

public class Single_one {

    // values

    String no_imgae_abl = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
    String now_login_email;

    String userImage = "";

    String the_now_open_drawer = "home";

    Fragment fragment = null;


    String toSearch ="";
    String now_login_pass;
    ArrayList<The_movies> movies_list = null;
    The_movies value_movie  = new The_movies("none");
    ArrayList<The_movies> the_love_movies = new ArrayList<>();
    String the_same_movie_id = "";
    ArrayList<String> friend_list = new ArrayList<>();

    private static final Single_one ourInstance = new Single_one();
    public static Single_one getInstance() {
        return ourInstance;
    }
    private Single_one() { }

    public Single_one(ArrayList<The_movies> movies_list) {
        this.movies_list = movies_list;
    }

    public Single_one(ArrayList<The_movies> movies_list, The_movies value_movie) {
        this.movies_list = movies_list;
        this.value_movie = value_movie;
    }



    public ArrayList<The_movies> getThe_love_movies() {
        return the_love_movies;
    }
    public void addThe_love_movies(The_movies movie) {
        the_love_movies.add(movie);
        Log.d("love", "addThe_love_movies:   + " + movie.getTitle());
    }

    public void setThe_love_movies(ArrayList<The_movies> the_love_movies) {
        this.the_love_movies = the_love_movies;
    }

    public The_movies getValue_movie() {
        return value_movie;
    }

    public void setValue_movie(The_movies value_movie) {
        this.value_movie = value_movie;
    }

    public ArrayList<The_movies> getMovies_list() {
        return movies_list;
    }

    public void setMovies_list(ArrayList<The_movies> movies_list) {
        this.movies_list = movies_list;
    }

    public boolean seeiflove(The_movies movie){
            for (int i = 0; i < the_love_movies.size(); i++) {
                if (the_love_movies.get(i).getTitle().equals(movie.getTitle())) {
                    Log.d("loves", "seeiflove: in the list already" );
                    return true;
                }
            }
        return false;
    }

    public boolean seeiffollow(String email_to_search){
        for (int i = 0; i < friend_list.size(); i++) {
            if (friend_list.get(i).equals(email_to_search)) {
                Log.d("loves", "seeiffollow: in the list already" );
                return true;
            }
        }
        return false;
    }
    public void add_to_friend(String fr) {
        friend_list.add(fr);
        Log.d("friends", "addThe_love_movies:   + " + fr);
    }

    public void remove_one(The_movies movie){

        Log.d("removenow", "remove_one: " + the_love_movies.size());

        for (int i = 0; i < the_love_movies.size(); i++) {

            if (the_love_movies.get(i).getTitle().equals(movie.getTitle()) ){
                the_love_movies.remove(i);
                break;
            }

        }
        Log.d("removenow", "remove_one: " + the_love_movies.size());

    }

    // id

    public String getThe_same_movie_id() {

        return the_same_movie_id;
    }

    public void setThe_same_movie_id(String the_same_movie_id) {
        this.the_same_movie_id = the_same_movie_id;
    }


    // login


    public String getNow_login_email() {
        return now_login_email;
    }

    public void setNow_login_email(String now_login_email) {
        this.now_login_email = now_login_email;
    }

    public String getNow_login_pass() {
        return now_login_pass;
    }

    public void setNow_login_pass(String now_login_pass) {
        this.now_login_pass = now_login_pass;
    }

    // Search


    public String getToSearch() {
        return toSearch;
    }

    public void setToSearch(String toSearch) {
        this.toSearch = toSearch;
    }

    // user image


    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    // friend list

    public ArrayList<String> getFriend_list() {
        return friend_list;
    }

    public void setFriend_list(ArrayList<String> friend_list) {
        this.friend_list = friend_list;
    }
    // no image

    public String getNo_imgae_abl() {
        return no_imgae_abl;
    }

    public void setNo_imgae_abl(String no_imgae_abl) {
        this.no_imgae_abl = no_imgae_abl;
    }

    // current drawer

    public String getThe_now_open_drawer() {
        return the_now_open_drawer;
    }

    public void setThe_now_open_drawer(String the_now_open_drawer) {
        this.the_now_open_drawer = the_now_open_drawer;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
