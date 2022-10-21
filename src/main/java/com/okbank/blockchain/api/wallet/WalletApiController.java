package com.okbank.blockchain.api.wallet;

import com.okbank.blockchain.api.wallet.dto.*;
import com.okbank.blockchain.common.payload.CommonResponse;
import com.okbank.blockchain.common.payload.DataResponse;
import com.okbank.blockchain.common.payload.PageResponse;
import com.okbank.blockchain.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.okbank.blockchain.common.constants.Constants.API_URI_PREFIX;

@RequiredArgsConstructor
@RestController
public class WalletApiController {

    private final WalletService walletService;

    @GetMapping(API_URI_PREFIX + "/my/wallet/{userUid}")
    public ResponseEntity<DataResponse<List<WalletListResponseDto>>> findMyWallets(
            @PathVariable Long userUid) {
        return ResponseEntity.ok(walletService.findMyWallets(userUid));
    }

    @GetMapping(API_URI_PREFIX + "/wallet")
    public ResponseEntity<PageResponse<List<WalletListResponseDto>>> findWallets(
            @Valid WalletListRequestDto requestDto) {
        return ResponseEntity.ok(walletService.findWallets(requestDto));
    }

    @PostMapping(API_URI_PREFIX + "/wallet")
    public ResponseEntity<DataResponse<Long>> saveWallet(
            @Valid @RequestBody WalletSaveRequestDto requestDto) {
        return ResponseEntity.ok(walletService.saveWallet(requestDto));
    }

    @PutMapping(API_URI_PREFIX + "/wallet/{walletUid}")
    public ResponseEntity<DataResponse<WalletResponseDto>> updateWallet(
            @PathVariable Long walletUid,
            @Valid @RequestBody WalletUpdateRequestDto requestDto) {
        return ResponseEntity.ok(walletService.updateWallet(walletUid, requestDto));
    }

    @DeleteMapping(API_URI_PREFIX + "/wallet/{walletUid}")
    public ResponseEntity<CommonResponse> deleteWallet(
            @PathVariable Long walletUid) {
        return ResponseEntity.ok(walletService.deleteWallet(walletUid));
    }
}
