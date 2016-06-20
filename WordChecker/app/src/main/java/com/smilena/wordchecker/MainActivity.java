package com.smilena.wordchecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import sessionManager.SessionManager;

public class MainActivity extends AppCompatActivity {
    String name = null;
    Double score = 0d;
    String username = "";
    public String mainLanguage;
    public String langToLearn;
    // Session Manager Class
    SessionManager session;

    // Button Logout
    Button btnLogout;

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        // Session class instance
        session = new SessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();
        String value = intent.getStringExtra("name");
        try {
            JSONObject jobj = new JSONObject(value);
            name = jobj.get("name").toString();
            score = jobj.getDouble("score");
            username = jobj.getString("username");
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
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("repeatWronWords", true);
                    jsonObject.put("mainLanguage", mainLanguage);
                    jsonObject.put("langToLearn", langToLearn);
                    String result = RESTConnector.connectToHTTPut(UrlKeeper.putUpdate + username,jsonObject);
                    if (result != null){
                        //Toast.makeText(View.getContext(), "Lang selected"+parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startGame(){

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

}
