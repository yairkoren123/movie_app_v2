 package com.example.drawer_try.ui.Setting;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawer_try.MainActivity;
import com.example.drawer_try.R;
import com.example.drawer_try.databinding.SettingFragmentBinding;
import com.example.drawer_try.modle.FlowerAdapter;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.singup.SingUp_Fragment;
import com.example.drawer_try.singup.ToData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    //firebase
    private FirebaseFirestore firebaseFirestore;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");
    private DocumentReference cool = db.collection("shopping")
            .document("First Thoughts");
    private CollectionReference collectionReference = db.collection("good");
    private CollectionReference collectionReference1 = db.collection("good");

    private DocumentReference movie_data_add = db.collection("good").document();

    FirebaseAuth auth;

    public String current_email = "" ;
    String user = "";


    //layout
    private TextView sing_up_text_view , currnet_email_text_top ;
    private Button login_now , logout_now;
    private EditText pass , email;

    //setting


    private SettingViewModel settingViewModel;
    private SettingFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = SettingFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        EditText pass = getView().findViewById(R.id.login_here);
//        pass.setText("HI");

        pass = root.findViewById(R.id.login_passowrd);
        email = root.findViewById(R.id.login_email);
        currnet_email_text_top = root.findViewById(R.id.no_user_setting_textview);




        login_now = root.findViewById(R.id.login_login_button);
        sing_up_text_view = root.findViewById(R.id.login_sing_up_button);
        //sing_up_text_view.setVisibility(View.VISIBLE);
        //login_now.setVisibility(View.VISIBLE);
        Single_one single_one = Single_one.getInstance();
        user = single_one.getNow_login_email();
        if (user.equals("none")){
            user = "";
        }
        currnet_email_text_top.setText(user);





        sing_up_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingUp_Fragment nextFrag= new SingUp_Fragment();
                //ConstraintLayout constraintLayout = root.findViewById(R.id.constractor_setting);
                Single_one single_one = Single_one.getInstance();
                single_one.setThe_now_open_drawer("singup");
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.mail_countener4, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

                //sing_up_text_view.setVisibility(View.GONE);
                //login_now.setVisibility(View.GONE);
            }
        });

        login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    msg("you are login already");

                } else {

                    String the_email, the_pass, the_username;

                    the_email = email.getText().toString().trim();
                    the_pass = pass.getText().toString().trim();


                    if (the_email.isEmpty()) {
                        email.setError("pls enter");
                        email.requestFocus();

                    } else if (the_pass.isEmpty()) {
                        pass.setError("pls enter");
                        pass.requestFocus();

                    } else {
                        hideKeyboard(getActivity());
                        auth.signInWithEmailAndPassword(the_email, the_pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            // now login
                                            msg("now you are login in login page");
                                            Single_one single_one = Single_one.getInstance();
                                            single_one.setNow_login_email(the_email);
                                            single_one.setNow_login_pass(the_pass);

                                            ToData toData = new ToData();
                                            toData.setEmail(the_email);
                                            toData.setThe_moviesArrayList(single_one.getThe_love_movies());
//                                            movie_data_add = db.collection("good").document();

                                            currnet_email_text_top.setText(the_email);

                                            Intent intent = new Intent(getActivity(), MainActivity.class);

                                            // todo
                                            startActivity(intent);
                                            getActivity().finish();

//
//
//                                            movie_data_add.set(toData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                                                    Log.d("sec2", "onComplete: " + task.toString());
//
//                                                }
//                                            });

                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                msg(e.getMessage());
                            }

                        });

                        collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                                    Log.d("sec", "onSuccess: " + snapshots.getId());

                                    ToData shopping = snapshots.toObject(ToData.class);
                                    Log.d("snal2", "onSuccess: " +the_email);

                                    if (shopping != null) {

                                        Log.d("bobob", "onSuccess: shopping : " + shopping.getEmail()+ "    email :" +email);
                                        Log.d("bobob", "onSuccess: snapshots " + snapshots.getId());


                                        if (snapshots.getId().equals(the_email)) {
                                            Single_one single_one = Single_one.getInstance();

                                            Log.d("snal", "onSuccess: " + snapshots.toString());
                                            Log.d("sec", "onSuccess: target on : " + snapshots.getId());

                                            Log.d("sec2", "onSuccess: target on : " + shopping.getEmail());

                                            single_one.setUserImage(shopping.getBitmap());

                                            Log.d("getimage3", "onSuccess: " + shopping.getBitmap());



                                            single_one.setThe_love_movies(shopping.getThe_moviesArrayList());
                                            Log.d("getarray", "onSuccess: " + shopping.getThe_moviesArrayList().toString());

                                            Log.d("data", "onSuccess: " + shopping.getThe_moviesArrayList().toString());

                                            msg("auisdauishfdkjasfdfjsgnolh");
                                            email.setText("");
                                            pass.setText("");
//                                            Intent intent = new Intent(getActivity(),MainActivity.class);
//                                            startActivity(intent);
//                                            getActivity().finish();
                                            break;

                                        }
                                    }

                                }
                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.d("settingfrag", "onFailure: " + e.getMessage());
                                msg(e.getMessage());
                            }
                        });

                    }

//                    collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                            String data = "";
//                            for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
//
//                                Log.d("sec", "onSuccess: " + snapshots.getId());
//
//                                ToData shopping = snapshots.toObject(ToData.class);
//                                Log.d("snal2", "onSuccess: " +the_email);
//
//                                if (shopping.getEmail().equals(the_email)){
//                                    Log.d("snal", "onSuccess: " +snapshots.toString());
//                                    Log.d("sec", "onSuccess: target on : " + snapshots.getId());
//
//                                    Log.d("sec2", "onSuccess: target on : " + shopping.getEmail());
//
//                                    Single_one single_one = Single_one.getInstance();
//                                    single_one.setThe_love_movies(shopping.getThe_moviesArrayList());
//
//                                    Log.d("data", "onSuccess: " + shopping.getThe_moviesArrayList().toString());
//                                }
//
//                            }
//                        }
//
//
//                    });



                }
            }
        });
        logout_now = root.findViewById(R.id.logout_button);
        logout_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                Single_one single_one = Single_one.getInstance();

                // adding a simple move to the start
                The_movies one_none_move = new The_movies();
                one_none_move.setTitle("Shaun the Sheep Movie");
                one_none_move.setOriginal_language("en");
                one_none_move.setRelease_date("2015-02-05");
                one_none_move.setVote_average("7");
                one_none_move.setImage("/dhVYlfMNc2bfXPB83LLL00I4l9n.jpg");
                one_none_move.setImage_sec("/1eJLkZWuFVKr6OnNkMyqgoqkU1E.jpg");


                ArrayList<The_movies> s =  new ArrayList<>();
                s.add(one_none_move);
                single_one.setThe_love_movies(s);
                single_one.setNow_login_email("none");
                msg("now your are out");
                currnet_email_text_top.setText("none");


                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);




            }
        });


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EditText pass = getView().findViewById(R.id.login_pass);

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}