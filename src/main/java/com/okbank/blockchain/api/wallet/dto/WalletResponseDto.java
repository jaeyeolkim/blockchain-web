package com.okbank.blockchain.api.wallet.dto;

import com.okbank.blockchain.domain.wallet.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class WalletResponseDto {
    private Long walletUid;
    private String walletName;
    private String ownerName;

    public WalletResponseDto(Wallet wallet) {
        this.walletUid = wallet.getWalletUid();
        this.walletName = wallet.getName();
        this.ownerName = wallet.getOwner().getName();
    }
}
