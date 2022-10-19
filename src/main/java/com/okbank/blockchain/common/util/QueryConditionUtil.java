package com.okbank.blockchain.common.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryConditionUtil {

    public static Predicate timeStampBetween(
            DateTimePath<Timestamp> timeStamp, LocalDate fromCreateAt, LocalDate toCreateAt) {
        var builder = new BooleanBuilder();
        if (fromCreateAt != null) {
            builder.and(timeStamp.goe(
                    Timestamp.valueOf(fromCreateAt.atStartOfDay())));
        }
        if (toCreateAt != null) {
            builder.and(timeStamp.lt(
                    Timestamp.valueOf(toCreateAt.plusDays(1).atStartOfDay())));
        }
        return builder;
    }

    public static Predicate timeStampLoe(
            DateTimePath<Timestamp> timeStamp, LocalDate createDate) {
        return timeStamp.lt(
                Timestamp.valueOf(createDate.plusDays(1).atStartOfDay()));
    }


    public static Predicate localDateBetween(
            DatePath<LocalDate> localDate, LocalDate fromDate, LocalDate toDate) {
        var builder = new BooleanBuilder();
        if (fromDate != null) {
            builder.and(localDate.goe(
                    fromDate));
        }
        if (toDate != null) {
            builder.and(localDate.lt(
                    toDate.plusDays(1)));
        }
        return builder;
    }

    public static Predicate localDateTimeBetween(
            DateTimePath<LocalDateTime> localDateTime, LocalDate fromDate, LocalDate toDate) {
        var builder = new BooleanBuilder();
        if (fromDate != null) {
            builder.and(localDateTime.goe(
                    fromDate.atStartOfDay()));
        }
        if (toDate != null) {
            builder.and(localDateTime.lt(
                    toDate.plusDays(1).atStartOfDay()));
        }
        return builder;
    }
}
