package com.proj4.rakshithramesh.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private static final String PREFERENCENAME = "myPreferences";
    UserInformationModel userInformationModel = new UserInformationModel();
    private Button signUpButton;
    private EditText firstName;
    private EditText lastName;
    private EditText redId;
    private EditText emailId;
    private EditText password;
    private String firsNameValue;
    private String lastNameValue;
    private String redIdValue;
    private String emailIdValue;
    private String passwordValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = findViewById(R.id.signUpButton);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        redId = findViewById(R.id.redId);
        emailId = findViewById(R.id.emailId);
        password = findViewById(R.id.password);

        signUpButton.setOnClickListener((view) -> {
            getValues();
            if (validateData()) {
                postRequest(view);
            }

        });
    }


    private void postRequest(View view) {
        JSONObject data = new JSONObject();
        try {
            data.put("firstname", firsNameValue);
            data.put("lastname", lastNameValue);
            data.put("redid", redIdValue);
            data.put("password", passwordValue);
            data.put("email", emailIdValue);
        } catch (JSONException error) {
            Log.e("rew", "JSON error", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("rew", response.toString());
                Log.d("firstname", firsNameValue);
                userInformationModel.setFirstName(firsNameValue);
                userInformationModel.setLastName(lastNameValue);
                userInformationModel.setEmail(emailIdValue);
                userInformationModel.setRedId(redIdValue);
                userInformationModel.setPassword(passwordValue);
                Toast message = Toast.makeText(getApplicationContext(), "REGISTERED", Toast.LENGTH_SHORT);
                message.show();
                SharedPreferences.Editor editor = getSharedPreferences(PREFERENCENAME, MODE_PRIVATE).edit();
                editor.putString("redId", redIdValue);
                editor.putString("password", passwordValue);
                editor.apply();
                Intent signUp = new Intent(view.getContext(), UserInformationActivity.class);
                signUp.putExtra("UserData", userInformationModel);
                signUp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signUp);
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rew", "post fail " + new String(error.networkResponse.data));
                String message = error.networkResponse.data.toString();
                Toast errorMessage = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                errorMessage.show();
            }
        };
        String url = "https://bismarck.sdsu.edu/registration/addstudent";
        JsonObjectRequest postRequest = new JsonObjectRequest(url, data, success, failure);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    private boolean validateData() {
        if (firsNameValue == null) {
            firstName.setBackgroundColor(Color.RED);
            return false;
        }
        if (lastNameValue == null) {
            firstName.setBackgroundColor(Color.RED);
            return false;
        }
        if (redIdValue == null) {
            firstName.setBackgroundColor(Color.RED);
            return false;
        }
        if (emailId == null) {
            firstName.setBackgroundColor(Color.RED);
            return false;
        }
        if (password == null) {
            firstName.setBackgroundColor(Color.RED);
            return false;
        }

        return true;
    }

    private void getValues() {
        firsNameValue = firstName.getText().toString();
        lastNameValue = lastName.getText().toString();
        redIdValue = redId.getText().toString();
        emailIdValue = emailId.getText().toString();
        passwordValue = password.getText().toString();

    }
}
