package com.proj4.rakshithramesh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCourseFragment extends Fragment {

    CourseListModel courseListModel;
    UserInformationModel userInformationModel;

    private EditText title;
    private EditText department;
    private EditText instructor;
    private EditText scheduleNo;
    private EditText units;
    private EditText room;
    private EditText startTime;
    private EditText endTime;
    private EditText prequesite;
    private EditText description;
    private String classAdded = null;

    private Button addCourseButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View courseDetailsView = inflater.inflate(R.layout.fragment_add_course, container, false);
        title = courseDetailsView.findViewById(R.id.titleValue);
        department = courseDetailsView.findViewById(R.id.departmentValue);
        instructor = courseDetailsView.findViewById(R.id.instructorValue);
        scheduleNo = courseDetailsView.findViewById(R.id.scheduleNoValue);
        units = courseDetailsView.findViewById(R.id.unitsValue);
        room = courseDetailsView.findViewById(R.id.roomValue);
        startTime = courseDetailsView.findViewById(R.id.startTimeValue);
        endTime = courseDetailsView.findViewById(R.id.endTimeValue);
        prequesite = courseDetailsView.findViewById(R.id.prerequesiteValue);
        description = courseDetailsView.findViewById(R.id.descriptionValue);
        addCourseButton = courseDetailsView.findViewById(R.id.submitButton);

        return courseDetailsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            courseListModel = (CourseListModel) getArguments().getSerializable("CourseDetails");
            userInformationModel = (UserInformationModel) getArguments().getSerializable("UserData");
        }
        updateView(courseListModel);

        addCourseButton.setOnClickListener((view) -> {
            addCourseRequest();
        });
    }

    private void addCourseRequest() {
        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Log.d("rew", response.toString());
                Intent userInformationActivity = new Intent(getActivity(), UserInformationActivity.class);
                userInformationActivity.putExtra("UserData", userInformationModel);
                userInformationActivity.putExtra("class added", "Class Added");
                Toast message = Toast.makeText(getContext(), "COURSE ADDED", Toast.LENGTH_SHORT);
                message.show();
                startActivity(userInformationActivity);
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
                String message = error.networkResponse.data.toString();
                Toast errorMessage = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                errorMessage.show();
            }
        };

        String urlPost = "https://bismarck.sdsu.edu/registration/registerclass";
        JSONObject dataToSend = new JSONObject();
        try {
            dataToSend.put("redid", userInformationModel.getRedId());
            dataToSend.put("password", userInformationModel.getPassword());
            dataToSend.put("courseid", courseListModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlPost, dataToSend, success, failure);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void updateView(CourseListModel courseListModel) {
        title.setText(courseListModel.getFullTitle(), TextView.BufferType.NORMAL);
        department.setText(courseListModel.getDepartment(), TextView.BufferType.NORMAL);
        instructor.setText(courseListModel.getInstructor(), TextView.BufferType.NORMAL);
        scheduleNo.setText(courseListModel.getScheduleNo(), TextView.BufferType.NORMAL);
        units.setText(courseListModel.getUnits(), TextView.BufferType.NORMAL);
        room.setText(courseListModel.getRoom(), TextView.BufferType.NORMAL);
        startTime.setText(courseListModel.getStartTime(), TextView.BufferType.NORMAL);
        endTime.setText(courseListModel.getEndTime(), TextView.BufferType.NORMAL);
        prequesite.setText(courseListModel.getPrerequesite(), TextView.BufferType.NORMAL);
        description.setText(courseListModel.getRoom(), TextView.BufferType.NORMAL);

    }
}
