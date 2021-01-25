package com.example.loginfragment.ui.event;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.loginfragment.Login.SignInDirections;
import com.example.loginfragment.Login.SignUpDirections;
import com.example.loginfragment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
/**
 * Murat YILMAZ
 */
public class EventFragment extends Fragment {

    EditText eventNameText;
    EditText detailsText;
    EditText locationText;
    EditText eventDateText;
    EditText conditionsText;
    EditText contactText;
    Button btn_create_event;
    Uri imageData;
    Bitmap selectedImage;
    ImageView imageView;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public EventFragment() {
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
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        eventNameText = view.findViewById(R.id.eventNameText);
        detailsText = view.findViewById(R.id.detailsText);
        locationText = view.findViewById(R.id.locationText);
        eventDateText = view.findViewById(R.id.eventDateText);
        conditionsText = view.findViewById(R.id.conditionsText);
        contactText = view.findViewById(R.id.contactText);

        btn_create_event = view.findViewById(R.id.btn_create_event);
        btn_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent(view);
            }
        });

        imageView = view.findViewById(R.id.uploadImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(view);
            }
        });
    }

    public void createEvent(final View view) {

        if (imageData != null) {
            //universal unique id
            UUID uuid = UUID.randomUUID();
            final String imageName = "images/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Download URL

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString();

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userEmail = firebaseUser.getEmail();

                            //TODO:burda uygulamayı oluşturan kullanıcıya ait userEmail dışında etkinliği oluşturan kişinin adını da eklemem gerekir.
                            String eventName = eventNameText.getText().toString();
                            String details = detailsText.getText().toString();
                            String location = locationText.getText().toString();
                            String eventDate = eventDateText.getText().toString();
                            String conditions = conditionsText.getText().toString();
                            String contact = contactText.getText().toString();

                            Boolean isSendInvite = false;
                            Boolean isAccepted = null;

                            HashMap<String, Object> postData = new HashMap<>();
                            postData.put("eventName",eventName);
                            postData.put("details",details);
                            postData.put("location",location);
                            postData.put("eventDate",eventDate);
                            postData.put("conditions",conditions);
                            postData.put("contact",contact);
                            postData.put("downloadurl",downloadUrl);
                            postData.put("userEmail",userEmail);
                            postData.put("date", FieldValue.serverTimestamp());

                            postData.put("isSendInvite",isSendInvite);
                            postData.put("isAccepted",isAccepted);

                            firebaseFirestore.collection("Events").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(),"Event Eklendi!", Toast.LENGTH_SHORT).show();
                                    /*NavDirections action = SignInDirections.actionSignInToForgotPassword();
                                    Navigation.findNavController(view).navigate(action);*/
                                    eventNameText.setText("");
                                    detailsText.setText("");
                                    locationText.setText("");
                                    eventDateText.setText("");
                                    conditionsText.setText("");
                                    contactText.setText("");
                                    imageView.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu_upload_you_tube));

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void selectImage(View view){
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery,2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null ) {

            imageData = data.getData();

            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(),imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageData);
                    imageView.setImageBitmap(selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

//TODO: seçtiğin resmin boyutlandırmasını yapman gerekiyor