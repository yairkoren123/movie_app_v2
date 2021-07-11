package com.example.drawer_try.stuffs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drawer_try.MainActivity;
import com.example.drawer_try.R;
import com.example.drawer_try.singletonClass.Single_one;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class my_back_adpter extends RecyclerView.Adapter<my_back_adpter.ViewHolder> {


    ArrayList<String> back_array = new ArrayList<>();
    Context context;

    // firebase

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference movie_data_add = db.collection("good").document();
    private DocumentReference movie_data_add_image_back = db.collection("good").document();

    public my_back_adpter(ArrayList<String> back_array, Pic_image_background activity) {
        this.back_array = back_array;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.background_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.textViewName.setText("helllo");

        String image_background =back_array.get(position) ;
        Glide.with(context)
                .load(image_background)
                .fitCenter()
                .into(holder.imageViewName);

        // .centerCrop()


        holder.imageViewName.setScaleType(ImageView.ScaleType.FIT_XY);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set the image_back to fire base
                Single_one single_one = Single_one.getInstance();
                movie_data_add_image_back = db.collection("good")
                        .document(single_one.getNow_login_email());
                // add to the list
                movie_data_add_image_back.
                        update("image_background",image_background).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if (task.isComplete()) {
                                    msg(" click on image number :  " + position);
                                    notifyDataSetChanged();

                                    //Intent intent = new Intent(context,MainActivity.class);


//                                    Intent intent = new Intent(Pic_image_background.this, MainActivity.class);
//                                    Pic_image_background.startActivity(intent);
//                                    Pic_Image_Activity.finish();

                                    // start a new activity and finish this activity
                                    Intent intent1=new Intent(context,MainActivity.class);
                                    context.startActivity(intent1);
                                    ((Activity)context).finish();

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
    }

    @Override
    public int getItemCount() {
        return back_array.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewName = itemView.findViewById(R.id.image_backgroun_rec);


        }
    }

    private void msg (String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG)
                .show();
    }

}
