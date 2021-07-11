package com.example.drawer_try.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.drawer_try.R;
import com.example.drawer_try.databinding.FragmentHomeBinding;
import com.example.drawer_try.modle.FlowerAdapter;
import com.example.drawer_try.modle.Fragment_the_movie_overview;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.modle.ViewPagerAdpter;
import com.example.drawer_try.singletonClass.Single_one;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.util.Collections.shuffle;

public class HomeFragment extends Fragment {


    // progressDialog
    ProgressDialog progressDialog;
    ViewPagerAdpter adpter_pager;
    FrameLayout the_frag_of_overview;

    ArrayList<The_movies> the_moviesArrayList_adpter = new ArrayList<>();




    // fireStore
    private FirebaseFirestore firebaseFirestore;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");

    private DocumentReference shopping_data_add = db.collection("good").document();

    //home

    private HomeViewModel homeViewModel;
    //FragmentHomeBinding
    private FragmentHomeBinding binding;


    // layout
    private ImageView imagemovies;

    String movie_id;

    ViewPager pager_images_movies;



    public ArrayList<String> imagesURLS = new ArrayList<>();

    ArrayList<String> the_type = new ArrayList<>();


    public ArrayList<The_movies> theMoviesArrayList = new ArrayList<>();

    Single_one single_one;
    String the_data = "popular";

    Button the_next_pages;


    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed
    //String JsonURL = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Object.JSON";


    ArrayList <String> the_values_array =  new ArrayList<>();
    int currntPage = 1;
    int the_limit_page;




    String JsonURL = "https://api.themoviedb.org/3/movie/popular?api_key=2029d84f820b9dc29ab83773c31b4320&page=1";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        the_next_pages = root.findViewById(R.id.add_page);



        the_next_pages.setVisibility(View.VISIBLE);
        the_next_pages.setText("PAGE 1");

        the_next_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = Volley.newRequestQueue(getContext());
                if (currntPage < the_limit_page) {

                    if (currntPage != 0){
                        the_next_pages.setText("PAGE " + (currntPage-1));
                    }
                    currntPage++;
                    JsonURL = JsonURL = "https://api.themoviedb.org/3/movie/" + the_data + "?api_key=2029d84f820b9dc29ab83773c31b4320&page=" + currntPage;
                    Log.d("current", "onClick: " + currntPage);
                    // todo view pager dispersing
                    //pager_images_movies.setVisibility(View.GONE);
                    get_movies();
                }else {
                    // you got to the limit of the pages
                    msg("no more pages");
                }

            }
        });
        return root;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog();



//            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();


        //imagemovies = findViewById(R.id.imageView);

        // set the URL

        //the_values_array.add("latest");
        //the_values_array.add("popular");
        the_values_array.add("top_rated");
        the_values_array.add("upcoming");

        int index = (int) (Math.random() * the_values_array.size());
        the_data = the_values_array.get(index);
        Log.d("tepy", "onCreate: data " + the_data);
        JsonURL = "https://api.themoviedb.org/3/movie/" + the_data + "?api_key=2029d84f820b9dc29ab83773c31b4320";


        firebaseFirestore = FirebaseFirestore.getInstance();

        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(getContext());
        Log.d("current", "onClick: "+currntPage);

        get_movies();

        // Casts results into the TextView found within the main layout XML with id jsonData
        //results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.

    }
    private void get_movies() {

            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL, null,
                    // The third parameter Listener overrides the method onResponse() and passes
                    //JSONObject as a parameter
                    new Response.Listener<JSONObject>() {

                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                the_limit_page = response.getInt("total_pages");


                                Log.d("pages", "onResponse: " + the_limit_page);
                                The_movies one_movie = new The_movies();
                                JSONObject jsonObject = new JSONObject();
                                JSONArray jsonArray = response.getJSONArray("results");


                                // use jsonArray.length to get all
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    one_movie = new The_movies();


                                    jsonObject = jsonArray.getJSONObject(i);

                                    // Retrieves the string labeled "colorName" and "description" from
                                    //the response JSON Object
                                    //and converts them into javascript objects

                                    // get values from URL
                                    boolean adult = jsonObject.getBoolean("adult");
                                    String title = jsonObject.getString("title");
                                    String average = jsonObject.getString("vote_average");
                                    String release = jsonObject.getString("release_date");
                                    String overview = jsonObject.getString("overview");
                                    String original_language = jsonObject.getString("original_language");
                                    int vote_count = jsonObject.getInt("vote_count");
                                    String image = jsonObject.getString("poster_path");
                                    String image_sec = jsonObject.getString("backdrop_path");
                                    movie_id = String.valueOf(jsonObject.getInt("id"));


                                    // put values in class movies
                                    one_movie.setAdult(adult);
                                    one_movie.setTitle(title);
                                    one_movie.setVote_average(average);
                                    one_movie.setRelease_date(release);
                                    one_movie.setOverview(overview);
                                    one_movie.setOriginal_language(original_language);
                                    one_movie.setVote_count(String.valueOf(vote_count));
                                    one_movie.setImage(image);
                                    one_movie.setImage_sec(image_sec);
                                    one_movie.setId(movie_id);



                                    // after all in movies :

                                    theMoviesArrayList.add(one_movie);
                                    String image_full = "https://image.tmdb.org/t/p/w500" + image;

                                    //imagesURLS.add(image_full);


                                    // Adds strings from object to the "data" string
                                    data = "title Name: " + title +
                                            "average : " + average +
                                            " image : " + image;

                                    // Adds the data string to the TextView "results"
                                    Log.d("TAG", "onResponse3: " + data);
                                    Log.d("TAG", "onResponse3: \n");

                                    //results.setText(data);

                                }
                                single_one = Single_one.getInstance();
                                shuffle(theMoviesArrayList);
                                Log.d("size", "onResponse: "+ theMoviesArrayList.size());

                                for (int i = 0; i < theMoviesArrayList.size(); i++) {
                                    String image_full = "https://image.tmdb.org/t/p/w500" + theMoviesArrayList.get(i).getImage();

                                    imagesURLS.add(image_full);

                                }

                                single_one.setMovies_list(theMoviesArrayList);


                                // give the id

                                next_level();


                                Log.d("size", "onResponse: " + theMoviesArrayList.size());

                            }
                            // Try and catch are included to handle any errors due to JSON
                            catch (JSONException e) {
                                // If an error occurs, this prints the error to the log
                                e.printStackTrace();
                            }
                        }
                    },
                    // The final parameter overrides the method onErrorResponse() and passes VolleyError
                    //as a parameter
                    new Response.ErrorListener() {
                        @Override
                        // Handles errors that occur due to Volley
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley", "Error");
                        }
                    }
            );
            // Adds the JSON object request "obreq" to the request queue
            requestQueue.add(obreq);
            //https://image.tmdb.org/t/p/w500/pKAxHs04yxLDQSIf4MNiZoePVWX.jpg
//pKAxHs04yxLDQSIf4MNiZoePVWX.jpg
//        //https://image.tmdb.org/t/p/w500/
//        Glide.with(getApplicationContext())
//                .load("https://image.tmdb.org/t/p/w500/pKAxHs04yxLDQSIf4MNiZoePVWX.jpg")
//                .into(imagemovies);

        }


    private void next_level(){




        // viewpager
        // text of image
        TextView name_of_image = getView().findViewById(R.id.name_of_movie_main);
        Log.d("xandy", "next_level: " + name_of_image.getTop());
        ViewPager pager_images_movies = getView().findViewById(R.id.viewpager);
        Log.d("pagecountnow", "next_level: out " + currntPage);

        ConstraintLayout pager_mail = getView().findViewById(R.id.constraintLayout2_pager);



        if (currntPage <= 3 ) {

            Log.d("is3n", "onClick: click pager " + currntPage);


            Log.d("pagecountnow", "next_level: more 3  " + currntPage);

            pager_images_movies.setVisibility(View.VISIBLE);

            the_moviesArrayList_adpter = theMoviesArrayList;


            adpter_pager = new ViewPagerAdpter(getContext(), the_moviesArrayList_adpter);



            pager_images_movies.setAdapter(adpter_pager);

            Log.d("222", "next_level: " + pager_images_movies.getCurrentItem());


            // todo add a name by the side of the image insted of the click listener ==============================

            name_of_image.setText(the_moviesArrayList_adpter.get(0).getTitle());

            pager_images_movies.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.d("222", "next_level: " + pager_images_movies.getCurrentItem());

                }

                @Override
                public void onPageSelected(int position) {
                    //best
                    Log.d("333", "next_level: " + pager_images_movies.getCurrentItem());
                    name_of_image.setText(the_moviesArrayList_adpter.get(position).getTitle());


                    String the_URl =  imagesURLS.get(pager_images_movies.getCurrentItem());
                    String[] try_me = the_URl.split("500");
                    Log.d("qqq", "onPageSelected: " + try_me[0]+ " \n and the 1 is : " + try_me[1] );
                    String the_good_side = try_me[1];

                    for (int i = 0; i < the_moviesArrayList_adpter.size(); i++) {
                        if (the_moviesArrayList_adpter.get(i).getImage() == the_good_side){
                            msg("we found the value " + the_moviesArrayList_adpter.get(i).getTitle());
                            break;
                        }
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.d("444", "next_level: " + pager_images_movies.getCurrentItem());

                }
            });
            pager_images_movies.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("3333", "onClick: click pager " + currntPage);
                }
            });
        }else if (currntPage > 3){
                Log.d("is3", "onClick: click pager " + currntPage);

            pager_images_movies.setVisibility(View.GONE);
            name_of_image.setVisibility(View.GONE);
            pager_mail.setVisibility(View.GONE);


        }

        // gridView

        FlowerAdapter adapter;

        GridView gridView = getView().findViewById(R.id.gridview);

        adapter = new FlowerAdapter(getContext(),theMoviesArrayList);
        gridView.setAdapter(adapter);


        gridView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.d("croll", "onScrollChange: " + oldScrollX + " y : " + oldScrollY);
                if (scrollY > 30){
                    pager_images_movies.setVisibility(View.GONE);
                }else {
                    pager_images_movies.setVisibility(View.VISIBLE);

                }
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msg("click on1 " + theMoviesArrayList.get(position).getTitle());
                single_one.setValue_movie(theMoviesArrayList.get(position));

                Log.d("URLhome", "onResponse: " + theMoviesArrayList.get(position).getId());
                single_one.setThe_same_movie_id(theMoviesArrayList.get(position).getId());


                // gone the button of the next page
                //the_next_pages.setVisibility(View.GONE);
                // gone the button of the next page

                // todo set gone in fragment and return its back

                //name_of_image.setVisibility(View.GONE);


                the_frag_of_overview = getView().findViewById(R.id.mail_countener9);
                the_frag_of_overview.setVisibility(View.VISIBLE);

                Single_one single_one = Single_one.getInstance();
                single_one.setThe_now_open_drawer("overview");

                Fragment_the_movie_overview nextFrag= new Fragment_the_movie_overview();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.mail_countener9, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();





//                                        .addToBackStack(null)


//                The_movies none_movie = new The_movies();
//                none_movie.setTitle("");
//
//                single_one.setValue_movie(none_movie);




//                gridView.setVisibility(View.GONE);
//                pager_images_movies.setVisibility(View.GONE);

            }
        });
        if (currntPage < 3){
            the_next_pages.callOnClick();
        }
        progressDialog.dismiss();




    }



    private void msg(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG)
                .show();
    }

    public void progressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        // set Content view
        progressDialog.setContentView(R.layout.progress_dialog);
        // set Transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }


        @Override
        public void onDestroyView(){
            super.onDestroyView();
            // dsmiss progress dialog
            progressDialog.dismiss();
            binding = null;
        }
}
