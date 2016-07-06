package com.smilena.wordchecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Smilena on 6/29/2016.
 */

public class StatisticActivity extends AppCompatActivity {
    StatisticInformationTask mInfoTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        if (mInfoTask != null) {
            return;
        } else {
            mInfoTask = new StatisticInformationTask();
            mInfoTask.execute((Void) null);
        }

    }
    public class StatisticInformationTask extends AsyncTask<Void, Void, Boolean> {
        JSONArray jsonArray;

        StatisticInformationTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String result = RESTConnector.connectToHTTGet(UrlKeeper.getStatistics);
            if (result != null) {

                try {

                    jsonArray = new JSONArray(result);

                    //((TextView)findViewById(R.id.textView2)).setText(jobj.get("second").toString());
                    //((TextView)findViewById(R.id.textView3)).setText(jobj.get("third").toString());

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
            mInfoTask = null;

            if (success) {
                try {
                    String firstName = ((JSONObject)jsonArray.get(0)).get("username").toString();
                    Double firstScore = ((JSONObject)jsonArray.get(0)).getDouble("score");
                    String secondName = ((JSONObject)jsonArray.get(1)).get("username").toString();
                    Double secondScore = ((JSONObject)jsonArray.get(1)).getDouble("score");
                    String thirdName = ((JSONObject)jsonArray.get(2)).get("username").toString();
                    Double thirdScore = ((JSONObject)jsonArray.get(2)).getDouble("score");
                    ((TextView)findViewById(R.id.textView1)).setText("1.    " + firstName +  " - " + firstScore);
                    ((TextView)findViewById(R.id.textView2)).setText("2.    " +secondName +  " - " + secondScore);
                    ((TextView)findViewById(R.id.textView3)).setText("3.    " +thirdName +  " - " + thirdScore);
                } catch (JSONException e) {
                    e.printStackTrace();
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
