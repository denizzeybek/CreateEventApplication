package com.example.loginfragment.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginfragment.MainActivity2;
import com.example.loginfragment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignUp extends Fragment {

    private  FirebaseAuth firebaseAuth;
    EditText EmailText;
    EditText PasswordText;
    Button btn_sign_up;
    TextView btn_goto_signin;

    public SignUp() {
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        EmailText = view.findViewById(R.id.EmailText);
        PasswordText = view.findViewById(R.id.PasswordText);

        btn_goto_signin = view.findViewById(R.id.btn_goto_signin);
        btn_goto_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignInPage(view);
            }
        });

        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpToMainPage(view);
            }
        });
    }
    public void goToSignInPage(View view){
        NavDirections action = SignUpDirections.actionSignUpToSignIn();
        Navigation.findNavController(view).navigate(action);
    }
    public void signUpToMainPage(final View view){
        String email = EmailText.getText().toString();
        String password = PasswordText.getText().toString();

        if(TextUtils.isEmpty(email)){
            EmailText.setError("Email alanı boş bırakılamaz");
            return;
        }
        if(TextUtils.isEmpty(password)){
            PasswordText.setError("Şifre alanı boş bırakılamaz");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getContext(), "Kullanıcı Oluşturuldu!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
                getActivity().finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });



    }
}

//TODO: telefon numarasını da vermesi gerekiyor