package com.neolink.ahoralotengo.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;

import com.neolink.ahoralotengo.data.dbHandler;

public class GeneralUtils {
    static Context mContext;
    private String apiurl = "";
    private dbHandler dba;

    public GeneralUtils(Context mContext){
        this.mContext = mContext;
        dba = new dbHandler(mContext, null);
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    public void setRestServer(String prefix){
        switch (prefix) {
            case "local":
                apiurl = "http://testing_url/";
                break;
            case "production":
                apiurl = "https://production_url/";
                break;
            case "development":
                apiurl = "http://development_url/";
            default:
                break;
        }
    }

    public String setUniqueURL(){
        return "&m=" + String.valueOf(SystemClock.currentThreadTimeMillis());
    }

    public String getUrlTarget(String requested) {
        return "target_action?parameter=" + requested + setUniqueURL();
    }

    public String getParallelUrlTarget(String requested) {
        return "target_action?parameter=" + requested + setUniqueURL();
    }

    public String getBaseUrl() {
        return apiurl;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void displayAlert(String tit, String mes){
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(tit);
        alertDialogBuilder
                .setMessage(mes)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                    }
                });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
