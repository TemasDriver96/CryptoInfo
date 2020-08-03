package com.example.user.cryptoinfo;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 05/04/2018.
 */

public class netChecker {

    public static boolean networkAvailability (Context contxt){
        return((ConnectivityManager)contxt.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static String exeGet(String getURL, String urlParams) {
        URL url;
        HttpURLConnection connect = null;

        try {
            url = new URL(getURL);
            connect = (HttpURLConnection)url.openConnection();
            connect.setRequestProperty("content-type", "application/json; charset=utf-8");
            connect.setRequestProperty("Content-Langauge", "en-US");
            connect.setUseCaches(false);
            connect.setDoInput(true);
            connect.setDoOutput(false);

            InputStream is;
            int status = connect.getResponseCode();

            if(status != HttpURLConnection.HTTP_OK) {
                is = connect.getErrorStream();
            } else {
                is = connect.getInputStream();
            }

            BufferedReader buffrd = new BufferedReader(new InputStreamReader(is));
            String ln;
            StringBuffer res = new StringBuffer();
            while((ln = buffrd.readLine()) != null) {
                res.append(ln);
                res.append(System.getProperty("line.separator"));
            }
            buffrd.close();
            return res.toString();

        } catch(Exception e) {
            return null;
        } finally {
            if(connect != null) {
                connect.disconnect();
            }
        }
    }
}
