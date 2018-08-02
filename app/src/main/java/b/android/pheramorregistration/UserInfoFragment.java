package b.android.pheramorregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoFragment extends Fragment {

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramorregistration.updated_user";

    private EditText mFullNameEdit;
    private EditText mZipCodeEdit;
    private EditText mHeightEdit;
    private TextView mInvalidText;

    private Button mBackButton;
    private Button mNextButton;

    private User mUser;

    public static UserInfoFragment newInstance(User user){

        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);

        UserInfoFragment fragment  = new UserInfoFragment();
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

        View v = inflater.inflate(R.layout.fragment_user_info, container, false);

        mFullNameEdit = v.findViewById(R.id.full_name_edit);
        mZipCodeEdit = v.findViewById(R.id.zip_code_edit);
        mHeightEdit = v.findViewById(R.id.height_edit);
        mInvalidText = v.findViewById(R.id.invalid_text);

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
                if(isUserInfoValid()) {
                    mUser.setFullName(mFullNameEdit.getText().toString());
                    mUser.setZipCode(Integer.parseInt(mZipCodeEdit.getText().toString()));
                    mUser.setHeight(Integer.parseInt(mHeightEdit.getText().toString()));
                    updateUser();
                    Fragment fragment = UserInfoAdditionalFragment.newInstance(mUser);
                    MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }

        });

        return v;
    }

    public boolean isUserInfoValid(){
        if(mFullNameEdit.getText().toString().length() == 0){
            mInvalidText.setText("Enter a name");
            return false;
        }
        if(!(mZipCodeEdit.getText().toString().length() == 5)) {
            mInvalidText.setText("Enter a 5 digit zip code");
            return false;
        }

        if(mHeightEdit.getText().toString().length() == 0) {
            mInvalidText.setText("Enter height");
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
