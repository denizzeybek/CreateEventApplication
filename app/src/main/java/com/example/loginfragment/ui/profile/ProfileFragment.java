package com.example.loginfragment.ui.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.loginfragment.MainActivity;
import com.example.loginfragment.MainActivity2;
import com.example.loginfragment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.app.Activity.RESULT_OK;
/**
 * Created by Murat YILMAZ
 */
public class ProfileFragment extends Fragment {

    Uri imageData;
    Bitmap selectedImage;

    ImageView uploadImage;
    EditText editNameText;
    EditText EmailEditText;
    Button btn_reset_password;
    Button btn_logout;
    Button btn_profile_update;
    FirebaseUser user;
    FirebaseAuth mFirebaseAuth;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        EmailEditText = (EditText) view.findViewById(R.id.editEmailText);
        editNameText = (EditText) view.findViewById(R.id.editNameText);
        btn_reset_password = (Button) view.findViewById(R.id.btn_reset_password);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_profile_update = (Button) view.findViewById(R.id.btn_profile_update);
        uploadImage = (ImageView) view.findViewById(R.id.uploadImage);
        //PassEditText = (EditText) view.findViewById(R.id.editPasswordText);

        // Inflate the layout for this fragment
        //Toast.makeText(getActivity(),"TEST",Toast.LENGTH_LONG).show();


        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseAuth = FirebaseAuth.getInstance();

        if (firebaseUser != null) {
            String email = firebaseUser.getEmail();
            String name = firebaseUser.getDisplayName();
            Uri imageUri = firebaseUser.getPhotoUrl();
            //uploadImage.setImageDrawable(LoadImageFromWebOperations(imageUrl.toString()));
            //Toast.makeText(getActivity(),email,Toast.LENGTH_LONG).show();
            editNameText.setText(name);
            EmailEditText.setText(email);
            Picasso.get().load(imageUri).into(uploadImage);
            EmailEditText.setEnabled(false);
            // imageView.


        }

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        final FirebaseUser userx = FirebaseAuth.getInstance().getCurrentUser();


        btn_profile_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(editNameText.getText().toString())
                        .setPhotoUri(Uri.parse("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"))
                        .build();

                userx.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Profil başarıyla güncellendi!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });



        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPassword = new EditText(v.getContext());
                final AlertDialog.Builder passResDi = new AlertDialog.Builder(v.getContext());
                passResDi.setTitle("Şifremi Sıfırla?");
                passResDi.setMessage("Yeni bir şifre girin (6 karakterden uzun)");
                passResDi.setView(resetPassword);

                passResDi.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(getActivity(), "Password Reset Successfully.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passResDi.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passResDi.create().show();

            }
        });


        uploadImage = view.findViewById(R.id.uploadImage);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(view);
            }
        });


    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageData = data.getData();
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    uploadImage.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageData);
                    uploadImage.setImageBitmap(selectedImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

