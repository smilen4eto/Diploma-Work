package com.smilena.wordchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    public Double score;
    public String mainLanguage;
    public String langToLearn;
    public String username;
    public String name;
    // Session Manager Class
    SessionManager session;
    private UserSelectionTask mAuthTask = null;
    private UserInformationTask mInfoTask = null;
    String json;
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        // Session class instance
        session = new SessionManager(getApplicationContext());
        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();
        username = session.getUserDetails().get("name");
        String name = intent.getStringExtra("name");
        if (mInfoTask != null) {
            return;
        } else {
            mInfoTask = new UserInformationTask(username);
            mInfoTask.execute((Void) null);
        }
//        try {
//            JSONObject jobj = new JSONObject(value);
//            name = jobj.get("name").toString();
//            score = jobj.getDouble("score");
//            //username = jobj.getString("username");
//        } catch (Exception e){
//            Log.e("Exception", e.toString());
//        }
        super.onCreate(Bundle.EMPTY);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);


        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), StatisticActivity.class);
                startActivity(i);

            }
        });

        Spinner spinnerMainLang = (Spinner) findViewById(R.id.spinnerMainLang);
        Spinner spinnerLangToLearn = (Spinner) findViewById(R.id.spinnerLangToLearn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.langs_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMainLang.setAdapter(adapter);

        spinnerLangToLearn.setAdapter(adapter);

        spinnerLangToLearn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langToLearn = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Моля изберете език", Toast.LENGTH_LONG).show();

            }
        });
        spinnerMainLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainLanguage = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Моля изберете език", Toast.LENGTH_LONG).show();
            }
        });




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
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                session = null;
                finish();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                return true;
            }
        });
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                i.putExtra("username", username);
                i.putExtra("name", name);
                startActivity(i);
                return true;
            }
        });
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
            if (mMainLanguage.equals(mLangToLearn)){
                return false;
            }
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
                json = result;
                    return true;

            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                if (((TextView) findViewById(R.id.textViewPreferences)) != null) {
                    ((TextView) findViewById(R.id.textViewPreferences)).setText("Натисни Старт бутона");
                    floatingActionButton1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Start the game /
                            Intent myIntent = new Intent(MainActivity.this, FullscreenActivity.class);
                            myIntent.putExtra("json", json); //Optional parameters
                            MainActivity.this.startActivity(myIntent);

                        }
                    });
                }

            } else {
                ((TextView) findViewById(R.id.textViewPreferences)).setText("Избери различни езици");
                floatingActionButton1.setOnClickListener(null);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public class UserInformationTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        public double points;
        public String mName;
        public String mMainLanguage;
        public String mLangToLearn;

        UserInformationTask(String username) {
            mUsername = username;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String value = RESTConnector.connectToHTTGet(String.format("%s%s", UrlKeeper.getUserInfo, username));
            try {
                JSONObject jobj = new JSONObject(value);
                //username = jobj.get("username").toString();
                Log.e("usernameeeeee",username);
                name = jobj.getString("name");
                mName = name;
                points = (double)jobj.get("score");
                Log.e("scoreeee",""+jobj.get("score"));
                mMainLanguage = jobj.get("mainLanguage").toString();
                mLangToLearn = jobj.get("langToLearn").toString();
                return true;
            } catch (Exception e){
                Log.e("Exception in get user", e.toString());
                return false;
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mInfoTask = null;

            if (success) {
                TextView welcome = (TextView) findViewById(R.id.textView);
                welcome.setText("Добре дошли, " + name + "!!!");
                TextView textViewScore = (TextView)findViewById(R.id.textViewScore);
                textViewScore.setText("Точките Ви са: " + points);
                if (mMainLanguage != null && mLangToLearn != null && !mMainLanguage.equals(mLangToLearn)) {
                    Spinner spinnerMainLang = (Spinner) findViewById(R.id.spinnerMainLang);
                    Spinner spinnerLangToLearn = (Spinner) findViewById(R.id.spinnerLangToLearn);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getBaseContext(),
                            R.array.langs_array, android.R.layout.simple_spinner_item);
                    spinnerMainLang.setSelection(adapter.getPosition(mMainLanguage));
                    spinnerLangToLearn.setSelection(adapter.getPosition(mLangToLearn));
                    floatingActionButton1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //Start the game /
                            Intent myIntent = new Intent(MainActivity.this, FullscreenActivity.class);
                            myIntent.putExtra("name", username); //Optional parameters
                            MainActivity.this.startActivity(myIntent);

                        }
                });
                }

            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mInfoTask = null;
        }
    }

}
