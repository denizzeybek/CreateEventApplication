package com.example.loginfragment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginfragment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
/**
 * Created by Murat YILMAZ
 */
public class HomeFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> eventNameFromFB;
    ArrayList<String> eventLocationFromFB;
    ArrayList<String> eventImageFromFB;
    FeedRecyclerAdapter feedRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventNameFromFB = new ArrayList<>();
        eventLocationFromFB = new ArrayList<>();
        eventImageFromFB = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        getDataFromFirestore();

        //RecyclerView

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //firebase den çekeceğin arraylist bilgileri
        feedRecyclerAdapter = new FeedRecyclerAdapter(eventNameFromFB, eventLocationFromFB, eventImageFromFB);

        recyclerView.setAdapter(feedRecyclerAdapter);
    }

    public void getDataFromFirestore() {

        CollectionReference collectionReference = firebaseFirestore.collection("Events");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(getContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        //Casting
                        String eventName = (String) data.get("eventName");
                        String location = (String) data.get("location");
                        String downloadUrl = (String) data.get("downloadurl");

                        eventLocationFromFB.add(location);
                        eventNameFromFB.add(eventName);
                        eventImageFromFB.add(downloadUrl);
                        feedRecyclerAdapter.notifyDataSetChanged();
                    }


                }

            }
        });


    }

}


