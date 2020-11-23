package com.kakaopay.moneyspread.funciton;

import org.apache.commons.lang3.RandomUtils;
import java.security.SecureRandom;

/**
 * 생성 함수
 * **/
public class Generator {

    /** 토큰을 생성합니다 */
    public static String getToken(){

        /** 대소문자+숫자를 조합하여 랜덤으로 문자열을 만듭니다. ***/
        String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
        String TOKEN_NUMBER= "1234567890";
        String DATA_FOR_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER+TOKEN_NUMBER;

        /** 랜덤 문자열 길이 **/
        int random_string_length=3;

        StringBuilder sb = new StringBuilder(random_string_length);
        SecureRandom random = new SecureRandom();
        String token = "";
        for (int i = 0; i < random_string_length; i++) {
            token+= DATA_FOR_RANDOM_STRING.charAt(random.nextInt(DATA_FOR_RANDOM_STRING.length()));
        }

        return token;
    }

    /** 뿌린돈에 대해서 돈을 분배합니다. */
    public static long[] divide(long amount, int count) {

        /** 랜덤으로 돈을 분배합니다. **/
        long[] array = new long[count];
        long max = RandomUtils.nextLong(amount / count, amount / count * 2);
        for (int i = 0; i < count - 1; i++) {
            array[i] = RandomUtils.nextLong(1, Math.min(max, amount));
            amount -= array[i];
        }
        array[count - 1] = amount;
        return array;
    }

}
