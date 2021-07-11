package com.example.drawer_try;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.drawer_try.modle.Fragment_the_movie_overview;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.singup.About;
import com.example.drawer_try.singup.ToData;
import com.example.drawer_try.stuffs.Pic_Image_Activity;
import com.example.drawer_try.stuffs.Pic_image_background;
import com.example.drawer_try.ui.AddFreind.Add_Friends_Fragment;
import com.example.drawer_try.ui.FriendList.List_Friends_Fragment;
import com.example.drawer_try.ui.Search.SearchBox;
import com.example.drawer_try.ui.Search.SearchFragment;
import com.example.drawer_try.ui.Setting.SettingFragment;
import com.example.drawer_try.ui.gallery.GalleryFragment;
import com.example.drawer_try.ui.home.HomeFragment;
import com.example.drawer_try.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.drawer_try.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // progressDialog
    ProgressDialog progressDialog;


    public static final String EXTRAEMAIL = "extraEmail";
    public static final String no_user_string_main = "Login/Sing to see your profile";

    FirebaseDatabase database;
    DatabaseReference reference;


    MenuItem menuItem_to_select = null;


    private AppBarConfiguration mAppBarConfiguration;
    //ActivityMainBinding
    private ActivityMainBinding binding;

    private FirebaseFirestore firebaseFirestore;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");
    private DocumentReference cool = db.collection("good")
            .document("First Thoughts");
    private CollectionReference collectionReference1 = db.collection("good");

    private DocumentReference movie_data_add = db.collection("good").document();

    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseUser currentUser;

    private String the_user_Image = "none";

    private Single_one single_one = Single_one.getInstance();

    DrawerLayout drawer;
    ImageView image_user;
    ImageView imageView_main_back;

    NavigationView navigationView;


    String email_now = "none";
    String image_now = "none";
    String now_image_background = "none";
    public String the_now_open_drawer = "home";

    boolean is_back_press = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.setTitle("hi");


        Window window = getWindow();

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_slideshow,
                R.id.nav_search,
                R.id.nav_watchlist,
                R.id.nav_friends,
                R.id.nav_add_friends,
                R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();



        // set color to the Drawer
        //navigationView.setBackgroundColor(Color.parseColor("#"));
        navigationView.setBackgroundColor(Color.WHITE);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



                authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        currentUser = firebaseAuth.getCurrentUser();

                        TextView email = findViewById(R.id.email_main);


                        if (currentUser != null) {
                            //user is already loggedin..
                            Log.d("cahneg", "onAuthStateChanged: in");
                            Single_one single_one = Single_one.getInstance();
                            single_one = Single_one.getInstance();
                            email_now = currentUser.getEmail().toString().trim();
                            single_one.setNow_login_email(email_now);
                            //email.setText(email_now);

                        } else {
                            //no user yet...
                            Log.d("change", "onAuthStateChanged: exit");
                            Single_one single_one = Single_one.getInstance();
                            single_one = Single_one.getInstance();
                            email_now = "none";
                            image_now = "none";
                            single_one.setUserImage(image_now);
                            single_one.setNow_login_email(email_now);
                        }

                    }
                };

            }


            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Log.d("meee", "onNavigationItemSelected: " + item.getTitle());
                return true;
            }

//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                Log.d("eeee", "onNavigationItemSelected: click on drawer item1");
//
//
//                int id = item.getItemId();
//                Log.d("eeee", "onNavigationItemSelected: click on drawer item");

//                single_one = Single_one.getInstance();
//
//                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
//
//                switch (item.getItemId()) {
//
//                    // .addToBackStack(null)
//
//                    case R.id.nav_home:
//                        the_now_open_drawer = "home";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//                        HomeFragment nextFrag_nav_home = new HomeFragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_nav_home, "findThisFragment")
//                                .commit();
//                        msg("nav_home");
//                        Log.d("asdf", "onNavigationItemSelected: home");
//
//                        break;
//
//
//                    case R.id.nav_slideshow:
//                        the_now_open_drawer = "slideshow";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//
//                        SlideshowFragment nextFrag_nav_slideshow = new SlideshowFragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_nav_slideshow, "findThisFragment")
//                                .commit();
//                        msg("nav_slideshow");
//                        Log.d("asdf", "onNavigationItemSelected: nav_slideshow");
//
//                        break;
//
//                    case R.id.nav_search:
//                        the_now_open_drawer = "search";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//
//                        SearchFragment nextFrag_nav_search = new SearchFragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_nav_search, "findThisFragment")
//                                .commit();
//                        msg("nav_search");
//                        Log.d("asdf", "onNavigationItemSelected: nav_search");
//
//                        break;
//
//
//                    case R.id.nav_watchlist:
//
//                        MainActivity.this.setTitle("Team B");
//                        the_now_open_drawer = "watchlist";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//
//                        GalleryFragment nextFrag_nav_watchlist = new GalleryFragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_nav_watchlist, "findThisFragment")
//                                .commit();
//                        msg("nav_watchlist");
//                        Log.d("asdf", "onNavigationItemSelected: nav_watchlist");
//
//                        break;
//
//
//                    case R.id.nav_friends:
//                        // add Friend
//                        the_now_open_drawer = "friends";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//
//                        List_Friends_Fragment nextFrag_friend_list = new List_Friends_Fragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_friend_list, "findThisFragment")
//                                .commit();
//                        msg("nav_friends");
//                        Log.d("asdf", "onNavigationItemSelected: nav_friends");
//
//                        break;
//
//                    case R.id.nav_add_friends:
//                        // add Friend
//                        the_now_open_drawer = "add_friends";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//
//                        Add_Friends_Fragment nextFrag_add_friends = new Add_Friends_Fragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_add_friends, "findThisFragment")
//                                .commit();
//                        msg("nav_add_friends");
//                        Log.d("asdf", "onNavigationItemSelected: nav_add_friends");
//
//
//                        break;
//
//                    case R.id.nav_setting:
//                        the_now_open_drawer = "setting";
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//                        SettingFragment nextFrag_setting = new SettingFragment();
//                        MainActivity.this.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.nav_host_fragment_content_main, nextFrag_setting, "findThisFragment")
//                                .commit();
//                        msg("nav_setting");
//                        Log.d("asdf", "onNavigationItemSelected: nav_setting");
//
//
//                        break;
//
//
//                    case R.id.nav_logout:
//                        the_now_open_drawer = "home";
//
//                        Log.d("asdf", "onNavigationItemSelected: home");
//
//                        single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//
//                        // logout
//                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
//                        FirebaseAuth auth = FirebaseAuth.getInstance();
//                        auth.signOut();
//                        Single_one single_one = Single_one.getInstance();
//
//                        // adding a simple move to the start
//                        The_movies one_none_move = new The_movies();
//                        one_none_move.setTitle("Shaun the Sheep Movie");
//                        one_none_move.setOriginal_language("en");
//                        one_none_move.setRelease_date("2015-02-05");
//                        one_none_move.setVote_average("7");
//                        one_none_move.setImage("/dhVYlfMNc2bfXPB83LLL00I4l9n.jpg");
//                        one_none_move.setImage_sec("/1eJLkZWuFVKr6OnNkMyqgoqkU1E.jpg");
//
//
//                        ArrayList<The_movies> s = new ArrayList<>();
//                        s.add(one_none_move);
//                        single_one.setThe_love_movies(s);
//                        single_one.setNow_login_email("none");
//                        msg("now your are out");
//
//
//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//
//                    default:
//                        Log.d("asdf", "onNavigationItemSelected: dont work");
////                        Intent intent_to_main = new Intent(MainActivity.this,MainActivity.class);
////                        startActivity(intent_to_main);
//
//                }
//                Log.d("nowdrawer1", "onBackPressed: " + the_now_open_drawer);
//
//                //This is for maintaining the behavior of the Navigation view
//
//
//                //NavigationUI.onNavDestinationSelected(menuItem,navController); // todo ? we need this ? its just if i click is going me throw the layout but not the friend list fragment , why? idk
//
//
//                //This is for closing the drawer after acting on it
//                drawer.closeDrawer(GravityCompat.START);
//                return true;
//
//
//            }


            private static int RESULT_LOAD_IMG = 1;
            ImageView imagemain;

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.main, menu);

                Single_one single_one = Single_one.getInstance();


                imageView_main_back = findViewById(R.id.background_main_imageview);


                imageView_main_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        if (auth.getCurrentUser() != null) {

                            Log.d("backclick", "onClick: now ");
                            // start the background activity
                            Intent intent = new Intent(MainActivity.this, Pic_image_background.class);
                            startActivity(intent);
                            finish();
                        } else {
                            msg("you dont (no user) 2");
                        }
                    }
                });
                //username.setText("hi");

                email_now = single_one.getNow_login_email();
                image_now = single_one.getUserImage(); // the image in none

                image_user = findViewById(R.id.imageView_main);
                if (image_now.equals("none") || image_now.equals("")) {
                    image_now = "smile_1.png";

                    final Single_one single_one1 = Single_one.getInstance();

                    collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Single_one single_one = Single_one.getInstance();

                            for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                                ToData shopping = snapshots.toObject(ToData.class);
                                if (shopping != null) {
                                    if (shopping.getEmail().equals(email_now)) {
                                        the_user_Image = shopping.getBitmap();
                                        image_now = shopping.getBitmap();
                                        now_image_background = shopping.getImage_background();

                                        // set the background image to null if he not find a image for background
                                        if (now_image_background == null) {
                                            now_image_background = "none";
                                        } else {
                                            Glide.with(getApplicationContext())
                                                    .load(now_image_background)
                                                    .fitCenter()
                                                    .into(imageView_main_back);

                                            imageView_main_back.setScaleType(ImageView.ScaleType.FIT_XY);
                                        }

                                        Log.d("imageuser", "onCreateOptionsMenu: no image get " + image_now + "image back P:  " + now_image_background);
                                        single_one.setUserImage(image_now);
                                        break;
                                    }
                                }
                            }
                        }
                    });
                    Log.d("imageuser", "onCreateOptionsMenu: no image");
                }

                TextView email = findViewById(R.id.email_main);
                if (email_now == "none") {
                    email_now = no_user_string_main;
                }
                email.setText(email_now);

                // see if the image is null todo here =====================
//        if (!the_user_Image.equals("none")){
//            single_one.setUserImage(the_user_Image);
//            image_now = the_user_Image;

                //}


                Log.d("imageuser", "onCreateOptionsMenu: image user1 : " + image_now);
                // todo just enter the image id to this :  image_user.setImageResource(R.drawable.background1) by number ==========;


                switch (image_now) {
                    case "smile_0.png":
                        image_user.setImageResource(R.drawable.smile_0);
                        break;
                    case "smile_1.png":
                        image_user.setImageResource(R.drawable.smile_1);
                        break;
                    case "smile_2.png":
                        image_user.setImageResource(R.drawable.smile_2);
                        break;
                    case "smile_3.png":
                        image_user.setImageResource(R.drawable.smile_3);
                        break;
                    case "smile_4.png":
                        image_user.setImageResource(R.drawable.smile_4);
                        break;
                    case "smile_5.png":
                        image_user.setImageResource(R.drawable.smile_5);
                        break;
                    case "none":
                        image_user.setImageResource(R.drawable.smile_7);
                        break;
                }


                imagemain = findViewById(R.id.imageView_main);


                // if email != null
                image_user.setVisibility(View.VISIBLE);
                // no user in the system
                image_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        if (auth.getCurrentUser() != null) {
                            Log.d("cantgothere", "onCreateOptionsMenu: " + auth.getCurrentUser());
                            Intent intent = new Intent(MainActivity.this, Pic_Image_Activity.class);
                            intent.putExtra(EXTRAEMAIL, email_now);
                            startActivity(intent);
                            finish();
                        } else {
                            msg("you dont (no user)");
                        }

//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
//                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

                    }
                });


                // set image user
                single_one = Single_one.getInstance();
//        DocumentReference documentReference = db.collection("good").document(email_now);
//
//        documentReference.update("bitmap",image_now)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        msg("image set");
//                    }
//                });

                // set the image of the background :

                if (now_image_background.equals("none")) {
                    now_image_background = "s";
                }
//        Glide.with(getApplicationContext())
//                .load(now_image_background)
//                .fitCenter()
//                .into(imageView_main_back);
//
//        imageView_main_back.setScaleType(ImageView.ScaleType.FIT_XY);


                Log.d("mainimage", "onCreateOptionsMenu: " + now_image_background);

                // todo to countoinue the image main in the background of the main screen


                return true;
            }


//  @Override
//    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//
//
//        if (resultCode == RESULT_OK) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//
//                // todo user image save
//                // set in single the user image
//                Single_one single_one = Single_one.getInstance();
//                single_one.setUserImage(selectedImage);
//
//
//                Log.d("image1", "onActivityResult: " + selectedImage);
//                DocumentReference documentReference = db.collection("good").document(email_now);
//
//
//
//                imagemain.setImageBitmap(selectedImage);
//
//                msg("image set!");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//
//        }else {
//            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
//        }
//    }

            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_about:
                        Log.d("drawery", "onOptionsItemSelected: ");

                        msg("is action_about");
                        Intent intent = new Intent(this, About.class);
                        startActivity(intent);

                        break;

                    case R.id.action_Login:
                        msg("is action_Login");
                        Log.d("drawery", "onOptionsItemSelected: ");

                        the_now_open_drawer = "setting";

                        SettingFragment nextFrag_setting = new SettingFragment();
                        MainActivity.this.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment_content_main, nextFrag_setting, "findThisFragment")
                                .commit();
                        msg("nav_setting");


                        break;

                    case R.id.action_Logout:

                        FirebaseAuth auth = FirebaseAuth.getInstance();
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

                        // THE LOVE MOVE ARRAY LIST  = s
                        ArrayList<The_movies> s = new ArrayList<>();
                        s.add(one_none_move);
                        single_one.setThe_love_movies(s);
                        single_one.setNow_login_email("none");
                        msg("now your are out");


                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;


                }


                return super.onOptionsItemSelected(item);
            }

            @Override
            public boolean onSupportNavigateUp() {
                TextView email = findViewById(R.id.email_main);


                // live when data change
                Single_one single_one = Single_one.getInstance();
                email.setText(email_now);

                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

                return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                        || super.onSupportNavigateUp();
            }


            @Override
            protected void onStart() {
                Single_one single_one = Single_one.getInstance();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                single_one.setThe_now_open_drawer("home");

                if (auth.getCurrentUser() != null) {
                    String email = auth.getCurrentUser().getEmail().toString().trim();
                    Log.d("username", "onStart: email:  " + email);
                    msg("welcome back  ");
                    single_one = Single_one.getInstance();
                    single_one.setNow_login_email(email);

                    The_movies the_movies = new The_movies();


//            db.collection("good")
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d("popo", document.getId() + " => " + document.getData());
//                                    Log.d("dec", "onComplete: "+document.getId());
//                                    ArrayList<The_movies> z = new ArrayList<>();
//                                    z = (ArrayList) document.getData().getOrDefault("the_moviesArrayList","true");
//                                    Log.d("dec1", "onComplete: "+z.get(0));
//                                    The_movies the_movies1 = new The_movies();
//                                    The_movies xd = new The_movies();
//                                    xd = z.get(0);
//
//                                }
//                            } else {
//                                Log.d("popo", "Error getting documents: ", task.getException());
//                            }
//                        }
//                    });


                    // get the love movies
                    collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                                Log.d("sec", "onSuccess: " + snapshots.getId());

                                ToData shopping = snapshots.toObject(ToData.class);
                                Log.d("snal2", "onSuccess: " + email);

                                if (shopping != null) {


                                    if (shopping.getEmail().equals(email)) {
                                        Log.d("snal", "onSuccess: " + snapshots.toString());
                                        Log.d("sec", "onSuccess: target on : " + snapshots.getId());

                                        Log.d("sec2", "onSuccess: target on : " + shopping.getEmail());

                                        the_user_Image = shopping.getBitmap();

                                        Log.d("getimage2", "onSuccess: " + the_user_Image);


                                        Single_one single_one = Single_one.getInstance();
                                        single_one.setFriend_list(shopping.getFriends());

                                        now_image_background = shopping.getImage_background();
                                        if (now_image_background == null || now_image_background.equals("none")) {
                                            //msg("email = none");

                                        } else {
                                            Glide.with(getApplicationContext())
                                                    .load(now_image_background)
                                                    .fitCenter()
                                                    .into(imageView_main_back);

                                            imageView_main_back.setScaleType(ImageView.ScaleType.FIT_XY);
                                        }


                                        image_now = shopping.getBitmap();
                                        Log.d("1start", "onSuccess: start " + image_now);
                                        switch (image_now) {
                                            case "smile_0.png":
                                                image_user.setImageResource(R.drawable.smile_0);
                                                break;
                                            case "smile_1.png":
                                                image_user.setImageResource(R.drawable.smile_1);
                                                break;
                                            case "smile_2.png":
                                                image_user.setImageResource(R.drawable.smile_2);
                                                break;
                                            case "smile_3.png":
                                                image_user.setImageResource(R.drawable.smile_3);
                                                break;
                                            case "smile_4.png":
                                                image_user.setImageResource(R.drawable.smile_4);
                                                break;
                                            case "smile_5.png":
                                                image_user.setImageResource(R.drawable.smile_5);
                                                break;
                                            case "none":
                                                image_user.setImageResource(R.drawable.smile_0);
                                                break;
                                        }


                                        single_one.setThe_love_movies(shopping.getThe_moviesArrayList());
                                        Log.d("getarray", "onSuccess: " + shopping.getThe_moviesArrayList().toString());

                                        Log.d("data", "onSuccess: " + shopping.getThe_moviesArrayList().toString());
                                    }
                                }

                            }
                        }


                    });


                } else {
                    single_one.setNow_login_email("none");
                }
                auth.addAuthStateListener(authStateListener);

                //navigationView.setNavigationItemSelectedListener(this);


                super.onStart();


            }
            // progressDialog

            public void progressDialog() {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.show();
                // set Content view
                progressDialog.setContentView(R.layout.progress_dialog);
                // set Transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
            }


            public void msg(String text) {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();
            }


            @Override
            public void onBackPressed() {

                Single_one single_one = Single_one.getInstance();

                Log.d("wwww", "onBackPressed:  now in " + single_one.getThe_now_open_drawer());

                if (single_one.getThe_now_open_drawer().equals("overview")) {


                    getSupportFragmentManager().beginTransaction().remove(single_one.getFragment()).commit();


                    single_one.setThe_now_open_drawer("home");
                } else {


                    new AlertDialog.Builder(this)
                            .setIcon(R.drawable.ic_baseline_priority_high_24)
                            .setTitle("EXIT")
                            .setMessage("Are you sure you want to EXIT ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }


//            super.onBackPressed();


//        if (is_back_press == true){
//
//        }else {
//            is_back_press = true;
//        }
//
//        navigationView.setNavigationItemSelectedListener(this);
//
//
//
//
//        single_one = Single_one.getInstance();
//        the_now_open_drawer = single_one.getThe_now_open_drawer();
//        Log.d("nowdrawer2", "onBackPressed: " + the_now_open_drawer);
//
//        if (the_now_open_drawer.equals("home")){
//
//            finish();
//
//        } else if (the_now_open_drawer.equals("singup")){
//            the_now_open_drawer = "setting";
//            single_one.setThe_now_open_drawer(the_now_open_drawer);
//            // if we are in sing up page
//            super.onBackPressed();
//
//
//        }
//        else if (the_now_open_drawer.equals("overview")) {
//            //Fragment_the_movie_overview myFragment = new Fragment_the_movie_overview();
//
//            the_now_open_drawer = "home";
//            single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//            super.onBackPressed();
//            Log.d("asdf", "onBackPressed: dont count");
//
//        }
//
//        else {
//            Log.d("need_start", "onBackPressed: need start activity? ");
//
//            the_now_open_drawer = "home";
//            single_one.setThe_now_open_drawer(the_now_open_drawer);
//
//            HomeFragment nextFrag_nav_home = new HomeFragment();
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.nav_host_fragment_content_main, nextFrag_nav_home, "findThisFragment")
//                    .commit();
//
//
////            Intent intent = new Intent(this,MainActivity.class);
////            startActivity(intent);
////            finish();
//        }
//
//
//
//        // dsmiss progress dialog
//        //progressDialog.dismiss();
//        Log.d("wwww2", "onBackPressed: we are in " + the_now_open_drawer );
//
//        msg("no");
////        Intent intent = new Intent(this,MainActivity.class);
////        startActivity(intent);
//

            }
        }
