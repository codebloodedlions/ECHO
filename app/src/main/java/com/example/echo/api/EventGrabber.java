package com.example.echo.api;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.echo.api.model.eventapi.Root;

import java.util.ArrayList;
import java.util.Arrays;

// 0-2 have different data verbosity
public class EventGrabber {
    public static ArrayList<String[]> getEventData(AssetManager assetManager){
        ArrayList<String[]> temp = new ArrayList<>();

        String divCityStr = "";
        String[] divCityArr = {};

        String divEventStr = "";
        String[] divEventArr = {};

        String divEventDescStr = "";
        String[] divEventDescArr = {};

        Root getDivCities = EventApiConn.touchGPTAPI(assetManager, "What are 10 of the most diverse cities in the united states in a comma separated list with no newlines or whitespace");
        divCityStr = getDivCities.choices.get(0).getText().strip();
        divCityArr = divCityStr.split(",");
        System.out.println(Arrays.toString(divCityArr));

        Root getDivEvents = EventApiConn.touchGPTAPI(assetManager, "Based on  " + Arrays.toString(divCityArr) + " what are 10 diversity events and their dates in parenthesis where people have talents related to sustainability and environment in a comma separated list with no newlines or whitespace");
        divEventStr = getDivEvents.choices.get(0).getText().strip();
        divEventArr = divEventStr.split(",");
        System.out.println(Arrays.toString(divEventArr));

        Root getDivDesc = EventApiConn.touchGPTAPI(assetManager, "provide only a description for each event in " + Arrays.toString(divEventArr) + " in a comma separated list with no newlines or whitespace");
        divEventDescStr = getDivDesc.choices.get(0).getText().strip();
        divEventDescArr = divEventDescStr.split(",");
        System.out.println(Arrays.toString(divEventDescArr));

        temp.add(divCityArr);
        temp.add(divEventArr);
        temp.add(divEventDescArr);

        return temp;
    }
}
