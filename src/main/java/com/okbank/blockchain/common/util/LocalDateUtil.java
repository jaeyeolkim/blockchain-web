package com.okbank.blockchain.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateUtil {
    private static final DateTimeFormatter DATE_8_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_10_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * yyyyMMdd 형식의 String을 LocalDate로 변경
     */
    public static LocalDate fromString(String date8String) {
        return LocalDate.parse(date8String, DATE_8_FORMATTER);
    }

    /**
     * yyyy-MM-dd 로 포멧 된 String 리턴
     */
    public static String format10String(LocalDate localDate) {
        return DATE_10_FORMATTER.format(localDate);
    }

    /**
     * 현재 일자의 yyyyMMdd 로 포멧 된 String 리턴
     */
    public static String now() {
        return LocalDate.now().format(DATE_8_FORMATTER);
    }

    /**
     * 현재 일자에 빼고 yyyy-MM-dd 로 포멧 된 String 리턴
     */
    public static String nowMinusDaysOfPattern(long daysToSubtract) {
        return LocalDate.now().minusDays(daysToSubtract).format(DATE_10_FORMATTER);
    }
    /**
     * 현재 일자에 더하여 yyyy-MM-dd 로 포멧 된 String 리턴
     */
    public static String nowPlusDaysOfPattern(long daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DATE_10_FORMATTER);
    }
}
