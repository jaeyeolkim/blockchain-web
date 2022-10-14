package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.okbank.blockchain.domain.user.QUser.user;
import static com.okbank.blockchain.domain.wallet.QWallet.wallet;
import static com.querydsl.core.types.Projections.fields;

@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WalletListResponseDto> findWallets() {
        return queryFactory
                .select(fields(WalletListResponseDto.class,
                        wallet.walletUid,
                        wallet.name.as("walletName"),
                        wallet.modifiedDate,
                        user.name.as("ownerName")
                ))
                .from(wallet)
                .join(user).on(wallet.owner.userUid.eq(user.userUid))
                .orderBy(wallet.walletUid.desc())
                .fetch();
    }
}
