package b.android.pheramorregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserInfoAdditionalFragment extends Fragment {

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramorregistration.updated_user";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private RadioGroup mGenderRadioGroup;
    private RadioButton mSelectedGenderRadioButton;
    private Button mDOBButton;
    private TextView mInvalidText;
    private Button mBackButton;
    private Button mNextButton;


    private User mUser;
    private int mSelectedGenderId;
    private Date defaultDate;

    public static UserInfoAdditionalFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        UserInfoAdditionalFragment fragment = new UserInfoAdditionalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = (User) getArguments().getSerializable(ARGS_USER);
        Toast.makeText(getActivity(), mUser.getFullName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_user_info_additional, container, false);


        defaultDate = new Date();

        mGenderRadioGroup = v.findViewById(R.id.gender_radio_group);
        mSelectedGenderId = mGenderRadioGroup.getCheckedRadioButtonId();
        mSelectedGenderRadioButton = v.findViewById(mSelectedGenderId);

        mInvalidText = v.findViewById(R.id.invalid_text);

        mDOBButton = v.findViewById(R.id.dob_button);
        mDOBButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment dialog = DatePickerFragment.newInstance(defaultDate);
                FragmentManager fm = getFragmentManager();
                dialog.setTargetFragment(UserInfoAdditionalFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

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
                if(isDOBValid()) {

                    mUser.setGender(mSelectedGenderRadioButton.getText().toString());
                    updateUser();
                    Fragment fragment = InterestFragment.newInstance(mUser);
                    MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE', 'dd' 'MMM' 'yyyy");
            String stringDate = dateFormat.format(date);


            mUser.setDateOfBirth(date);
            mDOBButton.setText(stringDate);
        }
    }

    private void setDOB(){

    }

    private boolean isDOBValid(){
        if(mDOBButton.getText() == null){
            mInvalidText.setText("Please select Date of Birth");
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
