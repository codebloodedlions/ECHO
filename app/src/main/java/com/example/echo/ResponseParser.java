package com.example.echo;
import com.example.echo.api.model.cRoot;
import com.example.echo.api.model.eventapi.Root;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseParser {
    public static cRoot parseCompletionJSON(String response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(response, cRoot.class);
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(500);
            return null;
        }
    }

    public static Root parseEventJSON(String response){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(response, Root.class);
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.exit(500);
            return null;
        }
    }
}


