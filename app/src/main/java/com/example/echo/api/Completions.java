package com.example.echo.api;

import android.content.res.AssetManager;
import android.util.Log;
import android.widget.TextView;

import com.example.echo.ResponseParser;
import com.example.echo.api.model.cRoot;
import com.example.echo.keyRetrieval;
import com.example.echo.userInterface.Message;
import com.example.echo.userInterface.MessageAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Completions {
    //get entire data
    public static void addMsgArrResponse(TextView dummy, MessageAdapter messageAdapter, ArrayList<Message> msgArray, String BOT_KEY, AssetManager assetManager, String prompt, String completionType) throws InterruptedException {
        AtomicReference<String> response = new AtomicReference<>();

        //noinspection CodeBlock2Expr
        Thread thread = new Thread(() -> {
            response.set(gptAPI(assetManager, prompt, completionType).choices.get(0).getText());
            msgArray.add(new Message(response.get().trim(), BOT_KEY));

            dummy.post(() -> {
                messageAdapter.notifyDataSetChanged();
            });

        });


        thread.start();
        //thread.join();
    }

    // set text via UI thread
    public static void setCodeCompleteText(TextView textView, AssetManager assetManager, String prompt, String completionType) throws InterruptedException {
        textView.post(() -> {
            try {
                String temp = codeCompleteText(assetManager, prompt, completionType);
                //Log.i("[API-ADD-TEXT]", temp);
                textView.setText(temp.trim());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    //==============================================================================================

    // returns API response -- uses custom model, so it's easy to access each attribute
    private static cRoot gptAPI(AssetManager assetManager, String prompt, String completionType)  {
        StringBuilder response = new StringBuilder();
        cRoot responseData = null;

        try {
            Log.i("[API]", "[API] Beginning API Task(s)...");

            // set endpoint and open connection
            URL url = new URL("https://api.openai.com/v1/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set request method property
            connection.setRequestMethod("POST");

            // formatting
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // to use api key, create a file called 'API_KEY.txt' in 'main' and paste your API key on the very first line
            // "fileHandler" should automatically pick up the file and extract the key.
            connection.setRequestProperty("Authorization", "Bearer " + keyRetrieval.getKey(assetManager));

            // connect ensure -- can't write to connection if disabled or false
            connection.setDoOutput(true);

            /*
              Notes:
               - 'code-davinci-002' provides complex info but is not suited for realtime
               - 'code-cushman-001' only produces code (thus far) with documentation but is suitable for realtime
               - if response is longer than 'max_tokens' the response will be cut off
             */
            int maxToken = 0;
            switch (completionType) {
                case "code-complex":
                    Log.i("[API]", "Setting model to complex code completion!");
                    completionType = "code-davinci-002";
                    maxToken = 900;
                    break;
                case "code-simple":
                    Log.i("[API]", "Setting model to simple code completion!");
                    completionType = "code-cushman-001";
                    maxToken = 900;
                    break;
                case "text":
                    Log.i("[API]", "Setting model to text completion!");
                    completionType = "text-davinci-003";
                    maxToken = 75;
                    break;
                default:
                    Log.e("[API]", "[API-SET-MODEL] COME ON D! yes I'm looking at you!");
                    Log.e("[API]", "[API-SET-MODEL] Model not allowed, please change to " +
                            "one of the following ('code-complex', 'code-simple', 'text') and restart");
                    System.exit(401);
            }

            // json POST
            Log.i("[API]", "[EDIT-API] Made it past URL");

            JSONObject postBody = new JSONObject();
            postBody.put("model", completionType);           // model to use -- 'code-davinci-002' or 'code-cushman-001' for code completion
            postBody.put("prompt", prompt);
            postBody.put("max_tokens", maxToken);
            postBody.put("temperature", 0.1);           // lower == more deterministic || higher == more random -- 0.2-0.4 seems to be the sweet spot
            postBody.put("top_p", 1);                   // completions to create -- leave at 1
            postBody.put("n", 1);
            postBody.put("stream", false);
            postBody.put("frequency_penalty", 0.15);
            postBody.put("stop", "\"\"\"");              // where API will stop generating sequences -- leave blank to allow for full response

            Log.i("[API]", "[EDIT-API] Made it past POST");

            // post jsonObj to endpoint
            try (OutputStreamWriter osWriter = new OutputStreamWriter(connection.getOutputStream())) {
                Log.i("[API]", "[EDIT-API-POST] " + postBody);
                osWriter.write(postBody.toString());
            }
            Log.i("[API]", "[EDIT-API] Made it past POSTJSON2BYTEARRAY");

            // store response as string and print raw response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String jsonResponseString;
                while ((jsonResponseString = br.readLine()) != null) {
                    response.append(jsonResponseString.trim());
                }
            }
            catch (Exception ex){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
                Log.e("[COMPLETION-API]", "[COMPLETION-API-HTTP-ERR] " + connection.getResponseMessage());
                while (br.readLine() != null) {
                    Log.e("[COMPLETION-API]", "[COMPLETION-API-HTTP-ERR]" + br.readLine());
                }
                System.exit(connection.getResponseCode());
            }

            Log.i("[API]", "[API-RESPONSE-TEST-PROMPT] >> " + prompt);

            // parse raw json string and store easily accessible data structure
            responseData = ResponseParser.parseCompletionJSON(String.valueOf(response));

            // leave arrayList element at 0 || get text is the generated response from the API
            Log.i("[API]", "[COMPLETION-TEST-PROMPT-ANSWER] >> " + responseData.choices.get(0).getText());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        Log.i("[API]", "[API] API Task(s) Completed");

        return responseData;
    }

    private static String codeCompleteText(AssetManager assetManager, String prompt, String completionType) throws InterruptedException {
        AtomicReference<String> response = new AtomicReference<>();

        //noinspection CodeBlock2Expr
        Thread thread = new Thread(() -> {
            response.set(gptAPI(assetManager, prompt, completionType).choices.get(0).getText());
        });
        thread.start();
        thread.join();

        return response.get();
    }
}
