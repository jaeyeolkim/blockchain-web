package com.okbank.blockchain.common.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

/**
 * 페이지 요청 DTO
 */
@Setter
public class PageRequest implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private String orderBy;

    public PageRequest() {
    }

    @Builder(builderMethodName = "pageBuilder")
    public PageRequest(Integer pageNum, Integer pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }

    public String getOrderBy() {
        return orderBy == null ? "createAt:desc" : orderBy;
    }

    @JsonIgnore
    protected Integer getPageIndex() {
        return getPageNum() > 0 ? getPageNum() - 1 : 0;
    }

    @JsonIgnore
    public Pageable getPageable() {
        return org.springframework.data.domain.PageRequest.of(getPageIndex(), getPageSize());
    }

    @JsonIgnore
    public PageRequest getPageRequest() {
        return this;
    }
}
