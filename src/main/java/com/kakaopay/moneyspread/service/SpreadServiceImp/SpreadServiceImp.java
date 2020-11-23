package com.kakaopay.moneyspread.service.SpreadServiceImp;

import com.kakaopay.moneyspread.domain.SpreadRequest;
import com.kakaopay.moneyspread.domain.SpreadResponse;
import com.kakaopay.moneyspread.repository.SpreadRepository;
import com.kakaopay.moneyspread.service.SpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.kakaopay.moneyspread.funciton.Generator.divide;
import static com.kakaopay.moneyspread.funciton.Generator.getToken;

/**
 *  Service Imp
 **/

@Component
public class SpreadServiceImp implements SpreadService {

    SpreadRepository spreadRepository;

    @Autowired
    public SpreadServiceImp(SpreadRepository spreadRepository) {
        this.spreadRepository = spreadRepository;
    }

    /**
     * 뿌린돈을 분배하여 저장합니다.
     * **/
    @Override
    public String save(HttpEntity<SpreadRequest> requestEntity) {

        String roomToken = getToken().trim();

        /** 뿌린돈을 분배합니다. **/
        long[] divide = divide(requestEntity.getBody().getTotalMoney(),requestEntity.getBody().getPeople());

        /** 인원수에 맞게 분배된 돈을 저장합니다. **/
        for(int i=0;i<requestEntity.getBody().getPeople();i++) {
            SpreadResponse response = SpreadResponse.builder()
                    .roomId(requestEntity.getHeaders().getFirst("X-ROOM-ID").trim())
                    .roomToken(roomToken)
                    .createUserId(requestEntity.getHeaders().getFirst("X-USER-ID").trim())
                    .spreadMoney(divide[i])
                    .id(i + 1)
                    .spreadYN("N")
                    .createTime(LocalDateTime.now())
                    .receivedId("null")
                    .build();
            spreadRepository.save(response).subscribe();
        }

        return roomToken;
    }

    /**
     * 토큰으로 정보를 조회합니다.
     * **/
    @Override
    public Flux<SpreadResponse> findInformation(HttpEntity<SpreadRequest> requestEntity) {
        return spreadRepository.findByRoomToken(requestEntity.getBody().getRoomToken());
    }


    /**
     * 돈을 받으면 돈을 받은 사람의 인적사항을 저장합니다.
     * **/
    @Override
    public Mono<SpreadResponse> update(SpreadResponse updateFlux) {
        SpreadResponse response = SpreadResponse.builder()
                .roomId(updateFlux.getRoomId())
                .roomToken(updateFlux.getRoomToken())
                .createUserId(updateFlux.getCreateUserId())
                .spreadMoney(updateFlux.getSpreadMoney())
                .id(updateFlux.getId())
                .spreadYN("Y")
                .createTime(updateFlux.getCreateTime())
                .receivedId(updateFlux.getReceivedId())
                .build();
        return spreadRepository.save(response);

    }

    /**
     * 아직 분배되지 않은 돈을 찾아줍니다.
     * **/
    @Override
    public Flux<SpreadResponse> findSpreadMoney(HttpEntity<SpreadRequest> requestEntity) {
        return  spreadRepository.findByRoomTokenAndSpreadYN(requestEntity.getBody().getRoomToken(), "N").take(1);
    }

}
