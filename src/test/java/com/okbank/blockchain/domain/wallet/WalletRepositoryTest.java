package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListRequestDto;
import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.okbank.blockchain.domain.user.User;
import com.okbank.blockchain.domain.user.UserMock;
import com.okbank.blockchain.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @DisplayName("Querydsl 동작 확인")
    void findAllByQuerydsl() {
        // given
        String userName = "kim";
        String walletName = "my wallet";
        User owner = UserMock.buildUser(userName);
        Wallet wallet = WalletMock.buildWallet(owner, walletName);

        userRepository.save(owner);
        walletRepository.save(wallet);

        // when
        List<WalletListResponseDto> wallets = walletRepository.findMyWallets(owner.getUserUid());
        WalletListResponseDto responseDto = wallets.stream().findFirst().orElseThrow();

        // then
        assertThat(responseDto.getWalletName()).isEqualTo(walletName);
        assertThat(responseDto.getOwnerName()).isEqualTo(userName);
    }

    @Test
    @DisplayName("월렛 페이징 조회")
    void findPageWallets() {
        // given
        String userName = "kim";
        User owner = UserMock.buildUser(userName);
        userRepository.save(owner);

        String walletName = "my wallet";
        int walletMaxSize = 20;
        for (int i = 0; i < walletMaxSize; i++) {
            Wallet wallet = WalletMock.buildWallet(owner, walletName + (i+1));
            walletRepository.save(wallet);
        }

        // when
        LocalDate now = LocalDate.now();
        WalletListRequestDto walletListRequestDto = WalletListRequestDto.builder()
                .pageNum(1)
                .fromDate(now.minusDays(1L))
                .toDate(now.plusDays(1L))
                .ownerName(userName)
                .build();
        Page<WalletListResponseDto> wallets = walletRepository.findWallets(walletListRequestDto);
        wallets.stream().forEach(dto -> logger.info("{}", dto));
        WalletListResponseDto responseDto = wallets.stream().findFirst().orElseThrow();

        // then
        assertThat(responseDto.getWalletName()).isEqualTo(walletName + walletMaxSize);
        assertThat(responseDto.getOwnerName()).isEqualTo(userName);
    }

}