package com.example._23cspringboot3jdbc.response;

public class MessageResponse {

    private String message;
//    private String field;
    public MessageResponse(String message)
    {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
