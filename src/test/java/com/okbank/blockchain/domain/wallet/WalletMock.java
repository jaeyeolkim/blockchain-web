package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.domain.user.User;

public class WalletMock {

    public static Wallet buildWallet(User owner, String walletName) {
        return Wallet.builder()
                .name(walletName)
                .useAt(true)
                .owner(owner)
                .build();
    }
}
