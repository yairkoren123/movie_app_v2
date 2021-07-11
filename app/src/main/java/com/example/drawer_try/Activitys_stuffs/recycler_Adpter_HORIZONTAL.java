package com.example.drawer_try.Activitys_stuffs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.drawer_try.R;
import com.example.drawer_try.modle.Fragment_the_movie_overview;
import com.example.drawer_try.modle.The_movies;
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

public class recycler_Adpter_HORIZONTAL extends RecyclerView.Adapter<recycler_Adpter_HORIZONTAL.ViewHolder> {
    ArrayList<The_movies> arrayList;
    Context context;


    // on ckick

    // firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference movie_data_add = db.collection("good").document();
    private DocumentReference movie_data_remove = db.collection("good").document();

    Single_one single_one = Single_one.getInstance();


    public recycler_Adpter_HORIZONTAL(ArrayList<The_movies> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        Log.d("recycler", "recycler_Adpter_HORIZONTAL: " + arrayList.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create view
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        // set logo to images
        //holder.imageView.setImageResource(arrayList.get(position).getImage());

        The_movies selectedMovie = arrayList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg(holder.textView.getText().toString());
                for (int i = 0; i < arrayList.size(); i++) {
                    if(arrayList.get(i).getTitle().equals(holder.textView.getText().toString())){

                        The_movies sected_movie_acount = arrayList.get(i);
                        // find the value we type on in recycler view
                        Log.d("6find", "onClick: " + sected_movie_acount.getTitle() );

                        Single_one single_one = Single_one.getInstance();
                        single_one.setValue_movie(sected_movie_acount);


                        break;

                    }

                }
            }
        });

        String image ="https://image.tmdb.org/t/p/w500" + selectedMovie.getImage() ;
        Log.d("images1234", "getView: " + image);


        if (image.equals("https://image.tmdb.org/t/p/w500null") || selectedMovie.getImage() == null || selectedMovie.getImage() == "" || selectedMovie.getImage() == "null"){
        image = "null";

        Log.d("image123", "getView: " + image);
        if (image.equals("null") || image.equals("https://image.tmdb.org/t/p/w500null")) {
            Log.d("opop", "onBindViewHolder: " + image);

            if (selectedMovie.getImage_sec() != null) {
                image = "https://image.tmdb.org/t/p/w500" + selectedMovie.getImage_sec();
                Glide.with(context)
                        .load(image)
                        .centerCrop()
                        .into(holder.imageView);
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);


                Log.d("opop", "onBindViewHolder: " + image);

            }
            if (image.equals("https://image.tmdb.org/t/p/w500null")) {
                // no images
                Glide.with(context)
                        .load(single_one.getNo_imgae_abl())
                        .centerCrop()
                        .into(holder.imageView);
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //Glide.with(getView(position,convertView,parent)).load(getImage("pic")).into(imageView_poster_movie);

            }
        }
        }else {

            Glide.with(context)
                    .load(image)
                    .fitCenter()
                    .into(holder.imageView);

            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }



        // set text

        holder.textView.setText(arrayList.get(position).getTitle());
        holder.stars_avg.setText(arrayList.get(position).getVote_average());

        if (single_one.seeiflove(selectedMovie) == true){
            holder.add.setAnimation(R.raw.minus);
        }else {
            holder.add.setAnimation(R.raw.add);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null){
            holder.add.setVisibility(View.INVISIBLE);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //movie_data_add = db.collection("shopping").document();
                //movie_data_add.set(shopping);

                if (single_one.seeiflove(selectedMovie) == false) {



//                Log.d("button", "getView: button" + position);
//                single_one.addThe_love_movies(selectedMovie);
                    holder.add.addAnimatorListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            holder.add.setAnimation(R.raw.minus);
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
                    holder.add.playAnimation();



                }else {
                    msg("already in the list");
                    // now is minus
                    // todo call a alert dialog and say: " do you wont to remove {movie} from the Watchlist?
                    Snackbar snackbar = Snackbar.make(holder.imageView,"remove " + selectedMovie.getTitle() + " from Watchlist ?",Snackbar.LENGTH_LONG)
                            .setDuration(5000)
                            .setAction("YES", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    single_one = Single_one.getInstance();
                                    single_one.remove_one(selectedMovie);

                                    ToData toData = new ToData();
                                    single_one = Single_one.getInstance();
                                    toData.setEmail(single_one.getNow_login_email());
                                    toData.setBitmap(String.valueOf(single_one.getUserImage()));
                                    toData.setThe_moviesArrayList(Single_one.getInstance().getThe_love_movies());

                                    movie_data_remove = db.collection("good")
                                            .document(single_one.getNow_login_email());
                                    // add to the list
                                    movie_data_remove.
                                            update("the_moviesArrayList",single_one.getThe_love_movies()).
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        msg(selectedMovie.getTitle() + " was remove from your list");
                                                        holder.add.setAnimation(R.raw.add);
                                                        notifyDataSetChanged();

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


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // here values
        ImageView imageView;
        TextView textView, stars_avg;
        LottieAnimationView add;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            // find by id ; here
            imageView = itemView.findViewById(R.id.image_rec);
            textView = itemView.findViewById(R.id.text_rec);
            add = itemView.findViewById(R.id.button_add_love_rec);
            stars_avg = itemView.findViewById(R.id.stars_rec);



        }




    }
    
    private void msg (String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG)
                .show();
    }
}
