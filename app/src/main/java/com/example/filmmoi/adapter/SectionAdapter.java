package com.example.filmmoi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmmoi.R;
import com.example.filmmoi.entity.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Section> listSection;
    MovieAdapter adapter;


    public SectionAdapter(Activity activity, List<Section> listSection) {
        this.activity = activity;
        this.listSection = listSection;
    }

    public SectionAdapter() {

    }

    //    public void reloadSection(List<Section> list, List<Movie> movieList){
//        adapter.reloadData(movieList);
//        listSection = list;
//        notifyDataSetChanged();
//    }
    @SuppressLint("NotifyDataSetChanged")
    public void reloadSection(List<Section> list){
        listSection = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.activity_film_section, parent,false);
        SectionHolder holder = new SectionHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SectionHolder vh = (SectionHolder) holder;
        //B1
        Section model = listSection.get(position);

        //B2
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

        //B3
        MovieAdapter adapter = new MovieAdapter(activity, model.getListMovies());
        adapter.setSection(model.getTitle());
        //B4
        vh.rvSection.setLayoutManager(layoutManager);
        vh.rvSection.setHasFixedSize(true);
        vh.rvSection.setAdapter(adapter);

        vh.tvTitle.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return listSection.size();
    }

    public static class SectionHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        RecyclerView rvSection;
        public SectionHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvSection = itemView.findViewById(R.id.rvSection);
        }
    }
}
