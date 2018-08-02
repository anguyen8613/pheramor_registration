package b.android.pheramorregistration;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class RaceAndReligionFragment extends Fragment {

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramorregistration.updated_user";

    private Spinner mRaceSpinner;
    private Spinner mReligionSpinner;
    private Button mBackButton;
    private Button mNextButton;

    private User mUser;

    public static RaceAndReligionFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        RaceAndReligionFragment fragment = new RaceAndReligionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = (User) getArguments().getSerializable(ARGS_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_race_religion, container, false);

        mRaceSpinner = v.findViewById(R.id.race_spinner);

        //Example list of Race
        String[] raceArray = {"Non Disclosed", "African American", "Caucasian", "Asian"};
        ArrayAdapter<String> raceSpinnerArrayAdpater = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, raceArray);
        raceSpinnerArrayAdpater.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mRaceSpinner.setAdapter(raceSpinnerArrayAdpater);

        mReligionSpinner = v.findViewById(R.id.religion_spinner);
        //Example list of Religion
        String[] religionArray = {"Non Disclosed", "Catholic", "Buddhist", "Christian"};
        ArrayAdapter<String> religionSpinnerArrayAdpater = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, religionArray);
        religionSpinnerArrayAdpater.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mReligionSpinner.setAdapter(religionSpinnerArrayAdpater);



        mBackButton = (Button) v.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fm.popBackStackImmediate();
            }
        });

        mNextButton = v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mUser.setRace(mRaceSpinner.getSelectedItem().toString());
                mUser.setReligion(mReligionSpinner.getSelectedItem().toString());
                updateUser();
               // Intent intent = new Intent(getActivity(), PicturePickerActivity.class);
               // startActivity(intent);
                Fragment fragment = PicturePickerFragment.newInstance(mUser);
                MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        return v;
    }

    public void updateUser(){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_UPDATED_USER, mUser);
        getActivity().setResult(Activity.RESULT_OK, intent);
    }

    public static User parseUser(Intent result){
        return (User) result.getSerializableExtra(EXTRA_UPDATED_USER);
    }
}
