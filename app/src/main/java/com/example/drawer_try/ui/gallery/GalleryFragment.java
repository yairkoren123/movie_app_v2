package com.example.drawer_try.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.drawer_try.R;
import com.example.drawer_try.databinding.FragmentGalleryBinding;
import com.example.drawer_try.modle.FlowerAdapter;
import com.example.drawer_try.modle.Fragment_the_movie_overview;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singletonClass.Single_one;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    //watchlist

    private GalleryViewModel galleryViewModel;
    //FragmentGalleryBinding
    private FragmentGalleryBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference1 = db.collection("good");


    public GalleryFragment() {
    }

    private Context context;

    FlowerAdapter adapter;

    private ArrayList<The_movies> the_moviesArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();


        Single_one single_one = Single_one.getInstance();


        the_moviesArrayList = single_one.getThe_love_movies();


        LinearLayout no_account = getView().findViewById(R.id.no_account_linar_image_gal);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        no_account.setVisibility(View.GONE);

        LinearLayout no_movie_in_account = getView().findViewById(R.id.no_movie_in_account);
        no_movie_in_account.setVisibility(View.GONE);


        FrameLayout frameLayout = getView().findViewById(R.id.mail_countener5);
        frameLayout.setVisibility(View.VISIBLE);

        GridView gridView = getView().findViewById(R.id.gridview_love_list);
        gridView.setVisibility(View.VISIBLE);


        if (auth.getCurrentUser() == null || single_one.getNow_login_email() == "none") {
            no_account.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            Log.d("vis", "onViewCreated: ");
        } else {
            msg("you are login already");
            // adpter

            Log.d("tostring", "onViewCreated: " + the_moviesArrayList.toString());

            if (the_moviesArrayList.size() == 0){
                Log.d("loves_movie", "onViewCreated: " +the_moviesArrayList.size());
                no_movie_in_account.setVisibility(View.VISIBLE);
            }

            if (the_moviesArrayList != null) {
//                if (the_moviesArrayList.get(0).getTitle().equals("none") || the_moviesArrayList.get(0).getTitle().equals("here the name of the move")) {
//                    Log.d("tostring 1", "onViewCreated: " + "none");
//                    the_moviesArrayList.get(0).setTitle("here the name of the move");


//
//                }

                adapter = new FlowerAdapter(getContext(), the_moviesArrayList);
                gridView.setAdapter(adapter);


                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        msg("click on " + the_moviesArrayList.get(position).getTitle());
                        single_one.setValue_movie(the_moviesArrayList.get(position));


                        Fragment_the_movie_overview nextFrag = new Fragment_the_movie_overview();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mail_countener5, nextFrag, "findThisFragment")
                                .addToBackStack(null)
                                .commit();

                        //                                .addToBackStack(null)

//                gridView.setVisibility(View.GONE);
//                pager_images_movies.setVisibility(View.GONE);

                    }
                });


            } else {
                Log.d("aaaa", "onViewCreated: ");
                msg("array  = null");
            }
        }
    }

    // on menu


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        Log.d("xdd", "onOptionsItemSelected: " + item);
        return super.onOptionsItemSelected(item);
    }

    private void msg(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG)
                .show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}