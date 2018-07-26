package com.proj4.rakshithramesh.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelsFragment extends Fragment {

    View levelView;
    RadioGroup radioViewGroup;
    private String levelSelected = null;
    private String startTime = null;
    private String endTime = null;
    private RadioButton lower;
    private RadioButton upper;
    private RadioButton graduate;
    private Button searchButton;
    private EditText startTimeValue;
    private EditText endTimeValue;


    public LevelsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        levelView = inflater.inflate(R.layout.fragment_levels, container, false);
        lower = levelView.findViewById(R.id.lowerRadioButton);
        upper = levelView.findViewById(R.id.upperRadioButton);
        graduate = levelView.findViewById(R.id.gradRadioButton);
        searchButton = levelView.findViewById(R.id.searchButton);
        startTimeValue = levelView.findViewById(R.id.startTimeValue);
        endTimeValue = levelView.findViewById(R.id.endTimeValue);
        radioViewGroup = levelView.findViewById(R.id.radioGroup);
        return levelView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // onRadioButtonClicked(radioView);

        searchButton.setOnClickListener((view) -> {
            startTime = startTimeValue.getText().toString();
            endTime = endTimeValue.getText().toString();
            if (levelSelected == null) {
                Toast text = Toast.makeText(getContext(), "SELECT LEVEL", Toast.LENGTH_SHORT);
                text.show();
            } else {
                Log.d("level", levelSelected);
                Bundle args = new Bundle();
                args.putSerializable("Level", levelSelected);
                if (startTime != null) {
                    args.putSerializable("StartTime", startTime);
                }
                if (endTime != null) {
                    args.putSerializable("EndTime", endTime);
                }
                MajorsFragment majorsFragment = new MajorsFragment();
                majorsFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, majorsFragment).commit();
            }
        });

        radioViewGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.lowerRadioButton:
                        levelSelected = "lower";
                        break;
                    case R.id.upperRadioButton:
                        levelSelected = "upper";
                        break;
                    case R.id.gradRadioButton:
                        levelSelected = "graduate";
                        break;
                }
            }

        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.lowerRadioButton:
                if (checked) {
                    levelSelected = "lower";
                }
                break;
            case R.id.upperRadioButton:
                if (checked) {
                    levelSelected = "upper";
                }
                break;
            case R.id.gradRadioButton:
                if (checked) {
                    levelSelected = "graduate";
                    break;
                }
        }
    }
}
