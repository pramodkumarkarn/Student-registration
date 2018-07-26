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
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MajorsFragment extends Fragment {

    private static String NOTSELECTED = "notselected";
    TextView mTextView;
    ListView majorListView;
    JsonObjectRequest jsonObjectRequest;
    UserInformationModel userInformationModel;
    private String levelSelected = NOTSELECTED;
    private String startTime = NOTSELECTED;
    private String endTime = NOTSELECTED;

    public MajorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View majorView = inflater.inflate(R.layout.fragment_majors, container, false);
        //mTextView = majorView.findViewById(R.id.testData);
        majorListView = majorView.findViewById(R.id.majorList);

        return majorView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable("Level") != null) {
                levelSelected = getArguments().getSerializable("Level").toString();
            }
            if (getArguments().getSerializable("StartTime") != null) {
                startTime = getArguments().getSerializable("StartTime").toString();
            }
            if (getArguments().getSerializable("EndTime") != null) {
                endTime = getArguments().getSerializable("EndTime").toString();
            }
            userInformationModel = (UserInformationModel) getArguments().getSerializable("UserData");
        }
        getRequest();
    }

    public void getRequest() {
        ArrayList<MajorListModel> majorListSubjectDetails = new ArrayList<>();
        Log.i("rew", "Start");
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("rew", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        majorListSubjectDetails.add(new Gson().fromJson(response.get(i).toString(), MajorListModel.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                displayMajorList(majorListSubjectDetails);

            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };

        String url = "https://bismarck.sdsu.edu/registration/subjectlist";
        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        MySingleton.getInstance(getContext()).addToRequestQueue(getRequest);
    }

    private void displayMajorList(ArrayList<MajorListModel> majorListSubjectDetails) {
        MajorListAdapter adapter = new MajorListAdapter(this.getActivity(), majorListSubjectDetails);
        majorListView.setAdapter(adapter);
        majorListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            MajorListModel selectedSubject = majorListSubjectDetails.get(position);
            Bundle data = new Bundle();
            //data.putSerializable("CourseID",selectedSubject.getId());
            data.putSerializable("CourseID", majorListSubjectDetails.get(position));
            Log.d("subjectId", String.valueOf(selectedSubject.getId()));
            // if (levelSelected != null){
            data.putSerializable("Level", levelSelected);
            // }
            // if (startTime != null){
            data.putSerializable("StartTime", startTime);
            // }
            // if (endTime != null){
            data.putSerializable("EndTime", endTime);
            // }
            data.putSerializable("UserData", userInformationModel);
            CourseListFragment courseListFragment = new CourseListFragment();
            courseListFragment.setArguments(data);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, courseListFragment).addToBackStack(null).commit();
        });
    }
}
