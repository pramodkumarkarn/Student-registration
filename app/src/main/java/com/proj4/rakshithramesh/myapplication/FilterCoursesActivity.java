package com.proj4.rakshithramesh.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class FilterCoursesActivity extends AppCompatActivity {

    private TextView TextMessage;
    private Fragment fragment = null;
    private UserInformationModel userInformationModel;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_majors:
                    fragment = new MajorsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_levels:
                    fragment = new LevelsFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter_courses);


        EmptyStateFragment emptyStateFragment = new EmptyStateFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, emptyStateFragment).commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationBar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        userInformationModel = (UserInformationModel) getIntent().getSerializableExtra("UserData");

    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            Bundle data = new Bundle();
            data.putSerializable("UserData", userInformationModel);
            fragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }

    }


}
