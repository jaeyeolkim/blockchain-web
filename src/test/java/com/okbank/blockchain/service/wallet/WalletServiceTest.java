package com.okbank.blockchain.service.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletResponseDto;
import com.okbank.blockchain.api.wallet.dto.WalletSaveRequestDto;
import com.okbank.blockchain.common.payload.DataResponse;
import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.user.UserMock;
import com.okbank.blockchain.domain.user.UserRepository;
import com.okbank.blockchain.domain.wallet.Wallet;
import com.okbank.blockchain.domain.wallet.WalletMock;
import com.okbank.blockchain.domain.wallet.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletService walletService;

    @AfterEach
    void cleanup() {
        walletRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("월렛 입력 및 조회")
    void walletFindById() {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);
        Wallet wallet = WalletMock.buildWallet(owner, walletName);

        userRepository.save(owner);
        Wallet savedWallet = walletRepository.save(wallet);

        // when
        WalletResponseDto findWallet = walletService.findWallet(savedWallet.getWalletUid());

        // then
        logger.info("findWallet={}", findWallet);
        assertThat(findWallet.getWalletName()).isEqualTo(walletName);
    }

    @Test
    @DisplayName("월렛이 입력된다")
    void saveWallet() {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);

        userRepository.save(owner);

        // when
        WalletSaveRequestDto walletSaveRequestDto = WalletMock.buildWalletSaveRequest(owner, walletName);
        DataResponse<Long> response = walletService.saveWallet(walletSaveRequestDto);

        // then
        WalletResponseDto findWallet = walletService.findWallet(response.getPayload());
        assertThat(findWallet.getWalletName()).isEqualTo(walletName);
        assertThat(findWallet.getOwnerName()).isEqualTo(userName);
    }
}