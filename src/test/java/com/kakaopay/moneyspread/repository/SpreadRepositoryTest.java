package com.kakaopay.moneyspread.repository;

import com.kakaopay.moneyspread.domain.SpreadResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class SpreadRepositoryTest {

   @Autowired
   private SpreadRepository spreadRepository;

    @Test
    @DisplayName("save Test")
    public void save() {
        //Give
        SpreadResponse sr = SpreadResponse.builder()
                        .createUserId("Donghak")
                        .people(5)
                        .totalMoney(2000)
                        .roomId("kakaoPayRoom")
                        .id(1)
                        .spreadYN("N")
                        .roomToken("AbC")
                        .build();
        //When
        Mono<SpreadResponse> newSr = spreadRepository.save(sr);

        //Then
        StepVerifier.create(newSr)
                .assertNext(user -> {
                    assertThat(user.getCreateUserId()).isEqualTo(sr.getCreateUserId());
                    assertThat(user.getPeople()).isEqualTo(sr.getPeople());
                    assertThat(user.getTotalMoney()).isEqualTo(sr.getTotalMoney());
                    assertThat(user.getRoomId()).isEqualTo(sr.getRoomId());
                    assertThat(user.getId()).isEqualTo(sr.getId());
                    assertThat(user.getSpreadYN()).isEqualTo(sr.getSpreadYN());
                    assertThat(user.getRoomToken()).isEqualTo(sr.getRoomToken());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Select Test")
    public void select(){

        //given
        save();

        //when
        Mono<SpreadResponse> newSr = spreadRepository.findById(1L);

        //Then
        StepVerifier.create(newSr)
                .assertNext(user -> {
                    assertThat(user.getCreateUserId()).isEqualTo("Donghak");
                    assertThat(user.getPeople()).isEqualTo(5);
                    assertThat(user.getTotalMoney()).isEqualTo(2000);
                    assertThat(user.getRoomId()).isEqualTo("kakaoPayRoom");
                    assertThat(user.getId()).isEqualTo(1);
                    assertThat(user.getSpreadYN()).isEqualTo("N");
                    assertThat(user.getRoomToken()).isEqualTo("Abc");
                }).verifyComplete();

    }

    @Test
    @DisplayName("Update Test")
    public void update() {

        //given
        Mono<SpreadResponse> newSr = spreadRepository.findById(1L);

        //when
        newSr.map(a -> {
            a.setSpreadYN("Y");
            return spreadRepository.save(a).subscribe();
        }).subscribe();

        //Then
        StepVerifier.create(newSr)
                .assertNext(user -> {
                    assertThat(user.getSpreadYN()).isEqualTo("Y");
                }).verifyComplete();
    }

    @Test
    @DisplayName("findByRoomToken Test")
    public void findByRoomToken_Test() {
        //given
        save();

        //when
        Flux<SpreadResponse> findRoom = spreadRepository.findByRoomToken("AbC").log();

        //Then
        StepVerifier.create(findRoom)
                .assertNext(user -> {
                    assertThat(user.getCreateUserId()).isEqualTo("Donghak");
                    assertThat(user.getPeople()).isEqualTo(5);
                    assertThat(user.getTotalMoney()).isEqualTo(2000);
                    assertThat(user.getRoomId()).isEqualTo("kakaoPayRoom");
                    assertThat(user.getId()).isEqualTo(1);
                    assertThat(user.getSpreadYN()).isEqualTo("N");
                }).verifyComplete();
    }

    @Test
    @DisplayName("findByRoomTokenAndSpreadYN_Test")
    public void findByRoomTokenAndSpreadYN_Test() {
        //given
        save();

        //when
        Flux<SpreadResponse> findRoom = spreadRepository.findByRoomTokenAndSpreadYN("AbC","N").log();

        //Then
        StepVerifier.create(findRoom)
                .assertNext(user -> {
                    assertThat(user.getCreateUserId()).isEqualTo("Donghak");
                    assertThat(user.getPeople()).isEqualTo(5);
                    assertThat(user.getTotalMoney()).isEqualTo(2000);
                    assertThat(user.getRoomId()).isEqualTo("kakaoPayRoom");
                    assertThat(user.getId()).isEqualTo(1);
                    assertThat(user.getSpreadYN()).isEqualTo("N");
                }).verifyComplete();
    }
}