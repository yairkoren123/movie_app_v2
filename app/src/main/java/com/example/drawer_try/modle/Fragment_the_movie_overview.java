package com.example.drawer_try.modle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.drawer_try.R;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.singup.ToData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Fragment_the_movie_overview extends Fragment {

    private ArrayList<The_movies> the_movies_list = new ArrayList<>();
    private The_movies the_string_movie;

    private LottieAnimationView add_animationview;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private DocumentReference movie_data_add = db.collection("shopping").document();
    private DocumentReference movie_data_remove = db.collection("shopping").document();


    public Fragment_the_movie_overview() {
        // Required empty public constructor
    }



    public static Fragment_the_movie_overview newInstance() {
        Fragment_the_movie_overview fragment = new Fragment_the_movie_overview();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Single_one single_one = Single_one.getInstance();

        single_one.setFragment(this);

        View view = inflater.inflate(R.layout.fragment_the_movie_overview, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get the list of the movies
        Single_one single_one = Single_one.getInstance();

        if (single_one.getThe_now_open_drawer().equals("slideshow")){

        }else {
            single_one.setThe_now_open_drawer("overview");
        }

        the_string_movie = single_one.getValue_movie();
        the_movies_list = single_one.getMovies_list();

        Log.d("random2", "onViewCreated: " +the_movies_list.size());
        Log.d("random3", "onViewCreated: " +the_string_movie);


        if (the_string_movie.getTitle().equals("none")) {

            if (!the_movies_list.isEmpty()) {
                int index = (int) (Math.random() * the_movies_list.size());
                the_string_movie = the_movies_list.get(index);
            }

        }


        // the TEXT views
        TextView the_name_movie_overview = view.findViewById(R.id.textview_name_movie_overview);
        the_name_movie_overview.setText(the_string_movie.getTitle().toString().trim());

        TextView description_text_overview = view.findViewById(R.id.textview_description_overview);
        description_text_overview.setText(the_string_movie.getOverview().toString().trim());

        TextView release_date = view.findViewById(R.id.textview_release_date_overview);
        release_date.setText("Date release : "+the_string_movie.getRelease_date().toString().trim());

        TextView average_text_overview = view.findViewById(R.id.textview_vote_average_overview);
        average_text_overview.setText("average : " + the_string_movie.getVote_average().toString().trim());

        TextView language_text_overview = view.findViewById(R.id.textview_language_overview);
        language_text_overview.setText("language : " + the_string_movie.getOriginal_language().toString().trim());






        // anim

        add_animationview = view.findViewById(R.id.add_overview_button);

        // check if is user in quarry
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            add_animationview.setVisibility(View.GONE);
        }
        if (single_one.seeiflove(the_string_movie) == true){
            add_animationview.setAnimation(R.raw.minus);
        }else {
            add_animationview.setAnimation(R.raw.add);
        }
        // click listener to the add movie button and remove
        add_animationview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (single_one.seeiflove(the_string_movie) == false) {

                    add_animationview.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            add_animationview.setAnimation(R.raw.minus);
                            super.onAnimationEnd(animation);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);


                            Log.d("overviewadd", "onAnimationStart: ");
                            Single_one single_one = Single_one.getInstance();
                            single_one.addThe_love_movies(the_string_movie);

                            ToData toData = new ToData();
                            single_one = Single_one.getInstance();
                            toData.setEmail(single_one.getNow_login_email());
                            toData.setBitmap(String.valueOf(single_one.getUserImage()));
                            toData.setThe_moviesArrayList(Single_one.getInstance().getThe_love_movies());


                            movie_data_add = db.collection("good").document(single_one.getNow_login_email());
                            // add to the list
                            // todo
                            movie_data_add.
                                    set(toData).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                msg(the_string_movie.getTitle() + " was add to your list");
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
                    });
                    add_animationview.playAnimation();

                }else {
                    msg("try one more time");
                    msg("already in the list");
                    // now is minus
                    // todo call a alert dialog and say: " do you wont to remove {movie} from the Watchlist?
                    Snackbar snackbar = Snackbar.make(average_text_overview,"remove " + the_string_movie.getTitle() + " from Watchlist ?",Snackbar.LENGTH_LONG)
                            .setDuration(5000)
                            .setAction("YES", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Single_one single_one1 = Single_one.getInstance();
                                    single_one1.remove_one(the_string_movie);

                                    ToData toData = new ToData();
                                    single_one1 = Single_one.getInstance();
                                    toData.setEmail(single_one1.getNow_login_email());
                                    toData.setBitmap(String.valueOf(single_one1.getUserImage()));
                                    toData.setThe_moviesArrayList(Single_one.getInstance().getThe_love_movies());

                                    movie_data_remove = db.collection("good")
                                            .document(single_one1.getNow_login_email());
                                    // add to the list
                                    movie_data_remove.
                                            set(toData).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        msg(the_string_movie.getTitle() + " was remove from your list");
                                                        add_animationview.setAnimation(R.raw.add);


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
                            });
                    snackbar.show();



                }
            }
        });


        // 18 +
        TextView adult_overview = view.findViewById(R.id.adult_overview);
        if(the_string_movie.isAdult()){
            adult_overview.setVisibility(View.VISIBLE);
        }else {
            adult_overview.setVisibility(View.GONE);
        }


        // IMAGE
        String image_background ="https://image.tmdb.org/t/p/w500" + the_string_movie.getImage_sec() ;

        if (the_string_movie.image_sec == null || the_string_movie.image_sec.equals("null")){
            image_background = single_one.getNo_imgae_abl();

        }

        ImageView imageView_poster_background =  view.findViewById(R.id.imageview_background_overview);
        Glide.with(getContext())
                .load(image_background)
                .fitCenter()
                .into(imageView_poster_background);

        // new
        imageView_poster_background.setScaleType(ImageView.ScaleType.FIT_XY);




        String image_poster ="https://image.tmdb.org/t/p/w500" + the_string_movie.getImage() ;
        ImageView imageView_poster_poster =  view.findViewById(R.id.image_view_poster_overview);

        if (the_string_movie.image == null || the_string_movie.image.equals("null")){
            image_poster = single_one.getNo_imgae_abl();
            imageView_poster_poster.setVisibility(View.INVISIBLE);
        }
        Glide.with(getContext())
                .load(image_poster)
                .fitCenter()
                .into(imageView_poster_poster);


        // scrollview
        ScrollView scrollView_overview = view.findViewById(R.id.scrool_view_overview);
        scrollView_overview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView_overview.getScrollY(); // For ScrollView

                Log.d("ppp", "onScrollChanged: " + scrollY);
                // DO SOMETHING WITH THE SCROLL COORDINATES
                if (scrollY > 30){
                    imageView_poster_poster.setAlpha((float) 0.4);
                }else {
                    imageView_poster_poster.setAlpha((float) 0.9);

                }
            }
        });


        The_movies cool = new The_movies();
        cool.setTitle("none");
        single_one.setValue_movie(cool);
        Log.d("TAG12", "onViewCreated: " + the_string_movie.getTitle());

    }
    private void msg (String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG)
                .show();
    }



}