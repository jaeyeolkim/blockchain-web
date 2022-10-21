package com.okbank.blockchain.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * 생성자, 수정자 정보 Audit
 *
 * @author jaeyeol
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(columnDefinition = "varchar(32) COMMENT '생성자 ID'", updatable = false)
    private String createBy;

    @LastModifiedBy
    @Column(columnDefinition = "varchar(32) COMMENT '마지막 수정자 ID'")
    private String lastUpdateBy;
}
