package com.example.loginfragment.Login;

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
import android.widget.EditText;

import com.example.loginfragment.R;


public class ForgotPassword extends Fragment {
    EditText EmailText;
    EditText PasswordText;
    EditText RepeatPasswordText;
    Button btn_update;
    public ForgotPassword() {
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EmailText = view.findViewById(R.id.EmailText);
        PasswordText = view.findViewById(R.id.PasswordText);
        RepeatPasswordText = view.findViewById(R.id.RepeatPasswordText);

        btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignInPage(view);
            }
        });
    }

    public void goToSignInPage(View view){

        NavDirections action = ForgotPasswordDirections.actionForgotPasswordToSignIn();
        Navigation.findNavController(view).navigate(action);

    }


}