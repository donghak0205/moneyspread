package com.kakaopay.moneyspread.repository;

import com.kakaopay.moneyspread.domain.SpreadResponse;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 *
 * Webflux Base Repositiory
 *
 * **/
@Repository
public interface SpreadRepository extends ReactiveCrudRepository<SpreadResponse, Long> {

    Flux<SpreadResponse> findByRoomTokenAndSpreadYN(String token, String spreadYN); //해당토큰으로 아직 안받아간 돈 찾기

    Flux<SpreadResponse> findByRoomToken(String token); //토큰으로 방정보 찾기

}
