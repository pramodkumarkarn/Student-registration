package com.proj4.rakshithramesh.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment {

    private static String NOTSELECTED = "notselected";
    ListView courseListView;
    Integer courseID;
    String url;
    private String levelSelected = NOTSELECTED;
    private String startTime = NOTSELECTED;
    private String endTime = NOTSELECTED;
    private MajorListModel majorListModel;
    private UserInformationModel userInformationModel;

    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View courseView = inflater.inflate(R.layout.fragment_course_list, container, false);
        courseListView = courseView.findViewById(R.id.courseList);

        return courseView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            //noinspection ConstantConditions
            majorListModel = (MajorListModel) getArguments().getSerializable("CourseID");
            courseID = (Integer) majorListModel.getId();
            // if (getArguments().getSerializable("Level").toString()!= null){
            levelSelected = getArguments().getSerializable("Level").toString();
            Log.d("level", levelSelected);
            //}
            // if (getArguments().getSerializable("StartTime").toString()!= null){
            startTime = getArguments().getSerializable("StartTime").toString();
            // }
            //if (getArguments().getSerializable("EndTime").toString()!= null){
            endTime = getArguments().getSerializable("EndTime").toString();
            //   }
            userInformationModel = (UserInformationModel) getArguments().getSerializable("UserData");
        }
        url = (new StringBuilder()).append("https://bismarck.sdsu.edu/registration/classidslist?subjectid=").append(courseID).toString();
        if (!Objects.equals(levelSelected, NOTSELECTED)) {
            url = (new StringBuilder()).append(url).append("&level=").append(levelSelected).toString();
        }
        if (!Objects.equals(startTime, NOTSELECTED)) {
            url = (new StringBuilder()).append(url).append("&start-time=").append(startTime).toString();
        }
        if (!endTime.equals(NOTSELECTED)) {
            url = (new StringBuilder()).append(url).append("&end-time=").append(endTime).toString();
        }
        getRequest();


    }


    public void getRequest() {
        ArrayList<Integer> classIdsList = new ArrayList<>();
        Log.i("rew", "Start");
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("ClassidList", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        classIdsList.add(Integer.valueOf(response.get(i).toString()));
                        //majorListSubjectDetails.add(new Gson().fromJson(response.get(i).toString(),MajorListModel.class));
                        postRequest(classIdsList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // displayMajorList(majorListSubjectDetails);

            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };


//        JSONArray requestData = new JSONArray();
//        try {
//            requestData.put("subjectids",courseID);
//        }
        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        MySingleton.getInstance(getContext()).addToRequestQueue(getRequest);
    }

    private void postRequest(ArrayList<Integer> classIdsList) {
        ArrayList<CourseListModel> courseListDetails = new ArrayList<>();
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
        MySingleton.getInstance(getContext()).addToRequestQueue(modifiedJsonArrayRequest);
    }

    private void displayClassList(ArrayList<CourseListModel> courseDetails) {
        CourseListAdapter courseListAdapter = new CourseListAdapter(this.getActivity(), courseDetails);
        courseListView.setAdapter(courseListAdapter);
        courseListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Bundle data = new Bundle();
            data.putSerializable("CourseDetails", courseDetails.get(position));
            data.putSerializable("UserData", userInformationModel);
            AddCourseFragment addCourseFragment = new AddCourseFragment();
            addCourseFragment.setArguments(data);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, addCourseFragment).addToBackStack(null).commit();
        });

    }
}
