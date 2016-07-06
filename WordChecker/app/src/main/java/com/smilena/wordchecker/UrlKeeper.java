package com.smilena.wordchecker;

/**
 * Created by Smilena on 6/19/2016.
 */
public class UrlKeeper {
    public static final String mainUrl = "http://192.168.0.106:8181/gs-accessing-mongodb-data-rest";
    public static final String postLogin = mainUrl + "/user/signin";
    public static final String postRegister = mainUrl + "/user/register";
    public static final String putUpdate = mainUrl + "/user/";
    public static final String getWord = mainUrl + "/word/";
    public static final String putUpdateSettings = mainUrl + "/user/settings/";
    public static final String getStatistics = mainUrl + "/user/3";
    public static final String getUserInfo = mainUrl + "/user/name/";

}
