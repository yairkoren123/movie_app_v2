package com.example.drawer_try.ui.slideshow;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.drawer_try.R;
import com.example.drawer_try.singletonClass.Single_one;

public class SlideshowViewModel extends ViewModel {

    // random

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();

        Single_one single_one = Single_one.getInstance();

        Log.d("mee", "SlideshowViewModel: " + single_one.getMovies_list().get(1).getTitle());
        //mText.setValue("This is slideshow fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }
}