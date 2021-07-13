package com.example.drawer_try.stuffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
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





        // check on INTERNET Connection

        if (!isconnected()) {
            // no internet
            Log.d("wifi", "onCreate: no ");
            alert_dialog();
        }else {
            Log.d("wifi", "onCreate: yes ");

        }
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // network available
                if (isconnected()){
                    Log.d("wifi", "onCreate: yes ");
                }

            }
            @Override
            public void onLost(Network network) {
                // network unavailable
                try {
                    alert_dialog();
                } catch (Exception e) {
                    Log.d("wifi", "Show Dialog: " + e.getMessage());
                }
                if (!isconnected()) {
                    Log.d("wifi", "onCreate: no ");
                }
            }
        };

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest request = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }




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
    public boolean isconnected(){
        boolean connected = false;
        ConnectivityManager connectivityManager1 = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager1.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            return true;
        }
        else {
            // don't have internet


            return false;
        }


    }
    public void alert_dialog(){

        new AlertDialog.Builder(Pic_image_background.this)
                .setTitle("Error")
                .setMessage("Internet not available, Cross check your internet connectivity and try again later...")
                .setCancelable(false)
                .setIcon(R.drawable.ic_baseline_wifi_off_24)
                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                        if (isconnected()){
                            dialog.dismiss();

                        }else {
                            alert_dialog();
                        }
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Pic_image_background.this, MainActivity.class);
        startActivity(intent);
        finish();

        //super.onBackPressed();
    }
}