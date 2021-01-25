package com.example.loginfragment.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginfragment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter2 extends RecyclerView.Adapter<FeedRecyclerAdapter2.Holder>  {

    private ArrayList<String> userEmailList;
    private ArrayList<String> eventNameList;
    private ArrayList<String> eventImageList;

    public FeedRecyclerAdapter2(ArrayList<String> userEmailList, ArrayList<String> eventNameList, ArrayList<String> eventImageList) {
        this.userEmailList = userEmailList;
        this.eventNameList = eventNameList;
        this.eventImageList = eventImageList;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_notifications,parent,false);
        return new FeedRecyclerAdapter2.Holder(view);
    }    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.userEmailText.setText(userEmailList.get(position));
        holder.eventNameText.setText(eventNameList.get(position));
        Picasso.get().load(eventImageList.get(position)).into(holder.userImage);
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Talep Onaylandı !!", Toast.LENGTH_LONG).show();
            }
        });
        holder.btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Talep Reddedildi !!", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return userEmailList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        //ImageView imageView;
        TextView userEmailText;
        TextView eventNameText;
        ImageView userImage;
        Button btnAccept;
        Button btnDecline;
        public Holder(@NonNull View itemView) {
            super(itemView);

            //imageView = itemView.findViewById(R.id.recyclerview_image);
            userEmailText = itemView.findViewById(R.id.userEmailText);
            eventNameText = itemView.findViewById(R.id.eventNameText);
            userImage = itemView.findViewById(R.id.userImage);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnDecline = itemView.findViewById(R.id.btn_decline);
        }
    }
}
//TODO:profileImage ayarlanacak.