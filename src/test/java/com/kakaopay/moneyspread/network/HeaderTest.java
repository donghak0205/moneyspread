package com.kakaopay.moneyspread.network;

import com.kakaopay.moneyspread.domain.SpreadResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeaderTest {

    @Test
    @DisplayName("Header_Test")
    public void findByRoomTokenAndSpreadYN_Test() {

        //give
        SpreadResponse sr = SpreadResponse.builder()
                .createUserId("Donghak")
                .people(5)
                .totalMoney(2000)
                .roomId("kakaoPayRoom")
                .id(1)
                .build();

        //when
        Header<SpreadResponse> header = Header.OK(sr);
        Header<String> headerError = Header.ERROR("카카오페이 안녕하세요!");

        //Then

        //1. OK
        Assertions.assertThat(header.getData().getCreateUserId()).isEqualTo("Donghak");
        Assertions.assertThat(header.getData().getPeople()).isEqualTo(5);
        Assertions.assertThat(header.getData().getTotalMoney()).isEqualTo(2000);
        Assertions.assertThat(header.getData().getRoomId()).isEqualTo("kakaoPayRoom");
        Assertions.assertThat(header.getData().getId()).isEqualTo(1);

        //2. ERROR
        Assertions.assertThat(headerError.getDescription()).isEqualTo("카카오페이 안녕하세요!");
    }
}