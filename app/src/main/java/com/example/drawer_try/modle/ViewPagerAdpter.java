package com.example.drawer_try.modle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.drawer_try.R;
import com.example.drawer_try.singletonClass.Single_one;

import java.util.ArrayList;

public class ViewPagerAdpter extends PagerAdapter implements View.OnClickListener {

    private Context context;
    private ArrayList<String> imagesURL = new ArrayList<>();
    Single_one single_one = Single_one.getInstance();

    private ArrayList<The_movies> movieList = new ArrayList<>();



    public ViewPagerAdpter(Context context, ArrayList<The_movies> movieList) {
        this.context = context;
        this.movieList = movieList;


        Log.d("firstone", "ViewPagerAdpter: "+ movieList.get(0));

        //single_one.setThe_now_open_drawer("overview");


        //shuffle Array list
//        Collections.shuffle(movieList);


        for (int i = 0; i < movieList.size(); i++) {
            Log.d("URLpager", "onPageSelected: " + movieList.get(i).getImage());
            String the_good_side ="https://image.tmdb.org/t/p/w500" + movieList.get(i).getImage();
            imagesURL.add(the_good_side);
        }
        this.imagesURL = imagesURL;
    }

    @Override
    public int getCount() {
        return imagesURL.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull  Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("3333", "onClick: click me");
                Single_one single_one = Single_one.getInstance();
                ArrayList<The_movies> theMoviesArrayList = single_one.getMovies_list();
                msg("click on " + theMoviesArrayList.get(position).getTitle());
                single_one.setValue_movie(theMoviesArrayList.get(position));

                Log.d("URLhome", "onResponse: " + theMoviesArrayList.get(position).getId());
                single_one.setThe_same_movie_id(theMoviesArrayList.get(position).getId());

                AppCompatActivity activity = (AppCompatActivity) v.getContext();


                Fragment_the_movie_overview nextFrag= new Fragment_the_movie_overview();
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.mail_countener9, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();




            }
        });


        Glide.with(context)
                .load(imagesURL.get(position))
                .fitCenter()
                .into(imageView);

//        Picasso.get()
//                .load(imagesURL.get(position))
//                .fit()
//                .centerInside()
//                .into(imageView);
//
//                .centerCrop()

        container.addView(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    private void msg(String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG)
                .show();
    }



    @Override
    public void onClick(View v) {
        Log.d("pager", "onClick: click pager");
    }





}
