package com.example.drawer_try.Activitys_stuffs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.drawer_try.R;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.singup.ToData;
import com.example.drawer_try.ui.AddFreind.Search_Account_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Activity_Account_overview extends AppCompatActivity{

    TextView follower_amount, watchlist_amount ,avg_amount , username;
    ImageView image_account , image_background;
    Button add_follow;
    Button back_button;

    String the_image_background;
    RecyclerView recyclerView;

    double avg_movies = 0;
    int count_movies = 0;
    String the_avg = "0";

    recycler_Adpter_HORIZONTAL adpterHORIZONTAL;


    ArrayList<The_movies> other_array_list = new ArrayList<>();


    Single_one single_one= Single_one.getInstance();


    String email;

    // firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference1 = db.collection("good");
    private DocumentReference movie_data_add = db.collection("good").document();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_overview);
        // the over view of the profiles


        follower_amount = findViewById(R.id.textview_followers_ammount_a);
        watchlist_amount = findViewById(R.id.textview_watchlist_account_a);
        avg_amount = findViewById(R.id.textview_watchlist_avg_a);
        add_follow = findViewById(R.id.text_acount_follow_add_a);
        username = findViewById(R.id.username_acount_a);
        image_account = findViewById(R.id.image_view_back_image_account);
        image_background = findViewById(R.id.image_backgroun_user_top);

        back_button = findViewById(R.id.button_back_account);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // recyclerview
        recyclerView = findViewById(R.id.recycler_view_a);

        Bundle extras = getIntent().getExtras();

        email = extras.getString(Search_Account_Fragment.EXTRAOVERVIEW);
        username.setText(email);

        getSupportActionBar().setTitle(" Profile  : " + email);


        String image = extras.getString(Search_Account_Fragment.EXTRAIMAGE);

        switch (image){
            case "smile_0.png":
                image_account.setBackgroundResource(R.drawable.smile_0);
                break;
            case "smile_1.png":
                image_account.setBackgroundResource(R.drawable.smile_1);
                break;
            case "smile_2.png":
                image_account.setBackgroundResource(R.drawable.smile_2);
                break;
            case "smile_3.png":
                image_account.setBackgroundResource(R.drawable.smile_3);
                break;
            case "smile_4.png":
                image_account.setBackgroundResource(R.drawable.smile_4);
                break;
            case "smile_5.png":
                image_account.setBackgroundResource(R.drawable.smile_5);
                break;
            case "none":
                image_account.setBackgroundResource(R.drawable.smile_7);
                break;
        }

        get_account();

        single_one = Single_one.getInstance();

        single_one.setThe_now_open_drawer("overview_acount");

        boolean isthere = single_one.seeiffollow(email);
        if (isthere == false) {
            // not in the list
            add_follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_24, 0, 0, 0);
        }else {
            // in the list
            add_follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_remove_24, 0, 0, 0);
        }

        add_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo on click follow button
                        boolean isthere = false;

                        Single_one single_one = Single_one.getInstance();
                        isthere = single_one.seeiffollow(email);
                        if (isthere == false) {
                            // not in the list

                            add_follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_remove_24, 0, 0, 0);


                            single_one.add_to_friend(email);

                            movie_data_add = db.collection("good")
                                    .document(single_one.getNow_login_email());
                            // add to the list
                            // set(toData)
                            movie_data_add.
                                    update("friends", single_one.getFriend_list()).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                msg("add to the follower list");
                                            } else {
                                                msg(task.getException().getMessage());
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    msg(e.getMessage());
                                }
                            });

                        }else {
                            // need to remove from the list
                            add_follow.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_24, 0, 0, 0);


                            ArrayList<String> needremov = single_one.getFriend_list();
                            needremov.remove(email);
                            single_one.setFriend_list(needremov);

                            movie_data_add = db.collection("good")
                                    .document(single_one.getNow_login_email());
                            // add to the list
                            // set(toData)
                            movie_data_add.
                                    update("friends", single_one.getFriend_list()).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                msg("add to the follower list");
                                            } else {
                                                msg(task.getException().getMessage());
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    msg(e.getMessage());
                                }
                            });
                        }
                    }
                });


        // recyclerview

        Single_one single_one = Single_one.getInstance();

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                Activity_Account_overview.this,LinearLayoutManager.HORIZONTAL
                ,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        // adpter


    }
    private void get_account(){
        collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {


                    ToData shopping = snapshots.toObject(ToData.class);
                    Log.d("6snal2", "onSuccess: " + email);

                    if (shopping != null) {

                        if (shopping.getEmail().equals(email)) {
                            // if we find the value
                                count_movies = shopping.getThe_moviesArrayList().size();

                                follower_amount.setText(String.valueOf(shopping.getFriends().size()));
                                watchlist_amount.setText(String.valueOf(count_movies));


                                the_image_background = shopping.getImage_background();

                                Glide.with(getApplicationContext())
                                        .load(the_image_background)
                                        .fitCenter()
                                        .into(image_background);

                            image_background.setScaleType(ImageView.ScaleType.FIT_XY);


                            // .centerCrop()



                            for (int i = 0; i < count_movies; i++) {
                                avg_movies += Double.parseDouble(shopping.getThe_moviesArrayList().get(i).getVote_average());
                            }
                            if (avg_movies == 0){
                                the_avg = "0";

                            }else {


                                DecimalFormat df = new DecimalFormat("#.#");
                                df.setRoundingMode(RoundingMode.CEILING);
                                avg_movies = avg_movies / count_movies;

                                the_avg = df.format(avg_movies);
                            }

                            Log.d("6avg", "onSuccess: " + the_avg);

                            avg_amount.setText(String.valueOf(the_avg));
                            other_array_list = shopping.getThe_moviesArrayList();

                            // adpter

                            adpterHORIZONTAL = new recycler_Adpter_HORIZONTAL(other_array_list,getApplicationContext());
                            recyclerView.setAdapter(adpterHORIZONTAL);

                            Log.d("6data123", "onSuccess: " + shopping.getThe_moviesArrayList().toString());
                            break;
                        }
                    }
                }
            }
        });
    }




    private void msg(String text){
        Toast.makeText(Activity_Account_overview.this,text,Toast.LENGTH_LONG)
                .show();
    }


}