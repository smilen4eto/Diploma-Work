package com.smilena.wordchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import sessionManager.SessionManager;

public class MainActivity extends AppCompatActivity {
    String name;
    Double score = 0d;
    public String mainLanguage;
    public String langToLearn;
    public String username = "";
    // Session Manager Class
    SessionManager session;
    private UserSelectionTask mAuthTask = null;

    // Button Logout
    Button btnLogout;
    //public TextView textPreferences = (TextView) findViewById(R.id.textViewPreferences);

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        // Session class instance
        session = new SessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();
        username = session.getUserDetails().get("name");
        String value = intent.getStringExtra("name");
        try {
            JSONObject jobj = new JSONObject(value);
            name = jobj.get("name").toString();
            score = jobj.getDouble("score");
            //username = jobj.getString("username");
        } catch (Exception e){
            Log.e("Exception", e.toString());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView welcome = (TextView) findViewById(R.id.textView);
        welcome.setText("Welcome, " + name + "!!!");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start the game /
                Intent myIntent = new Intent(MainActivity.this, FullscreenActivity.class);
                //myIntent.putExtra("email", mEmail); //Optional parameters
                MainActivity.this.startActivity(myIntent);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO make statistics view


            }
        });

        Spinner spinnerMainLang = (Spinner) findViewById(R.id.spinnerMainLang);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.langs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMainLang.setAdapter(adapter);
        Spinner spinnerLangToLearn = (Spinner) findViewById(R.id.spinnerLangToLearn);
        spinnerLangToLearn.setAdapter(adapter);

        spinnerLangToLearn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //parent.getItemAtPosition(position);
                //Toast.makeText(parent.getContext(), "Lang selected"+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                //langToLearn = parent.getItemAtPosition(position);
                langToLearn = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Please select language", Toast.LENGTH_LONG).show();

            }
        });
        spinnerMainLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //parent.getItemAtPosition(position);
                //Toast.makeText(parent.getContext(), "Lang selected"+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                mainLanguage = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Please select language", Toast.LENGTH_LONG).show();
            }
        });

        TextView textViewScore = (TextView)findViewById(R.id.textViewScore);
        textViewScore.setText("Your score is " + score);


        Button saveSettings = (Button) findViewById(R.id.btnSave);
        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }
    public void startGame(){
        if (mAuthTask != null) {
            return;
        } else {
            mAuthTask = new UserSelectionTask(langToLearn, mainLanguage);
            mAuthTask.execute((Void) null);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }


    public class UserSelectionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLangToLearn;
        private final String mMainLanguage;

        UserSelectionTask(String langToLearn, String mainLang) {
            mLangToLearn = langToLearn;
            mMainLanguage = mainLang;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = UrlKeeper.putUpdate+"/"+username;
            JSONObject jsonParam = new JSONObject();
            try {
                jsonParam.put("repeatWronWords", true);
                jsonParam.put("mainLanguage", mMainLanguage);
                jsonParam.put("langToLearn", mLangToLearn);
            } catch (Exception e) {
                Log.e("JSON creation exception", e.getMessage());
            }
            String result = RESTConnector.connectToHTTPut(url, jsonParam);
            if (result != null) {
                  //Toast.makeText(getApplicationContext(), "Preferences saved", Toast.LENGTH_SHORT).show();
                    return true;

//                try {
//                    //JSONObject jobj = new JSONObject(result);
//                    Toast.makeText(getApplicationContext(), jobj.get("mainLanguage").toString(), Toast.LENGTH_SHORT).show();
//                    //session.createLoginSession(jobj.get("username").toString(), mEmail);
//                    // Staring MainActivity
//                    //Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                    //i.putExtra("name", result.toString());
//                    //startActivity(i);
//                    //finish();
//
//                    return true;
//
//                } catch (Exception e) {
//                    Log.e("Json parsing exception", e.getMessage());
//                    return false;
//
//                }


            } else {
//                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
//                myIntent.putExtra("email", mEmail); //Optional parameters
//                LoginActivity.this.startActivity(myIntent);
//                // TODO: register the new account here.
//                return true;
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                if (((TextView) findViewById(R.id.textViewPreferences)) != null) {
                    ((TextView) findViewById(R.id.textViewPreferences)).setText("Click the Start button");
                }
                //finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                //mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }


}
