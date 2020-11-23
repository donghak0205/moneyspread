package com.kakaopay.moneyspread.service;

import com.kakaopay.moneyspread.domain.SpreadRequest;
import com.kakaopay.moneyspread.domain.SpreadResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface
 */

@Service
public interface SpreadService {

    String save(HttpEntity<SpreadRequest> requestEntity);   //뿌린돈 저장

    Mono<SpreadResponse> update(SpreadResponse updateFlux); //돈을 받으면 인적사항 저장

    Flux<SpreadResponse> findSpreadMoney(HttpEntity<SpreadRequest> requestEntity); //아직 안찾아간돈 찾기

    Flux<SpreadResponse> findInformation(HttpEntity<SpreadRequest> requestEntity); //돈뿌리기 정보 조회

}
