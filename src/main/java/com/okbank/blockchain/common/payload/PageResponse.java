package com.okbank.blockchain.common.payload;


import com.okbank.blockchain.common.constants.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> extends DataResponse<T> {

    private int pageNum;
    private long totalCount;
    private int pageSize;

    @Builder(builderMethodName = "pageBuilder")
    public PageResponse(ResponseCode responseCode, T data, int pageNum, long totalCount,
                        int pageSize) {
        super(responseCode, data);
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
    }
}
