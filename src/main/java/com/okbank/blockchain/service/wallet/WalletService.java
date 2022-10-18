package com.okbank.blockchain.service.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.api.wallet.dto.WalletResponseDto;
import com.okbank.blockchain.common.constants.ResponseCode;
import com.okbank.blockchain.common.payload.DataResponse;
import com.okbank.blockchain.domain.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public DataResponse<List<WalletListResponseDto>> findWallets() {
        List<WalletListResponseDto> wallets = walletRepository.findWallets();
        return DataResponse.<List<WalletListResponseDto>>dataBuilder()
                .responseCode(ResponseCode.OK)
                .data(wallets)
                .build();
    }

    @Transactional(readOnly = true)
    public WalletResponseDto findWallet(String walletUid) {
        return walletRepository.findWallet(walletUid);
    }
}
