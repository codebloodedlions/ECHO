package com.example.echo;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.echo.db.model.Candidate;
import com.example.echo.db.model.Event;
import com.example.echo.db.model.Jobs;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Util {
    // convert json data in txt file to map
    // 0 for candidate data, 1 for event data
    public static List<Candidate> candiateDataToList(AssetManager assetManager) throws IOException {
        InputStream candidateInput = assetManager.open("candidate_data.txt");

        ObjectMapper objectMapper = new ObjectMapper();

        // candidate json to data structure
        List<Candidate> CandidateData = Arrays.asList(objectMapper.readValue(candidateInput, Candidate[].class));
        candidateInput.close();

        for(int i = 0; i < CandidateData.size(); i++) {
            Log.i("UTIL-CANDIDATE-INF", String.valueOf(CandidateData.get(i).email));
        }

        return CandidateData;
    }

    public static List<Event> eventDataToList(AssetManager assetManager) throws IOException {
        InputStream eventInput = assetManager.open("event_data.txt");

        ObjectMapper objectMapper = new ObjectMapper();

        List<Event> EventData = Arrays.asList(objectMapper.readValue(eventInput, Event[].class));
        eventInput.close();

        for(int i = 0; i < EventData.size(); i++) {
            Log.i("UTIL-INF-EVENT", String.valueOf(EventData.get(i).event_name));
        }

        return EventData;
    }

    public static List<Jobs> jobDatatoList(AssetManager assetManager) throws IOException {
        InputStream jobInput = assetManager.open("job_data.txt");

        ObjectMapper objectMapper = new ObjectMapper();

        List<Jobs> jobData = Arrays.asList(objectMapper.readValue(jobInput, Jobs[].class));
        jobInput.close();

        for(int i = 0; i < jobData.size(); i++) {
            Log.i("UTIL-INF-EVENT", String.valueOf(jobData.get(i).job_name));
        }

        return jobData;
    }
}
