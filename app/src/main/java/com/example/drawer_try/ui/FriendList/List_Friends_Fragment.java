package com.example.drawer_try.ui.FriendList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drawer_try.R;
import com.example.drawer_try.databinding.AddFriendsFragmentBinding;
import com.example.drawer_try.databinding.ListFriendsFragmentBinding;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.ui.AddFreind.AddFriendsViewModel;
import com.example.drawer_try.ui.AddFreind.Search_Account_Fragment;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class List_Friends_Fragment extends Fragment {


    // layout

    LinearLayout no_friend ;
    LinearLayout private_find_friend ;



    private ListFriendsViewModel mViewModel;


    private ListFriendsViewModel list_friends_fragment;
    private ListFriendsFragmentBinding binding;

    // firebase
    private FirebaseAuth auth = FirebaseAuth.getInstance();



    // text to sent :

    public String text = "ABCDEFG@";

    public String getText() {
        return text;
    }

    public static List_Friends_Fragment newInstance() {
        return new List_Friends_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        list_friends_fragment =
                new ViewModelProvider(this).get(ListFriendsViewModel.class);

        binding = ListFriendsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // here the code
        auth = FirebaseAuth.getInstance();
        Single_one single_one =  Single_one.getInstance();

        private_find_friend = view.findViewById(R.id.private_find_friend);
        no_friend = view.findViewById(R.id.no_friend_in_account_lin);


        if (auth.getCurrentUser() == null || single_one.getNow_login_email() == "none") {
            // no user
            private_find_friend.setVisibility(View.VISIBLE);

//            content.setVisibility(View.GONE);
            Log.d("vis", "onViewCreated: ");
        }else {
            private_find_friend.setVisibility(View.GONE);
//            content.setVisibility(View.VISIBLE);
        }
        single_one = Single_one.getInstance();
        if (single_one.getFriend_list().size() == 0){
            no_friend.setVisibility(View.VISIBLE);
        }
        callfrag();


    }

    private void callfrag(){
        Search_Account_Fragment nextFrag = new Search_Account_Fragment(text);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mail_countener8, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
