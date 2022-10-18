package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.api.wallet.dto.WalletResponseDto;

import java.util.List;

public interface WalletRepositoryCustom {
    List<WalletListResponseDto> findWallets();
    WalletResponseDto findWallet(String walletUid);
}
