package com.okbank.blockchain.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity 상속하면 필드도 컬럼으로 인식하도록 한다
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 포함
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "timestamp NOT NULL COMMENT '생성시간'")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date", columnDefinition = "timestamp NOT NULL COMMENT '마지막 수정시간'")
    private LocalDateTime modifiedDate;
}
