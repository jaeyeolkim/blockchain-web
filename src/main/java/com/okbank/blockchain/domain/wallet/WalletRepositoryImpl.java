package com.okbank.blockchain.domain.wallet;

import com.okbank.blockchain.api.wallet.dto.WalletListRequestDto;
import com.okbank.blockchain.api.wallet.dto.WalletListResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

import static com.okbank.blockchain.common.util.QueryConditionUtil.localDateTimeBetween;
import static com.okbank.blockchain.domain.user.QUser.user;
import static com.okbank.blockchain.domain.wallet.QWallet.wallet;
import static com.querydsl.core.types.Projections.fields;
import static org.springframework.util.StringUtils.hasLength;

@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WalletListResponseDto> findMyWallets(Long userUid) {
        return queryFactory
                .select(fields(WalletListResponseDto.class,
                        wallet.walletUid,
                        wallet.name.as("walletName"),
                        wallet.modifiedDate,
                        user.name.as("ownerName")
                ))
                .from(wallet)
                .join(user).on(wallet.owner.userUid.eq(user.userUid))
                .where(
                        userUidEq(userUid)
                )
                .orderBy(wallet.walletUid.desc())
                .fetch();
    }

    @Override
    public Page<WalletListResponseDto> findWallets(WalletListRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable();
        List<WalletListResponseDto> fetch = queryFactory
                .select(fields(WalletListResponseDto.class,
                        wallet.walletUid,
                        wallet.name.as("walletName"),
                        wallet.modifiedDate,
                        user.name.as("ownerName")
                ))
                .from(wallet)
                .join(user).on(wallet.owner.userUid.eq(user.userUid))
                .where(
                        localDateTimeBetween(wallet.modifiedDate, requestDto.getFromDate(), requestDto.getToDate()),
                        userNameEq(requestDto.getOwnerName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(wallet.walletUid.desc())
                .fetch();

        Long count = queryFactory
                .select(wallet.count())
                .from(wallet)
                .join(user).on(wallet.owner.userUid.eq(user.userUid))
                .where(
                        localDateTimeBetween(wallet.modifiedDate, requestDto.getFromDate(), requestDto.getToDate()),
                        userNameEq(requestDto.getOwnerName())
                )
                .fetchOne();

        return new PageImpl<>(fetch, pageable, count == null ? 0 : count);
    }

    private BooleanExpression userUidEq(Long userUid) {
        return Objects.isNull(userUid) ? null : user.userUid.eq(userUid);
    }

    private BooleanExpression userNameEq(String userName) {
        return hasLength(userName) ? user.name.eq(userName) : null;
    }
}
