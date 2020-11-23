package com.kakaopay.moneyspread.domain;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 응답 정보
 * */
@Document("KakaoMoneySpread")
@Data
@Builder
public class SpreadResponse {

    @Id
    @NotNull
    private String roomId, createUserId;//요청자, 방이름

    @Id
    private String roomToken;   //방Tokem

    private int id;
    private long spreadMoney;   //배당금액
    private String receivedId;

    @Min(1)
    private long totalMoney;    //총 금액

    @Min(1)
    private int people;         //사람수

    private String spreadYN;    //받았는지 여부

    private LocalDateTime createTime; //뿌린시간

}
