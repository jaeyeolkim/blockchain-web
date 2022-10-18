package com.okbank.blockchain.common.util;

import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * UID 생성
 */
public class OkIdGenerator {

    private static final AlternativeJdkIdGenerator alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();
    private static final DateTimeFormatter UID_STRING_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSSSS");

    /**
     * UTC 시간기반 31자리 UID를 생성함.
     *
     * @return 31자리 UID
     */
    public static String generate() {
        final String now = LocalDateTime.now(ZoneOffset.UTC).format(UID_STRING_TIME_FORMATTER);
        return
                getCharForNumber(Integer.parseInt(now.substring(2, 3))) //2
                        + getCharForNumber(Integer.parseInt(now.substring(3, 4))) //2 년
                        + getCharForNumber(Integer.parseInt(now.substring(4, 6))) // 00월
                        + now.substring(6, 8) // 일
                        + getCharForNumber(Integer.parseInt(now.substring(8, 10))) // 시
                        + now.substring(10, 17)
                        + alternativeJdkIdGenerator.generateId().toString()
                        .replace("-", "")
                        .substring(0, 18);
    }

    private static String getCharForNumber(int i) {
        return String.valueOf((char) (i + 'a'));
    }
}
