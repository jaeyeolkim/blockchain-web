package com.okbank.blockchain.api.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalletUpdateRequestDto {

    private String walletName;
    private boolean useAt;
    private Long userUid;

    @Builder
    public WalletUpdateRequestDto(String walletName, boolean useAt, Long userUid) {
        this.walletName = walletName;
        this.useAt = useAt;
        this.userUid = userUid;
    }
}
