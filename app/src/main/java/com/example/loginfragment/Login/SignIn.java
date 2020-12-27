package com.example.loginfragment.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.loginfragment.MainActivity2;
import com.example.loginfragment.R;


public class SignIn extends Fragment {

    public SignIn() {
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_signin = view.findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainPage(view);
            }
        });




        TextView go_signup_text = view.findViewById(R.id.goto_signup);
        go_signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUpPage(view);
            }
        });
    }

    public void goToSignUpPage(View view){

        NavDirections action = SignInDirections.actionSignInToSignUp();
        Navigation.findNavController(view).navigate(action);
    }

    public void goToMainPage(View view){

        Intent intent = new Intent(getActivity(), MainActivity2.class);
        startActivity(intent);
    }
    //TODO: Şifremi unuttum a basınca yeni sayfaya geçmesi gerekiyor


}