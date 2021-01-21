package com.example.loginfragment.ui.home;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginfragment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<String> eventNameList;
    private ArrayList<String> eventLocationList;
    private ArrayList<String> eventImageList;


    public FeedRecyclerAdapter(ArrayList<String> eventNameList, ArrayList<String> eventLocationList, ArrayList<String> eventImageList) {
        this.eventNameList = eventNameList;
        this.eventLocationList = eventLocationList;
        this.eventImageList = eventImageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, final int position) {

        holder.eventNameText.setText(eventNameList.get(position));
        holder.locationText.setText(eventLocationList.get(position));
        Picasso.get().load(eventImageList.get(position)).into(holder.imageView);

        // click denemese
        //action_navigation_home_to_homeClickFragment

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeClickFragment(view, position);
            }
        });
    }

    public void goToHomeClickFragment(View view, final int position){

        HomeFragmentDirections.ActionNavigationHomeToNavigationHomeClick action = HomeFragmentDirections.actionNavigationHomeToNavigationHomeClick();
        action.setPosition(position);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public int getItemCount() {
        return eventNameList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView eventNameText;
        TextView locationText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerview_image);
            eventNameText = itemView.findViewById(R.id.recyclerview_eventNameText);
            locationText = itemView.findViewById(R.id.recyclerview_locationNameText);
        }
    }
}
