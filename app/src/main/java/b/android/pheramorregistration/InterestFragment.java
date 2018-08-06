package b.android.pheramorregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import b.android.pheramorregistration.models.User;
import biz.borealis.numberpicker.NumberPicker;

public class InterestFragment extends Fragment {

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramorregistration.updated_user";

    private CheckBox mMaleCheckBox;
    private CheckBox mFemaleCheckBox;
    private TextView mInvalidText;
    private NumberPicker mAgeMinPicker;
    private NumberPicker mAgeMaxPicker;
    private Button mBackButton;
    private Button mNextButton;

    private User mUser;

    public static InterestFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        InterestFragment fragment = new InterestFragment();
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

        View v = inflater.inflate(R.layout.fragment_interest, container, false);

        mMaleCheckBox = v.findViewById(R.id.male_checkbox);
        mFemaleCheckBox = v.findViewById(R.id.female_checkbox);

        mInvalidText = v.findViewById(R.id.invalid_text);
        mAgeMinPicker = v.findViewById(R.id.age_min_picker);
        mAgeMinPicker.setMin(18);

        mAgeMaxPicker = v.findViewById(R.id.age_max_picker);
        mAgeMaxPicker.setMin(18);

        mBackButton = v.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.fm.popBackStackImmediate();
            }
        });

        mNextButton = v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isGenderInterestValid()) {
                    mUser.setMaleInterest(mMaleCheckBox.isChecked());
                    mUser.setFemaleInterest(mFemaleCheckBox.isChecked());
                    mUser.setMinAge(mAgeMinPicker.getSelectedNumber());
                    mUser.setMaxAge(mAgeMaxPicker.getSelectedNumber());
                    updateUser();
                    Fragment fragment = RaceAndReligionFragment.newInstance(mUser);
                    MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        return v;
    }

    public boolean isGenderInterestValid(){
        if(!mMaleCheckBox.isChecked() && ! mFemaleCheckBox.isChecked()){
            mInvalidText.setText("Select genders of interest");
            return false;
        }

        return true;
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
