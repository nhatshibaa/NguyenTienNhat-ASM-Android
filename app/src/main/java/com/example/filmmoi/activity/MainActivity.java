package com.example.filmmoi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import com.example.filmmoi.R;
import com.example.filmmoi.adapter.MovieAdapter;
import com.example.filmmoi.adapter.SectionAdapter;
import com.example.filmmoi.entity.Content;
import com.example.filmmoi.entity.Item;
import com.example.filmmoi.entity.Movie;
import com.example.filmmoi.entity.Section;
import com.example.filmmoi.event.MessageEvent;
import com.example.filmmoi.repository.ApiManager;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3};
    List<Section> listSection = new ArrayList<>();
    SectionAdapter sectionAdapter = new SectionAdapter();
    MovieAdapter movieAdapter = new MovieAdapter();
    List<List<Movie>> listTitle = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBanner();
        initView();
    }

    private void initView() {
        RecyclerView rvHome = findViewById(R.id.rvHome);
        //B1: Data
        callApi();

        //B2: Layout
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //B3: Adapter
        sectionAdapter = new SectionAdapter(this, listSection);

        //B4:
        rvHome.setLayoutManager(layoutManager);
        rvHome.setAdapter(sectionAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void callApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiManager service = retrofit.create(ApiManager.class);
        service.apiGetData().enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                Content content = item.getContent();
                listTitle.add(content.getTrending());
                listTitle.add(content.getHot());
                listTitle.add(content.getPopular());
                listTitle.add(content.getUpcoming());
                movieAdapter.reloadData(listTitle.get(0));
                movieAdapter.reloadData(listTitle.get(1));
                movieAdapter.reloadData(listTitle.get(2));
                movieAdapter.reloadData(listTitle.get(3));
                listSection.add(new Section("Trending", listTitle.get(0)));
                listSection.add(new Section("Hot", listTitle.get(1)));
                listSection.add(new Section("Popular", listTitle.get(2)));
                listSection.add(new Section("Upcoming", listTitle.get(3)));
                sectionAdapter.reloadSection(listSection);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });


    }

    private void initBanner() {
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "onClick: "+position);
            }
        });
    }

    private void goToDetail(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("MOVIE", (Parcelable) movie);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent.MovieEvent movieEvent) {
        Movie movie = movieEvent.getMovie();
        Log.d("TAG", "onMessageEvent: "+movie.getName());
        goToDetail(movie);
    }
}