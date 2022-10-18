package com.okbank.blockchain.common.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.okbank.blockchain.common.constants.ResponseCode;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * 공통 결과 응답
 */
@Getter
public class CommonResponse implements Serializable {
    int code;
    String message;

    @Builder
    public CommonResponse(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    /**
     * ResponseCode 객체를 다시 리턴
     *
     * @return
     */
    @JsonIgnore
    public ResponseCode getResponseCode() {
        return ResponseCode.valueOf(code);
    }

    /**
     * 코드가 OK 인지 리턴
     *
     * @return
     */
    @JsonIgnore
    public boolean isOk() {
        return ResponseCode.OK.getCode() == code;
    }

    public static CommonResponse get(ResponseCode ok) {
        return CommonResponse.builder()
                .responseCode(ok)
                .build();
    }
}
