package com.okbank.blockchain.domain.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom {
}
