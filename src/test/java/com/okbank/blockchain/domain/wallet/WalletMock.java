package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletSaveRequestDto;
import com.okbank.blockchain.api.wallet.dto.WalletUpdateRequestDto;
import com.okbank.blockchain.domain.user.User;

public class WalletMock {

    public static Wallet buildWallet(User owner, String walletName) {
        return Wallet.builder()
                .name(walletName)
                .useAt(true)
                .owner(owner)
                .build();
    }

    public static WalletSaveRequestDto buildWalletSaveRequest(User owner, String walletName) {
        return WalletSaveRequestDto.builder()
                .userUid(owner.getUserUid())
                .walletName(walletName)
                .useAt(true)
                .build();
    }

    public static WalletUpdateRequestDto buildWalletUpdateRequest(Long userUid, String walletName) {
        return WalletUpdateRequestDto.builder()
                .userUid(userUid)
                .walletName(walletName)
                .useAt(true)
                .build();
    }
}
