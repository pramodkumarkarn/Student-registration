package com.proj4.rakshithramesh.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFERENCENAME = "myPreferences";
    private Button loginButton;
    private Button signUpButton;
    private EditText redId;
    private EditText password;
    private String redIdPref;
    private String passwordPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.logInButton);
        signUpButton = findViewById(R.id.signUpButton);
        redId = findViewById(R.id.redId);
        password = findViewById(R.id.password);
        SharedPreferences prefs = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE);
        redIdPref = prefs.getString("redId", null);
        passwordPref = prefs.getString("password", null);

        loginButton.setOnClickListener((view) -> {
            if (vaidateCredentials()) {
                Intent login = new Intent(view.getContext(), UserInformationActivity.class);
                startActivity(login);
            }

        });

        signUpButton.setOnClickListener((view) -> {
            Intent signUp = new Intent(view.getContext(), SignUpActivity.class);
            signUp.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(signUp);
        });
    }

    private boolean vaidateCredentials() {

        if (redIdPref == null || passwordPref == null) {
            Toast message = Toast.makeText(getApplicationContext(), "User Not Found,Register", Toast.LENGTH_LONG);
            message.show();
            return false;
        }
        if ((redIdPref.equals(redId.getText().toString())) && (passwordPref.equals(password.getText().toString())))
            return true;

        return false;
    }
}
