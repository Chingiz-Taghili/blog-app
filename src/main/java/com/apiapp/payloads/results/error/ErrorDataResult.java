package com.apiapp.payloads.results.error;

import com.apiapp.payloads.results.DataResult;

public class ErrorDataResult<TData> extends DataResult<TData> {

    public ErrorDataResult() {
        super(false);
    }

    public ErrorDataResult(String message) {
        super(false, message);
    }
}
