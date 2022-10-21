package com.okbank.blockchain.common.util;

import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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
    public static String getStringUid() {
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

    /**
     * UTC 시간기반 18자리 UID를 생성함.
     * 테이블의 ID로 사용하려는 경우 밀리세컨드까지 동일한 환경에서 입력 가능하다면 사용하기에 무리가 있을 수 있다.
     * @return 18자리 UID
     */
    public static Long getLongUid() {
        final String now = LocalDateTime.now(ZoneOffset.UTC).format(UID_STRING_TIME_FORMATTER);
        String s = now.substring(2, 3) //2
                + now.substring(3, 4) //2 년
                + now.substring(4, 6) // 00월
                + now.substring(6, 8) // 일
                + now.substring(8, 10) // 시
                + now.substring(10, 17)
                + getRandomInt(100, 999);
        return Long.parseLong(s);
    }

    private static String getCharForNumber(int i) {
        return String.valueOf((char) (i + 'a'));
    }

    private static int getRandomInt(int low, int high) {
        return new Random().nextInt(high-low) + low;
    }
}
