package com.example.drawer_try.ui.Search;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.drawer_try.R;
import com.example.drawer_try.databinding.FragmentGalleryBinding;
import com.example.drawer_try.databinding.SearchFragmentBinding;
import com.example.drawer_try.modle.FlowerAdapter;
import com.example.drawer_try.modle.Fragment_the_movie_overview;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.modle.ViewPagerAdpter;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.ui.gallery.GalleryViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    //search



    private SearchViewModel searchViewModel;
    private SearchFragmentBinding binding;

    private Context context;

    FlowerAdapter adapter;



    public ArrayList<String> imagesURLS = new ArrayList<>();


    public ArrayList<The_movies> theMoviesArrayList = new ArrayList<>();

    Single_one single_one;
    String need_search = "";

    // to search :

    String JsonURL = "https://api.themoviedb.org/3/movie/popular?api_key=2029d84f820b9dc29ab83773c31b4320";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = SearchFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();


        SearchBox nextFrag = new SearchBox();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mail_countener6, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();


//        // Creates the Volley request queue
        EditText text_to_search = getView().findViewById(R.id.edittext_search_s);
        ImageView search_now_imageView = getView().findViewById(R.id.imageView_search_s);

        search_now_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                need_search = text_to_search.getText().toString().trim();
                hideKeyboard(getActivity());

                Log.d("search", "onClick: click searck");
                Single_one single_one = Single_one.getInstance();
                single_one.setToSearch(need_search);

                // start fragment
                SearchBox nextFrag = new SearchBox();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.mail_countener6, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
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


//                text_to_search.setText("");
//                JsonURL = "https://api.themoviedb.org/3/search/movie?query=" + need_search + "&api_key=2029d84f820b9dc29ab83773c31b4320&page=1";
//                lets_do_it();
//            }
//        });
//        if (need_search == "" || need_search.equals("")) {
//            // default value
//            need_search = "Shaun the Sheep";
//        }
////        JsonURL = "https://api.themoviedb.org/3/search/movie?query=" + need_search + "&api_key=2029d84f820b9dc29ab83773c31b4320&page=1";
////        lets_do_it();
//    }
//    private void lets_do_it(){
//
//        requestQueue = Volley.newRequestQueue(getContext());
//
//        // Casts results into the TextView found within the main layout XML with id jsonData
//        //results = (TextView) findViewById(R.id.jsonData);
//
//        // Creating the JsonObjectRequest class called obreq, passing required parameters:
//        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
//        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL, null,
//                // The third parameter Listener overrides the method onResponse() and passes
//                //JSONObject as a parameter
//                new Response.Listener<JSONObject>() {
//
//                    // Takes the response from the JSON request
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            The_movies one_movie = new The_movies();
//                            JSONObject jsonObject = new JSONObject();
//                            JSONArray jsonArray = response.getJSONArray("results");
//
//
//                            // use jsonArray.length to get all
//                            for (int i = 0; i < 20; i++) {
//                                one_movie = new The_movies();
//
//                                jsonObject = jsonArray.getJSONObject(i);
//                                // Retrieves the string labeled "colorName" and "description" from
//                                //the response JSON Object
//                                //and converts them into javascript objects
//
//                                // get values from URL
//                                boolean adult = jsonObject.getBoolean("adult");
//                                String title = jsonObject.getString("title");
//                                String average = jsonObject.getString("vote_average");
//                                String release = jsonObject.getString("release_date");
//                                String overview = jsonObject.getString("overview");
//                                String original_language = jsonObject.getString("original_language");
//                                int vote_count = jsonObject.getInt("vote_count");
//                                String image = jsonObject.getString("poster_path");
//                                String image_sec = jsonObject.getString("backdrop_path");
//
////                                    if (title == null || title == ""){
////                                        title="null";
////                                    }
//
//                                // put values in class movies
//
//
//
//
//                                one_movie.setAdult(adult);
//                                one_movie.setTitle(title);
//                                one_movie.setVote_average(average);
//                                one_movie.setRelease_date(release);
//                                one_movie.setOverview(overview);
//                                one_movie.setOriginal_language(original_language);
//                                one_movie.setVote_count(String.valueOf(vote_count));
//                                one_movie.setImage(image);
//                                one_movie.setImage_sec(image_sec);
//
//
//                                // after all in movies :
//
//                                theMoviesArrayList.add(one_movie);
//                                String image_full = "https://image.tmdb.org/t/p/w500" + image;
//
//                                imagesURLS.add(image_full);
//
//
//                                // Adds strings from object to the "data" string
//                                data = "title Name: " + title +
//                                        "average : " + average +
//                                        " image : " + image;
//
//                                // Adds the data string to the TextView "results"
//                                Log.d("search", "onResponse3: " + data);
//                                Log.d("search", "onResponse3: \n");
//
//                                //results.setText(data);
//
//                            }
//                            single_one = Single_one.getInstance();
//                            single_one.setMovies_list(theMoviesArrayList);
//                            next_level();
//
//
//                            Log.d("search", "onResponse: " + theMoviesArrayList.size());
//
//                        }
//                        // Try and catch are included to handle any errors due to JSON
//                        catch (JSONException e) {
//                            // If an error occurs, this prints the error to the log
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                // The final parameter overrides the method onErrorResponse() and passes VolleyError
//                //as a parameter
//                new Response.ErrorListener() {
//                    @Override
//                    // Handles errors that occur due to Volley
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Volley", "Error");
//                    }
//                }
//        );
//        // Adds the JSON object request "obreq" to the request queue
//        requestQueue.add(obreq);
//        //https://image.tmdb.org/t/p/w500/pKAxHs04yxLDQSIf4MNiZoePVWX.jpg
////pKAxHs04yxLDQSIf4MNiZoePVWX.jpg
////        //https://image.tmdb.org/t/p/w500/
////        Glide.with(getApplicationContext())
////                .load("https://image.tmdb.org/t/p/w500/pKAxHs04yxLDQSIf4MNiZoePVWX.jpg")
////                .into(imagemovies);
//
//    }
//
//
//
//    @Override
//    public void onCreate(@Nullable  Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//
//
//    private void next_level(){
//
//        // viewpager
////        ViewPager pager_images_movies = getView().findViewById(R.id.viewpager);
////        ViewPagerAdpter adpter_pager = new ViewPagerAdpter(getContext(),imagesURLS);
////        pager_images_movies.setAdapter(adpter_pager);
//
//
//
//
//
//        // gridView
//
//
//        GridView gridView = getView().findViewById(R.id.gridview_search_s);
//
//        adapter = new FlowerAdapter(getContext(),theMoviesArrayList);
//        gridView.setAdapter(adapter);
//
//
//
//
//
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                msg("click on " + theMoviesArrayList.get(position).getTitle());
//                single_one.setValue_movie(theMoviesArrayList.get(position));
//
//
//                Fragment_the_movie_overview nextFrag= new Fragment_the_movie_overview();
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .add(R.id.mail_countener3, nextFrag, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();
//
////                gridView.setVisibility(View.GONE);
////                pager_images_movies.setVisibility(View.GONE);
//
//            }
//        });
//
//
//    }
//
//
//    private void msg(String text){
//        Toast.makeText(getContext(),text,Toast.LENGTH_LONG)
//                .show();
//    }