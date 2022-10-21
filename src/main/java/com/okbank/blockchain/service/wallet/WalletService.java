package com.okbank.blockchain.service.wallet;

import com.okbank.blockchain.api.wallet.dto.*;
import com.okbank.blockchain.common.constants.ResponseCode;
import com.okbank.blockchain.common.payload.CommonResponse;
import com.okbank.blockchain.common.payload.DataResponse;
import com.okbank.blockchain.common.payload.PageResponse;
import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.wallet.Wallet;
import com.okbank.blockchain.domain.wallet.WalletRepository;
import com.okbank.blockchain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserService userService;

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
    public WalletResponseDto findWallet(Long walletUid) {
        Wallet findWallet = walletRepository.findById(walletUid)
                .orElseThrow(() -> new IllegalArgumentException("해당 월렛이 존재하지 않습니다. walletUid=" + walletUid));
        return new WalletResponseDto(findWallet);
    }

    @Transactional
    public DataResponse<Long> saveWallet(WalletSaveRequestDto requestDto) {
        User findUser = userService.findUser(requestDto.getUserUid());
        Wallet newWallet = walletRepository.save(requestDto.toEntity(findUser));

        return DataResponse.<Long>dataBuilder()
                .responseCode(ResponseCode.OK)
                .data(newWallet.getWalletUid())
                .build();
    }

    @Transactional
    public DataResponse<WalletResponseDto> updateWallet(Long walletUid, WalletUpdateRequestDto requestDto) {
        User findUser = userService.findUser(requestDto.getUserUid());
        Wallet findWallet = walletRepository.findById(walletUid)
                .orElseThrow(() -> new IllegalArgumentException("해당 월렛이 존재하지 않습니다. walletUid=" + walletUid));

        findWallet.update(requestDto.getWalletName(), requestDto.isUseAt(), findUser);

        return DataResponse.<WalletResponseDto>dataBuilder()
                .responseCode(ResponseCode.OK)
                .data(new WalletResponseDto(findWallet))
                .build();
    }

    @Transactional
    public CommonResponse deleteWallet(Long walletUid) {
        Wallet findWallet = walletRepository.findById(walletUid)
                .orElseThrow(() -> new IllegalArgumentException("해당 월렛이 존재하지 않습니다. walletUid=" + walletUid));
        walletRepository.delete(findWallet);

        return CommonResponse.builder()
                .responseCode(ResponseCode.OK)
                .build();
    }

}
