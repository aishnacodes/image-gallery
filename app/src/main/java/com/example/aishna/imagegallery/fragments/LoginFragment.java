package com.example.aishna.imagegallery.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aishna.imagegallery.R;
import com.example.aishna.imagegallery.activities.ImageActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment {
    @Bind(R.id.editText_username)
    protected EditText mUsername;
    @Bind(R.id.editText_password)
    protected EditText mPassword;
    @Bind(R.id.button_login)
    protected Button mLoginButton;
    @Bind(R.id.button_signup)
    protected Button mSignUp;
    private Firebase mFirebase;
    private ImageActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(mActivity);

        mFirebase = new Firebase("https://imagegallery.firebaseio.com");
    }

    @OnClick(R.id.button_signup)
    public void onSignupClick() {
        mFirebase.createUser(mUsername.getText().toString(), mPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
            }
        });
        Toast.makeText(mActivity.getApplicationContext(), R.string.signup_success, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_login)
    public void onLogin() {
        Fragment fragment = new ImageSearchFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (ImageActivity) context;
    }
}