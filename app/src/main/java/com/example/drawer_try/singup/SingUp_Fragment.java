package com.example.drawer_try.singup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drawer_try.MainActivity;
import com.example.drawer_try.R;
import com.example.drawer_try.singletonClass.Single_one;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.sql.Struct;
import java.util.ArrayList;


public class SingUp_Fragment extends Fragment {



    FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference movie_data_add = db.collection("good").document();



    public SingUp_Fragment() {
        // Required empty public constructor
    }


    public static SingUp_Fragment newInstance(String param1, String param2) {
        SingUp_Fragment fragment = new SingUp_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText pass_sing = getView().findViewById(R.id.sing_pass);
        EditText email_sing = getView().findViewById(R.id.sing_email);

        Button login_sing_up_button = getView().findViewById(R.id.sing_button_create);

        auth = FirebaseAuth.getInstance();

        login_sing_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String the_email , the_pass , the_username;

                the_email = email_sing.getText().toString().trim();
                the_pass = pass_sing.getText().toString().trim();



                if(the_email.isEmpty()){
                    email_sing.setError("pls enter");
                    email_sing.requestFocus();

                }else if(the_pass.isEmpty()) {
                    pass_sing.setError("pls enter");
                    pass_sing.requestFocus();

                }else {
                    auth.createUserWithEmailAndPassword(the_email,the_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                // now login
                                msg("user name and email and pass are save now in the system");
                                //
                                Single_one single_one = Single_one.getInstance();
                                single_one.setNow_login_email(the_email);
                                single_one.setNow_login_pass(the_pass);

                                // hide hideKeyboard and start new MAin activity
                                hideKeyboard(getActivity());

                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }else {
                                msg(task.getException().getMessage());

                            }
                        }
                    });

                    // here put in data base

                    Single_one single_one = Single_one.getInstance();

                    single_one.setFriend_list(new ArrayList<>());

                    ToData toData = new ToData();
                    single_one = Single_one.getInstance();
                    toData.setEmail(the_email);
                    toData.setFriends(new ArrayList<>());
                    toData.setBitmap("smile_1.png");
                    toData.setThe_moviesArrayList(Single_one.getInstance().getThe_love_movies());

                    movie_data_add = db.collection("good")
                            .document(the_email);

                    movie_data_add
                            .set(toData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("winwin", "onComplete: ");
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




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sing_up_, container, false);



        return view;
    }
    public void msg(String text){
        Toast.makeText(getActivity(),text,Toast.LENGTH_LONG)
                .show();
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String email = user.getEmail().toString().trim();
            Log.d("username", "onStart: " + user + "email " + email);
            msg("you are login already");
             auth.getCurrentUser();

        }
    }
}