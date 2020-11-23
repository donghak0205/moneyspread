package com.kakaopay.moneyspread.controller;

import com.kakaopay.moneyspread.domain.SpreadRequest;
import com.kakaopay.moneyspread.domain.SpreadResponse;
import com.kakaopay.moneyspread.network.Header;
import com.kakaopay.moneyspread.service.SpreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static com.kakaopay.moneyspread.funciton.Check.*;
import static org.springframework.http.ResponseEntity.ok;


/**
 * 돈 뿌리기 Controller
 * **/
@RestController
@RequestMapping("/api/spread")
public class SpreadRestController {

    private final SpreadService spreadService;

    @Autowired
    public SpreadRestController(SpreadService spreadService) {
        this.spreadService = spreadService;
    }

    //1. 돈뿌리기 API
    @PostMapping("/spreadMoney")
    public ResponseEntity<Header<String>> SpreadMoney(HttpEntity<SpreadRequest> requestEntity) {

        //check
        if (!SpreadCheck(requestEntity)) {
            return ok().body(Header.ERROR("뿌릴 금액이나 인원을 0이상 입력해주세요."));
        }

        //return
        return ok().body(Header.OK(spreadService.save(requestEntity)));
    }


    //2. 받기 API
    @PostMapping("/receiveMoney")
    public Flux<Header<Long>> receiveMoney(HttpEntity<SpreadRequest> requestEntity) {

        String userId = requestEntity.getHeaders().get("X-USER-ID").get(0).toString();
        Long receiveCheck = receiveCheck(spreadService.findInformation(requestEntity), userId);

        //Check
        if (receiveCheck > 0) {
            return Flux.just(Header.ERROR("돈을 이미가져가져갔거나 돈을 뿌리셨으므로 돈을 받을수 없습니다. 확인바랍니다."));
        }

        //Return
        Flux<SpreadResponse> selectFlux = spreadService.findSpreadMoney(requestEntity).log();
        return selectFlux.map(a -> {
                    a.setReceivedId(userId);
                    spreadService.update(a).subscribe();
                    return a.getSpreadMoney();
                }
        ).map(n -> Header.OK(n)).defaultIfEmpty(Header.ERROR("Token 정보를 확인하거나 더 이상 받아갈 돈이 없습니다. 확인바랍니다."));

    }


    //3.조회 API
    @PostMapping("/selectSpread")
    public Flux<Header<SpreadResponse>> selectSpread(HttpEntity<SpreadRequest> requestEntity) {

        String userId = requestEntity.getHeaders().get("X-USER-ID").get(0).toString();
        Flux<SpreadResponse> selectedSpread = spreadService.findInformation(requestEntity);
        Long selectCheck = selectCheck(spreadService.findInformation(requestEntity), userId);

        //Check
        if (selectCheck > 0) {
            return Flux.just(Header.ERROR("돈을 뿌린사람이 아니거나 돈을 뿌린지가 7일이 경과되었습니다."));
        }

        //return
        return selectedSpread.map(n -> Header.OK(n)).defaultIfEmpty(Header.ERROR("Token 정보를 확인하세요."));
    }

}
