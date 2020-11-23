package com.kakaopay.moneyspread.funciton;

import com.kakaopay.moneyspread.domain.SpreadRequest;
import com.kakaopay.moneyspread.domain.SpreadResponse;
import org.springframework.http.HttpEntity;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;


/**
 *  로직 체크 함수
 *  */

public class Check {

    //spreadCheck
    public static Boolean SpreadCheck(HttpEntity<SpreadRequest> requestEntity) {

        SpreadRequest body = requestEntity.getBody();
        Boolean checkResult = true;

        //인원이 0명이거나 돈이 0원이면 돈을 뿌리지 않습니다.
        if(body.getTotalMoney()==0 || body.getPeople()==0){
            return false;
        }
        return checkResult;

    }

    //receiveCheck
    public static Long receiveCheck(Flux<SpreadResponse> selectedDate, String userId) {

        //뿌린사람이거나 받은사람이면 받지 못합니다.
        Long receiveCheck = selectedDate.filter(a->
                  a.getCreateUserId().equals(userId) //뿌린사람 check
                ||a.getReceivedId().equals(userId)   //받은사람 check
        ).count().block();

        //뿌린지 10분이 경과한경우에는 받지 못합니다.
        Long receiveCheckTime = selectedDate.filter(a->
                a.getCreateTime().plusMinutes(10).isBefore(LocalDateTime.now()) //뿌린시간 경과 체크
        ).count().block();

        return receiveCheck + receiveCheckTime;
    }

    //informationCheck
    public static Long selectCheck(Flux<SpreadResponse> selectedDate, String userId) {

        //뿌린사람인지 체크를 합니다.
        Long selectCheck = selectedDate.filter(a->
                        !a.getCreateUserId().equals(userId)                       //뿌린사람 check
        ).count().block();

        //뿌린지 7일이 경과하면 조회를 못합니다.
        Long selectCheckTime = selectedDate.filter(a->
                        a.getCreateTime().plusDays(7).isBefore(LocalDateTime.now()) //뿌린시간 경과 체크
        ).count().block();

        return selectCheck + selectCheckTime;
    }
}
