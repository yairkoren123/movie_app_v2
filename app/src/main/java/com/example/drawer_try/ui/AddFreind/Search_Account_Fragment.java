package com.example.drawer_try.ui.AddFreind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drawer_try.Activitys_stuffs.Activity_Account_overview;
import com.example.drawer_try.R;
import com.example.drawer_try.databinding.AddFriendsFragmentBinding;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.singup.ToData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class Search_Account_Fragment extends Fragment {

    //layout
    private ListView listView_friends;
    private CardView single_card;


    private AddFriendsViewModel mViewModel;
    int search_times = 0;

    private AddFriendsViewModel addFriendsViewModel;
    private AddFriendsFragmentBinding binding;
    private ArrayList<ToData> friendArrayList;

    String the_user_Image = "";
    String text = "";
    boolean search_by_text = false;
    ArrayList<String> friend_list_from_single_my  = new ArrayList<>();

    Single_one single_one= Single_one.getInstance();

    // firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference1 = db.collection("good");
    private DocumentReference movie_data_add = db.collection("good").document();




    //firestore / firebase
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public static String EXTRAOVERVIEW = "email";
    public static String EXTRAIMAGE = "image";


    public Search_Account_Fragment(String theText) {
        // Required empty public constructor
        text = theText;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search__account_, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // the code here
        friendArrayList = new ArrayList<>();
        listView_friends = view.findViewById(R.id.list_view_friends);



        if (text.contains("@")) {
            Log.d("6cou", "onClick: text have '@' text : " + text);
            search_by_text = true;
        }

        get_accounts();

    }

    public void get_accounts(){


        FirebaseAuth auth = FirebaseAuth.getInstance();
        Single_one single_one = Single_one.getInstance();

        // get the friend list from singleton
        friend_list_from_single_my = single_one.getFriend_list();


        String email = single_one.getNow_login_email();

        collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                    Log.d("sec", "onSuccess: " + snapshots.getId());

                    ToData shopping = snapshots.toObject(ToData.class);

                    Log.d("snal2", "onSuccess: " +email);

                    Log.d("friend_list", "onSuccess: " + single_one.getFriend_list().toString());

                    if (shopping != null) {


                        if (search_by_text == true){
                            // by followers

                            if (text.equals("ABCDEFG@") ){


                                for (int i = 0; i < friend_list_from_single_my.size(); i++) {

                                    if (shopping.getEmail().equals(friend_list_from_single_my.get(i))){
                                        the_user_Image = shopping.getBitmap();

                                        Log.d("getimage123", "onSuccess: " + the_user_Image);

                                        ToData toData = new ToData();
                                        toData.setEmail(shopping.getEmail());
                                        toData.setBitmap(shopping.getBitmap());
                                        toData.setLast_add(shopping.getLast_add());
                                        toData.setThe_moviesArrayList(shopping.getThe_moviesArrayList());
                                        friendArrayList.add(toData);
                                    }

                                }
                                if (single_one.getNow_login_email().contains("@")){
                                    if (friendArrayList.size() == 0){
                                    }
                                }


                            }

                            else if (shopping.getEmail().equals(text)) {
                                // by text


                                Log.d("snal123", "onSuccess: " + snapshots.toString());
                                Log.d("sec123", "onSuccess: target on : " + snapshots.getId());

                                Log.d("sec123", "onSuccess: target on : " + shopping.getEmail());

                                the_user_Image = shopping.getBitmap();

                                Log.d("getimage123", "onSuccess: " + the_user_Image);

                                ToData toData = new ToData();
                                toData.setEmail(shopping.getEmail());
                                toData.setBitmap(shopping.getBitmap());
                                toData.setLast_add(shopping.getLast_add());
                                toData.setThe_moviesArrayList(shopping.getThe_moviesArrayList());
                                friendArrayList.add(toData);


                                Log.d("data123", "onSuccess: " + shopping.getThe_moviesArrayList().toString());
                                break;
                            }
                        }else {
                            // the size of the search
                            if (search_times >= 30){
                                break;
                            }
                            // all user

                            if (!shopping.getEmail().equals(email)) {
                                Log.d("snal123", "onSuccess: " + snapshots.toString());
                                Log.d("sec123", "onSuccess: target on : " + snapshots.getId());

                                Log.d("sec123", "onSuccess: target on : " + shopping.getEmail());

                                the_user_Image = shopping.getBitmap();

                                Log.d("getimage123", "onSuccess: " + the_user_Image);

                                ToData toData = new ToData();
                                toData.setEmail(shopping.getEmail());
                                toData.setBitmap(shopping.getBitmap());
                                toData.setLast_add(shopping.getLast_add());
                                toData.setThe_moviesArrayList(shopping.getThe_moviesArrayList());
                                friendArrayList.add(toData);


                                Log.d("data123", "onSuccess: " + shopping.getThe_moviesArrayList().toString());
                                search_times++;
                            }
                        }

                    }

                }
                // after for loop
                CustomAdaper customAdaper = new CustomAdaper();
                Collections.shuffle(friendArrayList);
                listView_friends.setAdapter(customAdaper);

                // get the position of the account
                listView_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //here you can use the position to determine what checkbox to check
                        Log.d("6pos", "onItemClick: " + position);
                    }
                });
                customAdaper.notifyDataSetChanged();
                search_by_text = false;

            }


        });


    }

    class CustomAdaper extends BaseAdapter implements AdapterView.OnItemClickListener {

        //public ArrayList<ToData> arrayList = new ArrayList<>();


        @Override
        public int getCount() {
            return friendArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.d("county", "getView: " );

            View view = getLayoutInflater().inflate(R.layout.search_friend_item, null);

            Single_one single_one = Single_one.getInstance();


            TextView username = (TextView) view.findViewById(R.id.account_username_search_single);
            ImageView image = view.findViewById(R.id.account_image_search_single);
            Button folloingButton = view.findViewById(R.id.folloing_button_single);

            single_card = view.findViewById(R.id.card_view_single_account);




            Log.d("county", "getView: " + username.getText());


//            if (citysArrayList1.get(position).getCity() == null) {
//                textView.setText("not ");
//            } else {
//                textView.setText(values[position]); // citysArrayList1.get(postion).getData - error
//            }

            username.setText(friendArrayList.get(position).getEmail());

            String image_text = friendArrayList.get(position).getBitmap();
            switch (friendArrayList.get(position).getBitmap()){
                case "smile_0.png":
                    image.setBackgroundResource(R.drawable.smile_0);
                    break;
                case "smile_1.png":
                    image.setBackgroundResource(R.drawable.smile_1);
                    break;
                case "smile_2.png":
                    image.setBackgroundResource(R.drawable.smile_2);
                    break;
                case "smile_3.png":
                    image.setBackgroundResource(R.drawable.smile_3);
                    break;
                case "smile_4.png":
                    image.setBackgroundResource(R.drawable.smile_4);
                    break;
                case "smile_5.png":
                    image.setBackgroundResource(R.drawable.smile_5);
                    break;
                case "none":
                    image.setBackgroundResource(R.drawable.smile_7);
                    break;
            }

            Log.d("friends", "getView: " + friendArrayList.get(position).getEmail());

//            String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
//                    shoppingArrayList.get(position).getTimeAdded().getSeconds() * 1000);

//            time.setText(timeAgo);

            String email = friendArrayList.get(position).getEmail();

            //check is its need to be plus or minus
            boolean isthere = single_one.seeiffollow(email);
            if (isthere == false) {
                // not in the list
                folloingButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_24, 0, 0, 0);

            }else {
                // in the list
                folloingButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_remove_24, 0, 0, 0);

            }
            // click on card view
            single_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // card view click
                    Log.d("6card", "onClick: " + email);



                    // start over view on account
                    Intent intent = new Intent(getActivity(), Activity_Account_overview.class);
                    intent.putExtra(EXTRAOVERVIEW,email);
                    intent.putExtra(EXTRAIMAGE,image_text);
                    startActivity(intent);
                }
            });


            folloingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    boolean isthere = false;

                    Single_one single_one = Single_one.getInstance();
                    isthere = single_one.seeiffollow(email);
                    if (isthere == false) {
                        // not in the list

                        folloingButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_remove_24, 0, 0, 0);


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
                        folloingButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_add_24, 0, 0, 0);


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



            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("121", "onItemClick: " + id);

            Log.d("121", "onItemClick: " + position);
        }
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

    public void msg(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG)
                .show();
    }



}