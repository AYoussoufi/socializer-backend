package com.project.socializer.util.responsewriter;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
@Data
public class ResponseBody {
    private int status;
    private String message;
    private String date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    private String path;

    public ResponseBody(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public Map<String , Object> getResponseBodyMap(){
        Map<String,Object> responseBody = new HashMap<>();

        responseBody.put("path",this.path);
        responseBody.put("date",this.date);
        responseBody.put("message",this.message);
        responseBody.put("status",this.status);

        return responseBody;
    }
}
