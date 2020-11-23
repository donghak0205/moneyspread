package com.kakaopay.moneyspread.domain;

import lombok.*;

/**
 * 요청 정보
 **/
@Builder
@Getter
public class SpreadRequest {

    private String roomId, createUserId;//요청자, 방이름

    private String roomToken;   //방Token

    private long totalMoney;    //총 금액
    private int people;         //사람수
}
