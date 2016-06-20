package com.smilena.wordchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity  {
    private EditText mEmailView, mPasswordView, mNameView, mUsernameView;
    private UserRegisterTask mAuthTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        String value = intent.getStringExtra("email");
        mEmailView = (EditText) findViewById(R.id.editTextEmail);
        mEmailView.setText(value);
        final Button registerBtn = (Button) findViewById(R.id.btnRegister);
            registerBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sendInformationForRegister();
                }
            });
    }

    public void sendInformationForRegister() {
        if (mAuthTask != null) {
            return;
        }
        mNameView = ((EditText) findViewById(R.id.editText));
        mUsernameView = ((EditText) findViewById(R.id.editTextUsername));
        mPasswordView = ((EditText) findViewById(R.id.editTextPassword));
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mNameView.getText().toString();
        String username = mUsernameView.getText().toString();
        mAuthTask = new RegisterActivity.UserRegisterTask(email, password, name, username);
        mAuthTask.execute((Void) null);
        }
// --------------- To be implemented
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
            } catch (Exception e){
                Log.e("JSON creation exception", e.getMessage());
            }
            String url = UrlKeeper.postRegister;
            String result = RESTConnector.connectToHTTPost(url, jsonParam);
            if(result != null){
                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                myIntent.putExtra("name", result.toString()); //Optional parameters
                RegisterActivity.this.startActivity(myIntent);
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
