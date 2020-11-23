package com.kakaopay.moneyspread.funciton;

import com.kakaopay.moneyspread.domain.SpreadRequest;
import com.kakaopay.moneyspread.domain.SpreadResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static com.kakaopay.moneyspread.funciton.Check.SpreadCheck;
import static org.junit.jupiter.api.Assertions.*;

class CheckTest {

    //SpreadChec
    @Test
    public void SpreadCheck_Test(){

        //Given
        int people = 0;
        int spreadMoney = 0;
        Boolean bResult = true;

        //when
        if(people==0 || spreadMoney ==0){
            bResult=false;
        }

        //Then
        Assertions.assertThat(bResult).isFalse();
    }

    @Test
    public void receiveCheck_Test(){

        //Given - 뿌린돈 정보
       Flux<SpreadResponse> selectedDate = Flux.just(SpreadResponse.builder()
                .createUserId("Dong") //뿌린사람
                .receivedId("hak")    //받은사람
                .createTime(LocalDateTime.now()) //뿌린시간
                .build());

        String requestUser = "Donghak"; //요청자


        //When - 뿌린사람이거나 받은사람이면 받지 못합니다
        Long receiveCheck = selectedDate.filter(a->
                          a.getCreateUserId().equals(requestUser) //뿌린사람 check
                        ||a.getReceivedId().equals(requestUser)   //받은사람 check
        ).count().block();

        //When - 뿌린지 10분이 경과한경우에는 받지 못합니다.
        Long receiveCheckTime = selectedDate.filter(a->
                a.getCreateTime().plusMinutes(10).isBefore(LocalDateTime.now()) //뿌린시간 경과 체크
        ).count().block();


        //Then
        Assertions.assertThat(receiveCheck).isEqualTo(0);
        Assertions.assertThat(receiveCheckTime).isEqualTo(0);

    }


    @Test
    public void selectCheck_Test(){

        //Given - 뿌린돈 정보
        Flux<SpreadResponse> selectedDate = Flux.just(SpreadResponse.builder()
                .createUserId("Donghak") //뿌린사람
                .receivedId("hak")    //받은사람
                .createTime(LocalDateTime.now()) //뿌린시간
                .build());

        String requestUser = "Donghak"; //요청자


        //When  뿌린사람아니면 조회를 못합니다.
        Long receiveCheck = selectedDate.filter(a->
                !a.getCreateUserId().equals(requestUser)                       //뿌린사람 check
        ).count().block();

        //When - 뿌린지 10분이 경과한경우에는 받지 못합니다.
        Long receiveCheckTime = selectedDate.filter(a->
                a.getCreateTime().plusDays(7).isBefore(LocalDateTime.now()) //뿌린시간 경과 체크
        ).count().block();

        //Then
        Assertions.assertThat(receiveCheck).isEqualTo(0);
        Assertions.assertThat(receiveCheckTime).isEqualTo(0);
    }


}