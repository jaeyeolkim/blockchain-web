package com.okbank.blockchain.api.wallet.dto;

import com.okbank.blockchain.domain.wallet.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class WalletListResponseDto {
    private Long walletUid;
    private String walletName;
    private String ownerName;
    private LocalDateTime modifiedDate;

    public WalletListResponseDto(Wallet wallet) {
        this.walletUid = wallet.getWalletUid();
        this.walletName = wallet.getName();
        this.ownerName = wallet.getOwner().getName();
        this.modifiedDate = wallet.getModifiedDate();
    }
}
