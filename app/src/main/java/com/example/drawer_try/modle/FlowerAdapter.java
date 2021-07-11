package com.example.drawer_try.modle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FlowerAdapter extends BaseAdapter  {
    private Context mContext;

    private FirebaseAuth firebaseAuth;




    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference movie_data_add = db.collection("good").document();
    private DocumentReference movie_data_remove = db.collection("good").document();

    private CollectionReference movie_data_add2 = db.collection("good");


    private Single_one single_one = Single_one.getInstance();

    private ArrayList<The_movies> movies;



    private static LayoutInflater inflate = null;

    public FlowerAdapter(Context mContext, ArrayList<The_movies> movies) {
        this.mContext = mContext;
        this.movies = movies;

        inflate = (LayoutInflater) mContext
                .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    public FlowerAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return movies.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        itemView = inflate.inflate(R.layout.grid_item,null);


        ImageView imageView_poster_movie = itemView.findViewById(R.id.image_rec);
        TextView textView_title_movie = itemView.findViewById(R.id.text_rec);

        The_movies selectedMovie = movies.get(position);

        textView_title_movie.setText(selectedMovie.getTitle());
        String image ="https://image.tmdb.org/t/p/w500" + selectedMovie.image ;

        if (selectedMovie.image == null || selectedMovie.image.equals("") || image.equals("null")){
            image ="null";

        }
        single_one = Single_one.getInstance();
        Log.d("images", "getView: " + image);
        if (image.equals("https://image.tmdb.org/t/p/w500null") || image == "null") {

            if (selectedMovie.getImage_sec() != null) {
                Log.d("image_sec111", "getView: image null and sec : " + selectedMovie.image_sec );
                image = "https://image.tmdb.org/t/p/w500" + selectedMovie.getImage_sec();
                Glide.with(mContext)
                        .load(image)
                        .centerCrop()
                        .into(imageView_poster_movie);

                imageView_poster_movie.setScaleType(ImageView.ScaleType.FIT_XY);

            }if (selectedMovie.image_sec == null || selectedMovie.image_sec.equals("null")){
                Glide.with(mContext)
                        .load(single_one.getNo_imgae_abl())
                        .centerCrop()
                        .into(imageView_poster_movie);
                imageView_poster_movie.setScaleType(ImageView.ScaleType.FIT_XY);
            }

        }else {


            Glide.with(mContext)
                    .load(image)
                    .fitCenter()
                    .into(imageView_poster_movie);

            imageView_poster_movie.setScaleType(ImageView.ScaleType.FIT_XY);
        }



//        Picasso.get()
//                .load(image)
//                .fit()
//                .centerCrop()
//
//                .into(imageView_poster_movie);

        // button love

        LottieAnimationView add = itemView.findViewById(R.id.button_add_love_rec);



        if (single_one.seeiflove(selectedMovie) == true){
            add.setAnimation(R.raw.minus);
        }else {
            add.setAnimation(R.raw.add);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            add.setVisibility(View.INVISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //movie_data_add = db.collection("shopping").document();
                //movie_data_add.set(shopping);

                if (single_one.seeiflove(selectedMovie) == false) {



//                Log.d("button", "getView: button" + position);
//                single_one.addThe_love_movies(selectedMovie);
                    add.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            add.setAnimation(R.raw.minus);
                        }

                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            Log.d("button", "getView: button" + position);
                            single_one.addThe_love_movies(selectedMovie);

                            ToData toData = new ToData();
                            single_one = Single_one.getInstance();
                            toData.setEmail(single_one.getNow_login_email());
                            toData.setFriends(single_one.getFriend_list());
                            toData.setBitmap(String.valueOf(single_one.getUserImage()));
                            toData.setThe_moviesArrayList(Single_one.getInstance().getThe_love_movies());

                            movie_data_add = db.collection("good")
                                    .document(single_one.getNow_login_email());
                            // add to the list
                            // set(toData)
                            movie_data_add.
                                    update("the_moviesArrayList",single_one.getThe_love_movies()).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                msg(selectedMovie.getTitle() + " was add to your list");
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
                            //msg(selectedMovie.getTitle() + " was add to your list");
                        }
                    });
                    add.playAnimation();



                }else {
                    msg("already in the list");
                    // now is minus
                    // todo call a alert dialog and say: " do you wont to remove {movie} from the Watchlist?
                    Snackbar snackbar = Snackbar.make(imageView_poster_movie,"remove " + selectedMovie.getTitle() + " from Watchlist ?",Snackbar.LENGTH_LONG)
                            .setDuration(5000)
                            .setAction("YES", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    single_one = Single_one.getInstance();
                                    single_one.remove_one(selectedMovie);

                                    single_one = Single_one.getInstance();

//                                    ToData toData = new ToData();
//                                    single_one = Single_one.getInstance();
//                                    toData.setEmail(single_one.getNow_login_email());
//                                    toData.setBitmap(String.valueOf(single_one.getUserImage()));
//                                    toData.setThe_moviesArrayList(Single_one.getInstance().getThe_love_movies());

                                    movie_data_remove = db.collection("good")
                                            .document(single_one.getNow_login_email());
                                    // add to the list
                                    movie_data_remove.
                                            update("the_moviesArrayList",single_one.getThe_love_movies()).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if (task.isComplete()) {
                                                        msg(selectedMovie.getTitle() + " was remove from your list");
                                                        add.setAnimation(R.raw.add);
                                                        notifyDataSetChanged();

                                                    } else {
                                                        msg(task.getException().getMessage());
                                                        Log.d("EEE", "onComplete: error" );
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


        return itemView;
    }

    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }
    private void msg (String text){
        Toast.makeText(mContext,text,Toast.LENGTH_LONG)
                .show();
    }

}