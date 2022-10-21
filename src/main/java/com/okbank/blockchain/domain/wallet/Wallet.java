package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.domain.BaseEntity;
import com.okbank.blockchain.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        indexes = {
                @Index(name = "idx_wallet_user_uid", columnList = "user_uid")
        }
)
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletUid;

    @Column(columnDefinition = "varchar(100) NOT NULL comment '지갑명'")
    private String name;

    @Column(columnDefinition = "tinyint(1) NOT NULL default '1' comment '사용여부'")
    private boolean useAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uid", nullable = false, updatable = false)
    private User owner;

    @Builder
    public Wallet(String name, boolean useAt, User owner) {
        this.name = name;
        this.useAt = useAt;
        this.owner = owner;
    }

    /**
     * 수정
     */
    public Wallet update(String name, boolean useAt, User owner) {
        this.name = name;
        this.useAt = useAt;
        this.owner = owner;
        return this;
    }

    /**
     * 사용여부 수정
     */
    public Wallet updateUseAt(boolean useAt) {
        this.useAt = useAt;
        return this;
    }

}
