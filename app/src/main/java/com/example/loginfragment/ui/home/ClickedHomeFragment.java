package com.example.loginfragment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ClickedHomeFragment extends Fragment {
    TextView EventNameText;
    TextView EventCreatorText;
    TextView EventLocationText;
    TextView EventDateText;
    TextView EventDetailsText;
    ImageView EventImage;
    Button BtnJoin;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    ArrayList<String> eventNameFromFB;
    ArrayList<String> eventCreatorFromFB;
    ArrayList<String> eventImageFromFB;
    ArrayList<String> eventLocationFromFB;
    ArrayList<String> eventDateFromFB;
    ArrayList<String> eventDetailsFromFB;
    int position;



    public ClickedHomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home_click, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventNameText = view.findViewById(R.id.EventNameText);
        EventCreatorText = view.findViewById(R.id.EventCreatorText);
        EventLocationText = view.findViewById(R.id.EventLocationText);
        EventDateText = view.findViewById(R.id.EventDateText);
        EventDetailsText = view.findViewById(R.id.EventDetailsText);
        EventImage = view.findViewById(R.id.EventImage);
        BtnJoin = view.findViewById(R.id.JoinButton);

        eventNameFromFB = new ArrayList<>();
        eventLocationFromFB = new ArrayList<>();
        eventImageFromFB = new ArrayList<>();

        eventCreatorFromFB = new ArrayList<>();
        eventDateFromFB = new ArrayList<>();
        eventDetailsFromFB = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        getDataFromFirestore();

        if( getArguments() != null){
            position = ClickedHomeFragmentArgs.fromBundle(getArguments()).getPosition();
        }
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
                        String eventCreator = (String) data.get("userEmail");
                        String downloadUrl = (String) data.get("downloadurl");
                        String location = (String) data.get("location");
                        String eventDate = (String) data.get("eventDate");
                        String eventDetails = (String) data.get("details");

                        eventNameFromFB.add(eventName);

                        eventCreatorFromFB.add(eventCreator);
                        eventImageFromFB.add(downloadUrl);
                        eventLocationFromFB.add(location);
                        eventDateFromFB.add(eventDate);
                        eventDetailsFromFB.add(eventDetails);
                        try{
                            EventNameText.setText(eventNameFromFB.get(position));
                            EventCreatorText.setText("Oluşturan : " + eventCreatorFromFB.get(position));
                            EventLocationText.setText("Konum : " + eventLocationFromFB.get(position));
                            EventDateText.setText("Tarih : " + eventDateFromFB.get(position));
                            EventDetailsText.setText("Detaylar : " + eventDetailsFromFB.get(position));
                            Picasso.get().load(eventImageFromFB.get(position)).into(EventImage);
                        }catch(Exception exception){
                            System.out.println(exception);
                        }
                    }
                }
            }
        });


    }
}


//TODO: navigations arası iletişimde arraylist i almaya çalışacaksın!!!