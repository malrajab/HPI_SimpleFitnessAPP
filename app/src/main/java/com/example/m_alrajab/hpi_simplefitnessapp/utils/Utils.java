package com.example.m_alrajab.hpi_simplefitnessapp.utils;

/**
 * Created by m_alrajab on 12/27/16.
 */

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.m_alrajab.hpi_simplefitnessapp.BuildConfig;
import com.facebook.stetho.Stetho;



/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    private static String LOG_TAG = Utils.class.getSimpleName();
    public static boolean showPercent = true;


    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    public static void setStethoWatch(Context context) {
        if(BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(context)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                            .build()
            );
        }
    }

    // helping method to get the view context parents for the fragment manager
    public static AppCompatActivity getActivity(View v) {
        Context c = v.getContext();
        while (c instanceof ContextWrapper) {
            if (c instanceof AppCompatActivity) return (AppCompatActivity)c;
            c = ((ContextWrapper)c).getBaseContext();
        }
        return null;
    }



}
