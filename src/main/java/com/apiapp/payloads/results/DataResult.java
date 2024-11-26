package com.apiapp.payloads.results;

public class DataResult<TData> extends Result{
    public TData data;

    public DataResult(boolean success, TData data) {
        super(success);
        this.data = data;
    }

    public DataResult(boolean success) {
        super(success);
    }

    public DataResult(boolean success, String message) {
        super(success, message);
    }

}
