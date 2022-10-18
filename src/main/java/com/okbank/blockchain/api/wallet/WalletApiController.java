package com.okbank.blockchain.api.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.common.payload.DataResponse;
import com.okbank.blockchain.service.wallet.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.okbank.blockchain.common.constants.Constants.API_URI_PREFIX;

@RequiredArgsConstructor
@RestController
public class WalletApiController {

    private final WalletService walletService;

    @GetMapping(API_URI_PREFIX + "/wallets")
    public ResponseEntity<DataResponse<List<WalletListResponseDto>>> findWallets() {
        return ResponseEntity.ok(walletService.findWallets());
    }
}
