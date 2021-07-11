package com.example.drawer_try.stuffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.drawer_try.MainActivity;
import com.example.drawer_try.R;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singletonClass.Single_one;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class Pic_image_background extends AppCompatActivity {


    //https://api.unsplash.com/search/photos?query=background&client_id=tRWDOxYibuPAqYe_PAsvjFOmrv9cfh18r6y-Xp3RH7U&page=2&key=tRWDOxYibuPAqYe_PAsvjFOmrv9cfh18r6y-Xp3RH7U%26

    String JsonUrl = "https://api.unsplash.com/search/photos?query=background&client_id=tRWDOxYibuPAqYe_PAsvjFOmrv9cfh18r6y-Xp3RH7U&page=2&key=tRWDOxYibuPAqYe_PAsvjFOmrv9cfh18r6y-Xp3RH7U%26";

    RequestQueue requestQueue;

    ArrayList<String> background_array_list = new ArrayList<>();

    int the_limit_page = 0;

    RecyclerView recyclerView;
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_image_background);


        // set bar title
        Pic_image_background.this.setTitle("Background for Profile");



        backbutton = findViewById(R.id.image_back_button_background);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        recyclerView = findViewById(R.id.background_rec);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getbackground();

    }

    public void getbackground(){

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonUrl, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            the_limit_page = response.getInt("total_pages");


                            Log.d("9pages", "onResponse: " + the_limit_page);
                            The_movies one_movie = new The_movies();
                            JSONObject jsonObject = new JSONObject();
                            JSONArray jsonArray = response.getJSONArray("results");


                            // use jsonArray.length to get all
                            for (int i = 0; i < jsonArray.length(); i++) {
//                                one_movie = new The_movies();

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i).getJSONObject("urls");
                                Log.d("jsonObject", "onResponse: " + jsonObject1);

                                Log.d("jsonObject1", "onResponse: " + jsonObject1.get("raw"));

                                String the_image_url = (String) jsonObject1.get("regular");

                                background_array_list.add(the_image_url);

                                Log.d("9array", "onResponse: " + background_array_list.size());





                            }
                            Log.d("9array", "onResponse: " + background_array_list.size());


                            next_level2();

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(obreq);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pic_image_background.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    public void next_level2(){
        // after we get all the images

        my_back_adpter my_back_adpter = new my_back_adpter(background_array_list,Pic_image_background.this);
        recyclerView.setAdapter(my_back_adpter);

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Pic_image_background.this, MainActivity.class);
        startActivity(intent);
        finish();

        //super.onBackPressed();
    }
}