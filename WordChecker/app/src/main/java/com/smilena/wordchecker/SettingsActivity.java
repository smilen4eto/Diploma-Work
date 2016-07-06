package com.smilena.wordchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Arrays;

import sessionManager.SessionManager;

/**
 * Created by Smilena on 6/29/2016.
 */
public class SettingsActivity extends AppCompatActivity {
    private UserSaveSettingsTask mAuthTask = null;
    public  EditText mTextName;
    public  EditText mPasswordView;
    public  EditText mRepeatPasswordView;
    public SessionManager session;
    public String username;
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        name = intent.getStringExtra("name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        //Log.e("fgdfhghgfhg", username+name);
        mTextName = (EditText) findViewById(R.id.textName);
        mPasswordView = (EditText) findViewById(R.id.textPassword);
        mRepeatPasswordView = (EditText) findViewById(R.id.textRepeatPassword);
        mTextName.setText(name.toString());
        session = new SessionManager(getApplicationContext());
        Button btnSave = (Button) findViewById(R.id.button4);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mTextName.setError(null);
        mPasswordView.setError(null);
        mRepeatPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String name = mTextName.getText().toString();
        String password = mPasswordView.getText().toString();
        Log.e("passswoooordddd", password);
        String repeatPassword = mRepeatPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Паролата е твърде кратка");
            focusView = mPasswordView;
            cancel = true;
        }

        if(!doPasswordsMatch(password,repeatPassword)){
            mRepeatPasswordView.setError("Паролите не съвпадат");
            focusView = mRepeatPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            mTextName.setError("Полето е задължително");
            focusView = mTextName;
            cancel = true;
        } else if (!isNameValid(name)) {
            mTextName.setError("Името трябва да е повече от 3 символа");
            focusView = mTextName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserSaveSettingsTask(name, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isNameValid(String name) {
        return name.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean doPasswordsMatch(String password, String repeatPassword) {
        return password.contentEquals(repeatPassword);
    }


    public class UserSaveSettingsTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mPassword;

        UserSaveSettingsTask(String name, String password) {
            mName = name;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = UrlKeeper.putUpdateSettings;
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("username", username);
                jsonParam.put("name", mName);
                Log.e("mPAssswooooord", mPassword);
                Log.e("mPAssswooooord encoded", Arrays.toString(Base64.encode(mPassword.getBytes(), Base64.NO_WRAP)));
                jsonParam.put("password", mPassword);
//                jsonParam.put("password", Arrays.toString(Base64.encode(mPassword.getBytes(), Base64.NO_WRAP)));
            } catch (Exception e) {
                Log.e("JSON creation exception", e.getMessage());
            }
            String result = RESTConnector.connectToHTTPut(url, jsonParam);
            if (result != null) {

                try {
                    JSONObject jobj = new JSONObject(result);
                    session.createLoginSession(username, mName);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("name", username);
                    startActivity(i);
                    finish();
                    return true;

                } catch (Exception e) {
                    Log.e("Json parsing exception", e.getMessage());
                    return false;

                }


            } else {
                Toast.makeText(getApplicationContext(), "Опитайте отново", Toast.LENGTH_SHORT).show();
                return false;
            }

        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError("Грешна парола");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    }

