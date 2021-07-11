package com.example.drawer_try.ui.slideshow;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drawer_try.R;
import com.example.drawer_try.databinding.FragmentGalleryBinding;
import com.example.drawer_try.databinding.FragmentSlideshowBinding;
import com.example.drawer_try.modle.Fragment_the_movie_overview;
import com.example.drawer_try.modle.The_movies;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.ui.gallery.GalleryViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlideshowFragment extends Fragment {



    // random

    ArrayList<The_movies> the_moviesArrayList = new ArrayList<>();


    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // get Array from home fragment

        // get singleton
        Single_one single_one = Single_one.getInstance();

        single_one.setThe_now_open_drawer("slideshow");


        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // use  TextView textView = binding.textSlideshow; to get like id


        The_movies the_movie = new The_movies();

        Random rand;
        rand = new Random();
        the_moviesArrayList =  single_one.getMovies_list();
//        if(!the_moviesArrayList.isEmpty()) {
//            int index = (int) (Math.random() * the_moviesArrayList.size());
//            the_movie = the_moviesArrayList.get(index);
//            movie_Random.setText(the_moviesArrayList.get(index).getTitle());
//        }
        Fragment_the_movie_overview nextFrag= new Fragment_the_movie_overview();
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.mail_countener, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // get Rndom from Array list


}
