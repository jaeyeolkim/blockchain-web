package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.user.UserMock;
import com.okbank.blockchain.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanup() {
        walletRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("월렛 입력 및 조회")
    void findById() {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);
        Wallet wallet = WalletMock.buildWallet(owner, walletName);

        userRepository.save(owner);
        Wallet savedWallet = walletRepository.save(wallet);

        // when
        Wallet findWallet = walletRepository.findById(savedWallet.getWalletUid())
                .orElseThrow();

        // then
        assertThat(findWallet.getName()).isEqualTo(walletName);
    }

    @Test
    @DisplayName("Querydsl 테스트")
    void findAllByQuerydsl() {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);
        Wallet wallet = WalletMock.buildWallet(owner, walletName);

        userRepository.save(owner);
        walletRepository.save(wallet);

        // when
        List<WalletListResponseDto> wallets = walletRepository.findWallets();
        WalletListResponseDto responseDto = wallets.stream().findFirst().orElseThrow();

        // then
        System.out.println("responseDto = " + responseDto);
        assertThat(responseDto.getWalletName()).isEqualTo(walletName);
        assertThat(responseDto.getOwnerName()).isEqualTo(userName);
    }

}