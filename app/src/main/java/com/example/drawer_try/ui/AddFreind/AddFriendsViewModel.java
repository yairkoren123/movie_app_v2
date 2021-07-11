package com.example.drawer_try.ui.AddFreind;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddFriendsViewModel extends ViewModel {

        private MutableLiveData<String> mText;

        public AddFriendsViewModel() {
            mText = new MutableLiveData<>();
        }

        public LiveData<String> getText() {
            return mText;
        }
    }
