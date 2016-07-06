package com.smilena.wordchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;
import java.util.Arrays;

import sessionManager.SessionManager;

public class RegisterActivity extends AppCompatActivity {
    public EditText mEmailView, mPasswordView, mNameView, mUsernameView, mRepeatPasswordView;
    private UserRegisterTask mAuthTask = null;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        String value = intent.getStringExtra("email");
        mEmailView = (EditText) findViewById(R.id.editTextEmail);
        mEmailView.setText(value);
        mNameView = ((EditText) findViewById(R.id.editText));
        mUsernameView = ((EditText) findViewById(R.id.editTextUsername));
        mPasswordView = ((EditText) findViewById(R.id.editTextPassword));
        mRepeatPasswordView = ((EditText)findViewById(R.id.editTextRepeatPassword));
        final Button registerBtn = (Button) findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendInformationForRegister();
            }
        });
        session = new SessionManager(getApplicationContext());
    }


    private void sendInformationForRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNameView.setError(null);
        mPasswordView.setError(null);
        mRepeatPasswordView.setError(null);
        mEmailView.setError(null);
        mUsernameView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mNameView.getText().toString();
        String username = mUsernameView.getText().toString();
        String repeatPassword = mRepeatPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Паролата е твърде кратка");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!doPasswordsMatch(password, repeatPassword)) {
            mRepeatPasswordView.setError("Паролите не съвпадат");
            focusView = mRepeatPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError("Полето е задължително");
            focusView = mNameView;
            cancel = true;
        } else if (!isNameValid(name)) {
            mNameView.setError("Името трябва да е повече от 3 символа");
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Полето е задължително");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("Невалидна поща");
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("Полето е задължително");
            focusView = mUsernameView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new RegisterActivity.UserRegisterTask(email, password, name, username);
            mAuthTask.execute((Void) null);
            ;
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

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String mUsername;


        UserRegisterTask(String email, String password, String name, String username) {
            mEmail = email;
            mPassword = password;
            mName = name;
            mUsername = username;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("username", mUsername);
                jsonParam.put("name", mName);
                jsonParam.put("email", mEmail);
                jsonParam.put("password", mPassword);
                jsonParam.put("score", 0);
                jsonParam.put("repeatWronWords", false);
                jsonParam.put("mainLanguage", "EN");
                jsonParam.put("langToLearn", "EN");
            } catch (Exception e){
                Log.e("JSON creation exception", e.getMessage());
            }
            String url = UrlKeeper.postRegister;
            String result = RESTConnector.connectToHTTPost(url, jsonParam);
            if(result != null){
                session.createLoginSession(mUsername, mEmail);
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                myIntent.putExtra("name", mUsername); //Optional parameters
                //RegisterActivity.this.startActivity(myIntent);
                startActivity(myIntent);
                finish();
                return true;
            }
            return false;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
