package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListRequestDto;
import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.api.wallet.dto.WalletResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WalletRepositoryCustom {
    List<WalletListResponseDto> findMyWallets(Long userUid);
    Page<WalletListResponseDto> findWallets(WalletListRequestDto requestDto);
    WalletResponseDto findWallet(String walletUid);
}
