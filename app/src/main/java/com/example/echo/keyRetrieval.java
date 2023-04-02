package com.example.echo;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class keyRetrieval {
    public static String getKey(AssetManager assetManager){
        return readAPIKey(assetManager);
    }

    private static String readAPIKey(AssetManager keyFile){
        String key = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(keyFile.open("key.txt")));

            key = bufferedReader.readLine();
            Log.i("[FILE-HANDLER]", ">> API_KEY=" + key);
        }
        catch (Exception fileEX){
            fileEX.printStackTrace();
            System.exit(401);
        }

        return key;
    }
}
