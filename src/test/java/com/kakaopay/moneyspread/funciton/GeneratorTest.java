package com.kakaopay.moneyspread.funciton;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorTest {

    @Test
    public static void getToken_Test(){
        //Given
        /** 대소문자+숫자를 조합하여 랜덤으로 문자열을 만듭니다. ***/

        String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
        String TOKEN_NUMBER= "1234567890";
        String DATA_FOR_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER+TOKEN_NUMBER;

        /** 랜덤 문자열 길이 **/
        int random_string_length=3;

        //When
        StringBuilder sb = new StringBuilder(random_string_length);
        SecureRandom random = new SecureRandom();
        String token = "";
        for (int i = 0; i < random_string_length; i++) {
            token+= DATA_FOR_RANDOM_STRING.charAt(random.nextInt(DATA_FOR_RANDOM_STRING.length()));
        }

        //Then
        //길이가 3자리인지 체크
        Assertions.assertThat(token.length()).isEqualTo(3);

        //랜덤인지 체크
        Assertions.assertThat(token.charAt(0))
                .isNotEqualTo(token.charAt(1))
                .isNotEqualTo(token.charAt(2));

    }

    @Test
    public void divide_Test_Muilti(){
        /** 다수일때 분배 **/
        //Given
        int count = 5;
        long amount =1000;

        //When
        /** 랜덤으로 돈을 분배합니다. **/
        long[] array = new long[count];
        long max = RandomUtils.nextLong(amount / count, amount / count * 2);
        for (int i = 0; i < count - 1; i++) {
            array[i] = RandomUtils.nextLong(1, Math.min(max, amount));
            amount -= array[i];
        }
        array[count - 1] = amount;

        //Then
        Assertions.assertThat(array[0])
                .isNotEqualTo(array[1])
                .isNotEqualTo(array[2]);

    }

    @Test
    public void divide_Test_single(){

        /** 한명일때 분배 **/

        //Given
        int count = 1;
        long amount =1000;

        //When
        /** 랜덤으로 돈을 분배합니다. **/
        long[] array = new long[count];
        long max = RandomUtils.nextLong(amount / count, amount / count * 2);
        for (int i = 0; i < count - 1; i++) {
            array[i] = RandomUtils.nextLong(1, Math.min(max, amount));
            amount -= array[i];
        }
        array[count - 1] = amount;

        /** 한명이니깐 금액이 뿌린 금액 그대로 들어가야 합니다. **/
        //Then
        Assertions.assertThat(array[0]).isEqualTo(amount);
    }

}