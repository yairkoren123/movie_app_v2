package com.example.drawer_try.ui.FriendList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListFriendsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListFriendsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
