package com.smilena.wordchecker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import sessionManager.SessionManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    public String username;
    public String mainLanguage;
    public String langToLearn;
    public Double score;
    SessionManager session;
    private WordSelectionTask mAuthTask = null;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            mControlsView.setVisibility(View.VISIBLE);
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            session = new SessionManager(getApplicationContext());
            session.checkLogin();
            username = session.getUserDetails().get("name");
//            Intent intent = getIntent();
//            String value = intent.getStringExtra("json");


        try {
//            String value = RESTConnector.connectToHTTGet(String.format("%s%s", UrlKeeper.getUserInfo, username));
//                JSONObject jobj = new JSONObject(value);
//                //username = jobj.get("username").toString();
//                Log.e("usernameeeeee",username);
//                score = (double)jobj.get("score");
//                Log.e("scoreeee",""+jobj.get("score"));
//                mainLanguage = jobj.get("mainLanguage").toString();
//                langToLearn = jobj.get("langToLearn").toString();
                if (mAuthTask != null) {
                    return;
                } else {
                    mAuthTask = new WordSelectionTask(username);
                    mAuthTask.execute((Void) null);
                }

            //repeatWronWords = jobj.getBoolean("repeatWronWords");
        } catch (Exception e){
            Log.e("Exception", e.toString());
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(FullscreenActivity.this, MainActivity.class);
        myIntent.putExtra("name", username);
        FullscreenActivity.this.startActivity(myIntent);

    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }



    public class WordSelectionTask extends AsyncTask<Void, Void, Boolean> implements View.OnClickListener{
        String json;
        private  String mLangToLearn;
        private  String mMainLanguage;
        private final String mUsername;
        public  double mPoints;
        public HashMap<String, Boolean> words = new HashMap<String, Boolean>();


        WordSelectionTask(String username) {
//            mLangToLearn = langToLearn;
//            mMainLanguage = mainLang;
            mUsername = username;
//            mPoints = points;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String value = RESTConnector.connectToHTTGet(String.format("%s%s", UrlKeeper.getUserInfo, username));
            try {
                JSONObject jobj = new JSONObject(value);
                //username = jobj.get("username").toString();
                Log.e("usernameeeeee",username);
                mPoints = (double)jobj.get("score");
                Log.e("scoreeee",""+jobj.get("score"));
                mMainLanguage = jobj.get("mainLanguage").toString();
                mLangToLearn = jobj.get("langToLearn").toString();
            } catch (Exception e){
                Log.e("Exception in get user", e.toString());
            }

            String url = UrlKeeper.getWord+mUsername;
            String result = RESTConnector.connectToHTTGet(url);
            if (result != null) {
                json = result;
                return true;

            } else {
//                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            String mainLang = "";
            if (success) {
                try {
                    JSONObject jobj = new JSONObject(json);
                    mainLang = jobj.get("wordInMainLanguage").toString();

                    JSONObject current = (JSONObject) jobj.get("word");

                    ArrayList<String> arr = new ArrayList();
                    for (int i = 0; i < current.names().length(); i++) {
                        arr.add(current.names().get(i).toString());
                        words.put(current.names().get(i).toString(), (Boolean)current.get(current.names().get(i).toString()));
                    }

                    ((TextView) findViewById(R.id.fullscreen_content)).setText(mainLang);
                    ((Button) findViewById(R.id.button)).setText(arr.get(0));
                    ((Button) findViewById(R.id.button)).setOnClickListener(this);
                    ((Button) findViewById(R.id.button1)).setText(arr.get(1));
                    ((Button) findViewById(R.id.button1)).setOnClickListener(this);

                    ((Button) findViewById(R.id.button2)).setText(arr.get(2));
                    ((Button) findViewById(R.id.button2)).setOnClickListener(this);

                    ((Button) findViewById(R.id.button3)).setText(arr.get(3));
                    ((Button) findViewById(R.id.button3)).setOnClickListener(this);


                    //Log.e(current.get(arr.get(0)).toString(), "Resultttttttnfdgkjfdnd");

                }catch (Exception e){
                    Log.e("Exception", e.toString());
                }


            } else {
                //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.fullscreen_content)).setText("");
                ((Button) findViewById(R.id.button)).setText("");
                ((Button) findViewById(R.id.button1)).setText("");
                ((Button) findViewById(R.id.button2)).setText("");
                ((Button) findViewById(R.id.button3)).setText("");

            }
        }

        @Override
        public void onClick(View v) {
            if(words.get(((Button)v).getText()) == true){
                mPoints = mPoints + 10;
                updateUser(mPoints);
                Toast.makeText(getApplicationContext(), "Вярно! :) ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Грешно! :(", Toast.LENGTH_SHORT).show();
            }
            mAuthTask = new WordSelectionTask(username);
            mAuthTask.execute((Void) null);

        }

        @Override
        protected void onCancelled() {

        }

        void updateUser(double score){

            UserUpdatingTask uptTask = new UserUpdatingTask(username,score);
            uptTask.execute((Void) null);
        }
    }

    public class UserUpdatingTask extends AsyncTask<Void, Void, Boolean>{

        private final String mUsername;
        private final Double mPoints;


        UserUpdatingTask(String username, double points) {
            mUsername = username;
            mPoints = points;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url = UrlKeeper.putUpdate;
            JSONObject json = new JSONObject();
            try {
                json.put("username", mUsername);
                json.put("score", mPoints);
            }catch (Exception e){
                Log.e("JSON creation exception", e.getMessage());
            }
            String result = RESTConnector.connectToHTTPut(url,json);
            if (result != null) {
                return true;

            } else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
            Toast.makeText(getApplicationContext(), "Точките ти са "+mPoints, Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "Грешка, моля опитайте отново", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onCancelled() {

        }

    }


}
