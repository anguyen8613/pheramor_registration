package b.android.pheramorregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import b.android.pheramorregistration.models.User;

public class ProfileScreenFragment extends Fragment{

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramorregistration.updated_user";

    private ImageView mPhoto;
    private TextView mAgeText;
    private TextView mNameText;

    private TextView mRaceText;
    private TextView mReligionText;
    private TextView mGenderText;
    private TextView mHeightText;

    private boolean mIsOpen = false;
    private ConstraintSet mLayout1, mLayout2;
    private ConstraintLayout mContraintLayout;

    private User mUser;

    public static ProfileScreenFragment newInstance(User user){

        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        ProfileScreenFragment fragment = new ProfileScreenFragment();
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

        View v = inflater.inflate(R.layout.fragment_profile_screen, container, false);



        Window w = getActivity().getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mPhoto = v.findViewById(R.id.photo);
        mPhoto.setImageBitmap(mUser.getProfilePicture());

        mNameText = v.findViewById(R.id.name_text);
        mNameText.setText(mUser.getFullName());

        mAgeText = v.findViewById(R.id.age_text);
        mAgeText.setText(Integer.toString(getAge()));

        mHeightText = v.findViewById(R.id.height_text);
        mHeightText.setText(Integer.toString(mUser.getHeight()));

        mRaceText = v.findViewById(R.id.race_text);
        mRaceText.setText(mUser.getRace());

        mReligionText = v.findViewById(R.id.religion_text);
        mReligionText.setText(mUser.getReligion());

        mGenderText = v.findViewById(R.id.gender_text);
        mGenderText.setText(mUser.getGender());


        mLayout1 = new ConstraintSet();
        mLayout2 = new ConstraintSet();

        mContraintLayout = v.findViewById(R.id.constraint_layout);
        mLayout1.clone(getActivity(), R.layout.fragment_profile_expanded);
        mLayout2.clone(mContraintLayout);

        mPhoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                if(!mIsOpen){
                    TransitionManager.beginDelayedTransition(mContraintLayout);
                    mLayout1.applyTo(mContraintLayout);
                    mIsOpen = !mIsOpen;
                }else{
                    TransitionManager.beginDelayedTransition(mContraintLayout);
                    mLayout2.applyTo(mContraintLayout);
                    mIsOpen = !mIsOpen;
                }
            }
        });

        Toast.makeText(getActivity(), "Click on Profile Picture", Toast.LENGTH_SHORT).show();
        return v;
    }

    private int getAge(){
        Calendar dob  = Calendar.getInstance();
        dob.setTime(mUser.getDateOfBirth());
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
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
