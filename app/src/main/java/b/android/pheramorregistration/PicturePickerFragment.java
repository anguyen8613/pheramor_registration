package b.android.pheramorregistration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PicturePickerFragment extends Fragment {


    private static final String ARGS_USER = "user";
    private static final String EXTRA_UPDATED_USER = "b.android.pheramorregistration.updated_user";
    private static final String TAG = "Response";

    private final int IMG_REQUEST = 1;
    private final int ACTION_REQUEST_GALLERY = 2;
    private final int ACTION_REQUEST_CAMERA = 3;

    private Button mBackButton;
    private Button mNextButton;
    private ImageView mImageView;
    private Button mSelectImageButton;
    private Bitmap mBitmap;
    private User mUser;



    public static PicturePickerFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_USER, user);
        PicturePickerFragment fragment = new PicturePickerFragment();
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

        View v = inflater.inflate(R.layout.fragment_picture_picker, container, false);

        mImageView = v.findViewById(R.id.imageView);
        mSelectImageButton = v.findViewById(R.id.select_image);

        mSelectImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
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
                mUser.setProfilePicture(mBitmap);
                if(isPicturePresent()) {
                    updateUser();
                    postRequest();
                    Fragment fragment = ProfileScreenFragment.newInstance(mUser);
                    MainActivity.fm.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right).replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                }
            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_REQUEST && resultCode == Activity.RESULT_OK && data != null){
            Uri path = data.getData();
            Log.d(TAG, "onActivityResult: " + path.getPath());

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                    mImageView.setImageBitmap(mBitmap);
                    mImageView.setVisibility(View.VISIBLE);
                    mUser.setProfilePicture(mBitmap);

                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    }

    private void openGallery(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }


    public boolean isPicturePresent(){
        if(mUser.getProfilePicture() == null){
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



    public void getRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL = "https://external.dev.pheramor.com/";
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.toString());
                    }
                }

        );


        requestQueue.add(objectRequest);
    }

    private void postRequest(){
        String URL = "https://external.dev.pheramor.com/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String>  params = new HashMap<String, String>();

                params.put("EMAIL", mUser.getEmail());
                params.put("PASSWORD", mUser.getPassword());
                params.put("FULLNAME", mUser.getFullName());
                params.put("ZIPCODE", Integer.toString(mUser.getZipCode()));
                params.put("HEIGHT", Integer.toString(mUser.getHeight()));
                params.put("GENDER", mUser.getGender());
                params.put("DOB", mUser.getDateOfBirth().toString());
                params.put("MALE_INTEREST", Boolean.toString(mUser.isMaleInterest()));
                params.put("FEMALE_INTEREST", Boolean.toString(mUser.isFemaleInterest()));
                params.put("MIN_AGE", Integer.toString(mUser.getMinAge()));
                params.put("MAX_AGE", Integer.toString(mUser.getMaxAge()));
                params.put("RACE", mUser.getRace());
                params.put("RELIGION", mUser.getReligion());
                params.put("PROFILE_PICTURE", mUser.profilePicture.toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
