package com.example.echo;
import com.example.echo.api.model.cRoot;
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
}


