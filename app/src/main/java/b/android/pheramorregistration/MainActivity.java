package b.android.pheramorregistration;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_USER = 0;
    private static final int REQUEST_PASSWORD = 1;
    private static final int REQUEST_USER_INFO = 2;
    private static final int REQUEST_USER_INFO_ADDITIONAL = 3;
    private static final int REQUEST_INTEREST = 4;
    private static final int REQEUEST_RACE_RELIGION = 5;
    private static final int REQUEST_PICTURE_PICKER = 6;
    private User mUser;



    public static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();

        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = EmailFragment.newInstance(mUser);
            fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).add(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result){
        super.onActivityResult(requestCode, resultCode, result);
        if(resultCode == Activity.RESULT_OK) {

            switch(requestCode){
                case(REQUEST_USER):
                    mUser = EmailFragment.parseUser(result);
                case(REQUEST_PASSWORD):
                    mUser = PasswordFragment.parseUser(result);
                case(REQUEST_USER_INFO):
                    mUser = UserInfoFragment.parseUser(result);
                case(REQUEST_USER_INFO_ADDITIONAL):
                    mUser = UserInfoAdditionalFragment.parseUser(result);
                case(REQUEST_INTEREST):
                    mUser = InterestFragment.parseUser(result);
                case(REQEUEST_RACE_RELIGION):
                    mUser = RaceAndReligionFragment.parseUser(result);
                case(REQUEST_PICTURE_PICKER):
                    mUser = PicturePickerFragment.parseUser(result);



            }

        }
    }




}
