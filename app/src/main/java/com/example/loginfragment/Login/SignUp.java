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


public class SignUp extends Fragment {

    EditText EmailText;
    EditText NameText;
    EditText PasswordText;
    EditText PhoneNumberText;
    Button btn_sign_up;

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

        EmailText = view.findViewById(R.id.EmailText);
        NameText = view.findViewById(R.id.NameText);
        PasswordText = view.findViewById(R.id.PasswordText);
        PhoneNumberText = view.findViewById(R.id.PhoneNumberText);

        btn_sign_up = view.findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignInPage(view);
            }
        });
    }

    public void goToSignInPage(View view){

        NavDirections action = SignUpDirections.actionSignUpToSignIn();
        Navigation.findNavController(view).navigate(action);

    }
}

//TODO: telefon numarasını da vermesi gerekiyor