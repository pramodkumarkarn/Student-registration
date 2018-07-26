package com.proj4.rakshithramesh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements DropClassAdapter.DropButtonClickListener {

    UserInformationModel userInformationModel;
    CourseListModel courseListModel;
    ArrayList<Integer> classIdsList = new ArrayList<>();
    ListView registerClassList;
    ArrayList<CourseListModel> courseListDetails = new ArrayList<>();
    DropClassAdapter dropClassAdapter = new DropClassAdapter(this, courseListDetails);
    private String password;
    private String redId;
    private String dropCall = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerClassList = findViewById(R.id.registerClassList);
        userInformationModel = (UserInformationModel) getIntent().getSerializableExtra("UserData");
        redId = userInformationModel.getRedId();
        password = userInformationModel.getPassword();
        postRegisterDataRequest();
    }

    private void postRegisterDataRequest() {
        JSONObject data = new JSONObject();
        try {
            data.put("redid", redId);
            data.put("password", password);
        } catch (JSONException error) {
            Log.e("rew", "JSON eorror", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray dataResponse = null;
                Log.i("rew", response.toString());
                try {
                    dataResponse = response.getJSONArray("classes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < data.length(); i++) {
                    try {
                        classIdsList.add(Integer.valueOf(dataResponse.get(i).toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                postRequest(classIdsList);

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
        String url = "https://bismarck.sdsu.edu/registration/studentclasses";
        JsonObjectRequest postRequest = new JsonObjectRequest(url, data, success, failure);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }


    private void postRequest(ArrayList<Integer> classIdsList) {

        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("rew", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        courseListDetails.add(new Gson().fromJson(response.get(i).toString(), CourseListModel.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (dropCall == null)
                    displayClassList(courseListDetails);

            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };

        String urlPost = "https://bismarck.sdsu.edu/registration/classdetails";
        JSONObject dataToSend = new JSONObject();
        try {
            dataToSend.put("classids", new JSONArray(classIdsList));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModifiedJsonArrayRequest modifiedJsonArrayRequest = new ModifiedJsonArrayRequest(Request.Method.POST, urlPost, dataToSend, success, failure);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(modifiedJsonArrayRequest);
    }

    private void displayClassList(ArrayList<CourseListModel> courseDetails) {

        registerClassList.setAdapter(dropClassAdapter);

    }

    public void dropClass(final Integer postion) {
        courseListModel = courseListDetails.get(postion);
        postUnregisterRequest(courseListModel);
    }

    private void postUnregisterRequest(CourseListModel courseListModel) {
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("rew", response.toString());

                if (response != null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "COURSE DROPPED", Toast.LENGTH_LONG);
                    toast.show();
                    dropCall = "dropped";
                    courseListDetails.clear();
                    postRegisterDataRequest();
                    dropClassAdapter.notifyDataSetChanged();

                }
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };

        String urlPost = "https://bismarck.sdsu.edu/registration/unregisterclass";
        JSONObject dataToSend = new JSONObject();
        try {
            dataToSend.put("redid", userInformationModel.getRedId());
            dataToSend.put("password", userInformationModel.getPassword());
            dataToSend.put("courseid", courseListModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModifiedJsonArrayRequest modifiedJsonArrayRequest = new ModifiedJsonArrayRequest(Request.Method.POST, urlPost, dataToSend, success, failure);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(modifiedJsonArrayRequest);

    }


}
