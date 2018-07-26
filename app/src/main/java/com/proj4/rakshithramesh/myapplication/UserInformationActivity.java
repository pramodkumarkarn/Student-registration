package com.proj4.rakshithramesh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserInformationActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String redId;
    private String classAdded = null;

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView redIdTextView;

    private Button register;
    private Button waitList;
    private Button addClass;
    private Button logOut;

    private UserInformationModel userInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        firstNameTextView = findViewById(R.id.userFirstNameValue);
        lastNameTextView = findViewById(R.id.userLastNameValue);
        redIdTextView = findViewById(R.id.UserRedIdValue);
        register = findViewById(R.id.registerButton);
        waitList = findViewById(R.id.waitlistButton);
        addClass = findViewById(R.id.addClassButton);
        logOut = findViewById(R.id.logOutButton);

        Intent intent = getIntent();
        userInformationModel = (UserInformationModel) intent.getSerializableExtra("UserData");
        if (intent.getSerializableExtra("class added") != null)
            displayToast();
        firstName = userInformationModel.getFirstName();
        lastName = userInformationModel.getLastName();
        redId = userInformationModel.getRedId();
        firstNameTextView.setText(firstName, TextView.BufferType.NORMAL);
        lastNameTextView.setText(lastName, TextView.BufferType.NORMAL);
        redIdTextView.setText(redId, TextView.BufferType.NORMAL);

        register.setOnClickListener((view) -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            registerIntent.putExtra("UserData", userInformationModel);
            startActivity(registerIntent);
        });

        waitList.setOnClickListener((view) -> {
            Intent waitListIntent = new Intent(this, WaitlistActivity.class);
            waitListIntent.putExtra("UserData", userInformationModel);
            startActivity(waitListIntent);
        });

        addClass.setOnClickListener((view) -> {
            Intent addClassIntent = new Intent(this, FilterCoursesActivity.class);
            addClassIntent.putExtra("UserData", userInformationModel);
            startActivity(addClassIntent);
        });

        logOut.setOnClickListener((view) -> {
            Intent logInIntent = new Intent(this, LoginActivity.class);
            //addClassIntent.putExtra("UserData",userInformationModel);
            logInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logInIntent);
        });


    }

    private void displayToast() {
        Toast toast = Toast.makeText(getApplicationContext(), "COURSE ADDED", Toast.LENGTH_LONG);
        toast.show();
    }
}
