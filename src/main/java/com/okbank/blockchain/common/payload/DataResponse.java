package com.okbank.blockchain.common.payload;

import com.okbank.blockchain.common.constants.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse<T> extends CommonResponse {

    private T payload;

    public DataResponse() {
        super(ResponseCode.OK);
    }

    @Builder(builderMethodName = "dataBuilder")
    public DataResponse(ResponseCode responseCode, T data) {
        super(responseCode);
        this.payload = data;
    }
}
