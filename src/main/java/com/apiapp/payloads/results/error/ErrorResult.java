package com.apiapp.payloads.results.error;

import com.apiapp.payloads.results.Result;

public class ErrorResult extends Result {

    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String message) {
        super(false, message);
    }
}
