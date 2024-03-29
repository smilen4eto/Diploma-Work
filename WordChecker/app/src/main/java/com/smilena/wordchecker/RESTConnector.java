package com.smilena.wordchecker;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Smilena on 6/16/2016.
 */
public class RESTConnector
{
    public static HttpURLConnection httpcon;
    public static String connectToHTTGet(String url){
        DataOutputStream printout;
        String result = null;
        try {
            httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
            httpcon.setRequestMethod("GET");
            httpcon.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            Log.e("Resulttt", result);
            return result;
        }
        catch(Exception e){
            Log.e("Exception in connection", e.toString());
            return null;
        }

    }
    public static String connectToHTTPost(String url, JSONObject jsonParam ){
        DataOutputStream printout;
        String result = null;
        try {
            httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            Log.e("jsooon",jsonParam.toString());
            writer.write(jsonParam.toString());
            writer.close();
            os.flush();
            os.close();

            //Read

            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            Log.e("Resulttt", result);
            return result;
        }
        catch(Exception e){
            Log.e("Exception in connection", e.toString());
            return null;
        }
    }


    public static String connectToHTTPut(String url, JSONObject jsonParam ){
            DataOutputStream printout;
        String result = null;
        try {
            httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestMethod("PUT");
            httpcon.connect();
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            Log.e("json",jsonParam.toString());
            writer.write(jsonParam.toString());
            writer.close();
            os.flush();
            os.close();

            //Read

            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();
            Log.e("Result", result);
            return result;
        }
        catch(Exception e){
            Log.e("Exception in connection", e.toString());
            return null;
        }
        //return null;
    }

}
