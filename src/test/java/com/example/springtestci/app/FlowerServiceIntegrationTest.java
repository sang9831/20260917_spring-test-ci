package com.example.springtestci.app;

import com.example.springtestci.domain.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("FlowerService 통합 테스트")
class FlowerServiceIntegrationTest {

    @Autowired
    private FlowerUseCase flowerService;

    @BeforeEach
    void setUp() {
        // 테스트 격리를 위해 각 테스트 시작 전 저장된 꽃이 없음을 전제
    }

    @Nested
    @DisplayName("save와 count는")
    class Describe_save_and_count {

        @Test
        @DisplayName("실제 저장소를 통해 저장된 꽃 개수를 반영한다")
        void it_reflects_saved_count() {
            // given
            // 저장 전 초기 개수 확인
            long before = flowerService.count();

            // when
            // 실제 DB에 꽃 저장
            flowerService.save(new Flower("장미", "빨강", 5000));

            // then
            // 저장 이후 개수가 1 증가해야 함
            assertThat(flowerService.count()).isEqualTo(before + 1);
        }
    }

    @Nested
    @DisplayName("save는")
    class Describe_save {

        @Test
        @DisplayName("저장한 꽃 내용을 그대로 반환한다")
        void it_returns_saved_flower() {
            // given
            // 저장할 꽃 객체 준비
            Flower flower = new Flower("튤립", "노랑", 3000);

            // when
            // 실제 DB에 꽃 저장 요청
            Flower saved = flowerService.save(flower);

            // then
            // 반환된 값이 원본과 동일한 내용이어야 함
            assertThat(saved).isEqualTo(flower);
        }
    }

    @Nested
    @DisplayName("findAll은")
    class Describe_findAll {

        @Test
        @DisplayName("실제 저장소에 저장된 꽃 목록을 모두 반환한다")
        void it_returns_all_saved_flowers() {
            // given
            // 서로 다른 꽃 두 개를 실제 DB에 저장
            Flower rose = flowerService.save(new Flower("장미", "빨강", 5000));
            Flower tulip = flowerService.save(new Flower("튤립", "노랑", 3000));

            // when
            // 전체 꽃 목록 조회
            List<Flower> result = flowerService.findAll();

            // then
            // 저장한 꽃이 모두 포함되어야 함
            assertThat(result).contains(rose, tulip);
        }
    }
}
