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

import b.android.pheramorregistration.models.User;

public class EmailFragment extends Fragment {

    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramoreregistration.updated_user";

    private Button mNextButton;
    private EditText mEmailEdit;
    private TextView mInvalidText;

    private User mUser;

    public static EmailFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        EmailFragment fragment = new EmailFragment();
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

        View v = inflater.inflate(R.layout.fragment_email, container, false);



        mEmailEdit = v.findViewById(R.id.email_edit);
        mInvalidText = v.findViewById(R.id.invalid_text);

        mNextButton = v.findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(!isValidEmail(mEmailEdit.getText())){
                    mInvalidText.setText("Enter a valid email");
                }else {
                    mUser.setEmail(mEmailEdit.getText().toString());
                    updateUser(mUser);
                    Fragment fragment = PasswordFragment.newInstance(mUser);
                    MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        return v;

    }


    public  boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
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
