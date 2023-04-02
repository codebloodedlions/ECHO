package com.example.echo.api;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.echo.api.model.eventapi.Root;

import java.util.Arrays;

// 0-2 have different data verbosity
public class EventGrabber {
    public Object[] getEventData(AssetManager assetManager){
        Object[] temp = new Object[3];

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

        temp[0] = divCityArr;
        temp[1] = divEventArr;
        temp[2] = divEventDescArr;

        return temp;
    }
}
