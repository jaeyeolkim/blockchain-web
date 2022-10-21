package com.okbank.blockchain.api.wallet.dto;

import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.wallet.Wallet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalletSaveRequestDto {

    private String walletName;

    private boolean useAt;

    private Long userUid;

    @Builder
    public WalletSaveRequestDto(String walletName, boolean useAt, Long userUid) {
        this.walletName = walletName;
        this.useAt = useAt;
        this.userUid = userUid;
    }

    public Wallet toEntity(User owner){
        return Wallet.builder()
                .name(walletName)
                .useAt(useAt)
                .owner(owner)
                .build();
    }
}
