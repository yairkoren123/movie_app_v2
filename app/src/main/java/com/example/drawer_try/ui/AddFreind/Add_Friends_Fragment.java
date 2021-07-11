package com.example.drawer_try.ui.AddFreind;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.drawer_try.R;
import com.example.drawer_try.databinding.AddFriendsFragmentBinding;
import com.example.drawer_try.singletonClass.Single_one;
import com.example.drawer_try.singup.ToData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Add_Friends_Fragment extends Fragment {

    // main ADD Friend


    //layout
    private EditText search_account_by_name_et;
    private ImageView search_account_image;


    private AddFriendsViewModel mViewModel;

    private AddFriendsViewModel addFriendsViewModel;
    private AddFriendsFragmentBinding binding;
    private ArrayList<ToData> friendArrayList;

    String the_user_Image = "";
    String text = "";
    boolean search_by_text = false;



    // firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference1 = db.collection("good");



    //firestore / firebase
    private FirebaseAuth auth = FirebaseAuth.getInstance();



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        addFriendsViewModel =
                new ViewModelProvider(this).get(AddFriendsViewModel.class);

        binding = AddFriendsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        // layout
        search_account_by_name_et = view.findViewById(R.id.edittext_search_friends);
        search_account_image = view.findViewById(R.id.imageView_search_friends);
        Single_one single_one = Single_one.getInstance();

        ConstraintLayout private_s = view.findViewById(R.id.private_find_friend);
        ConstraintLayout content = view.findViewById(R.id.contant_friend_add);


        if (auth.getCurrentUser() == null || single_one.getNow_login_email() == "none") {
            // no user
            private_s.setVisibility(View.VISIBLE);

            content.setVisibility(View.GONE);
            Log.d("vis", "onViewCreated: ");
        }else {
            private_s.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }

        search_account_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = search_account_by_name_et.getText().toString().trim();

                    if (text.contains("@")){
                        friendArrayList = new ArrayList<>();

                        Log.d("6cou", "onClick: text have '@' text : " + text);
                        search_by_text = true;



                    }
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    call_frag();
                    // low keyboard
                    hideKeyboard(getActivity());
                
                }

            }

        });


        friendArrayList = new ArrayList<>();


        // GET ALL THE USERS / or     - > by text by starting fragment
        call_frag();


    }
    public void call_frag(){
        Search_Account_Fragment nextFrag = new Search_Account_Fragment(text);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.mail_countener7, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddFriendsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void msg(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG)
                .show();
    }
}

