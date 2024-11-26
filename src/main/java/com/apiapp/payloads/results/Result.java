package com.apiapp.payloads.results;

public class Result {
    public boolean success;
    public String message;

    public Result() {
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
