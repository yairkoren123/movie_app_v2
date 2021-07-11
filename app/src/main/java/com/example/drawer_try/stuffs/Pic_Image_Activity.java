package com.example.drawer_try.stuffs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawer_try.MainActivity;
import com.example.drawer_try.R;
import com.example.drawer_try.singletonClass.Single_one;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pic_Image_Activity extends AppCompatActivity {

    // layout
    GridLayout mgrid;
    String email_now = "";
    TextView username;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_image);


        // set title activity
        Pic_Image_Activity.this.setTitle("Image for Profile");


        //layout

        mgrid = findViewById(R.id.mainGrid_image);

        username = findViewById(R.id.email_pic_image);


        Intent intent=getIntent();
        email_now = intent.getStringExtra(MainActivity.EXTRAEMAIL);
        username.setText(email_now);

        if (email_now.equals(MainActivity.no_user_string_main)){
            LinearLayout private_screen = findViewById(R.id.no_account_linar_image_add_friend);
            TextView setImageText = findViewById(R.id.title_view_open_image);

            private_screen.setVisibility(View.VISIBLE);
            setImageText.setVisibility(View.INVISIBLE);
            mgrid.setVisibility(View.INVISIBLE);
        }




        setSingleEvent(mgrid);



    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finali = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    msg("" + finali);
                    Single_one single_one = Single_one.getInstance();
                    Intent intent;

                    if (finali == 0) {
                        //0
                        single_one.setUserImage("smile_0.png");


                    } else if (finali == 1) {
                        single_one.setUserImage("smile_1.png");


                        //1

                    } else if (finali == 2) {
                        single_one.setUserImage("smile_2.png");
                        // 2 NAME
                    } else if (finali == 3) {
                        single_one.setUserImage("smile_3.png");



                        //3

                    } else if (finali == 4) {
                        single_one.setUserImage("smile_4.png");


                        //4

                    } else if (finali == 5) {
                        single_one.setUserImage("smile_5.png");


                        //5

                    } else {
                        single_one.setUserImage("smile_0.png");

                        //Error

                    }
                    // back to the main activity
                    msg(single_one.getUserImage());

                    DocumentReference documentReference = db.collection("good").document(email_now);

                    documentReference.update("bitmap",single_one.getUserImage())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    msg("image set");
                                }
                            });

                    intent = new Intent(Pic_Image_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            });
        }
    }

    private void msg(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
//        super.onBackPressed();
    }
}