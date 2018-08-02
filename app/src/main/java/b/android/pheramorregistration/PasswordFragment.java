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

public class PasswordFragment extends Fragment{

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramoreregistration.updated_user";

    private EditText mPasswordEdit;
    private EditText mConfirmPasswordEdit;
    private Button mBackButton;
    private Button mNextButton;
    private TextView mInvalidText;

    private User mUser;

    public static PasswordFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        PasswordFragment fragment = new PasswordFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser = (User) getArguments().getSerializable(ARGS_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_password, container, false);


        mPasswordEdit = v.findViewById(R.id.full_name_edit);
        mConfirmPasswordEdit = v.findViewById(R.id.confirm_password_edit);
        mInvalidText = v.findViewById(R.id.invalid_text);

        mBackButton = v.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                MainActivity.fm.popBackStackImmediate();
            }
        });

        mNextButton = v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            ;

            @Override
            public void onClick(View v){
                if(isPasswordValid()) {
                    mUser.setPassword(mPasswordEdit.getText().toString());
                    updateUser(mUser);

                    UserInfoFragment fragment = UserInfoFragment.newInstance(mUser);
                    MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        return v;
    }

    private boolean isPasswordValid(){
        if(mPasswordEdit.getText().toString().length() < 8){
            mInvalidText.setText("Must be atleast 8 characters");
            return false;
        }
        if(!mPasswordEdit.getText().toString().equals(mConfirmPasswordEdit.getText().toString())){
            mInvalidText.setText("Passwords don't match");
            return false;
        }

        return true;
    }

    public void updateUser(User user){
        Intent data = new Intent();
        data.putExtra(EXTRA_UPDATED_USER, user);
        getActivity().setResult(Activity.RESULT_OK, data);
    }

    public static User parseUser(Intent result){
        return (User) result.getSerializableExtra(EXTRA_UPDATED_USER);
    }
}
