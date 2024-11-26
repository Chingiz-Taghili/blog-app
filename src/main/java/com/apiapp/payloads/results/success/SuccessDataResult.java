package com.apiapp.payloads.results.success;

import com.apiapp.payloads.results.DataResult;

public class SuccessDataResult<TData> extends DataResult<TData> {

    public SuccessDataResult(TData tData) {
        super(true, tData);
    }
}
