package com.okbank.blockchain.service.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListRequestDto;
import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.api.wallet.dto.WalletResponseDto;
import com.okbank.blockchain.common.constants.ResponseCode;
import com.okbank.blockchain.common.payload.DataResponse;
import com.okbank.blockchain.common.payload.PageResponse;
import com.okbank.blockchain.domain.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public DataResponse<List<WalletListResponseDto>> findMyWallets(Long userUid) {
        List<WalletListResponseDto> wallets = walletRepository.findMyWallets(userUid);
        return DataResponse.<List<WalletListResponseDto>>dataBuilder()
                .responseCode(ResponseCode.OK)
                .data(wallets)
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponse<List<WalletListResponseDto>> findWallets(WalletListRequestDto requestDto) {
        Page<WalletListResponseDto> wallets = walletRepository.findWallets(requestDto);
        return PageResponse.<List<WalletListResponseDto>>pageBuilder()
                .responseCode(ResponseCode.OK)
                .data(wallets.getContent())
                .totalCount(wallets.getTotalElements())
                .pageNum(requestDto.getPageNum())
                .pageSize(requestDto.getPageSize())
                .build();
    }

    @Transactional(readOnly = true)
    public WalletResponseDto findWallet(String walletUid) {
        return walletRepository.findWallet(walletUid);
    }
}
