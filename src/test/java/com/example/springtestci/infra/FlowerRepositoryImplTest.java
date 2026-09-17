package com.example.springtestci.infra;

import com.example.springtestci.domain.Flower;
import com.example.springtestci.domain.FlowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("FlowerRepositoryImpl 단위 테스트")
class FlowerRepositoryImplTest {

    @Autowired
    private FlowerJpaRepository flowerJpaRepository;

    private FlowerRepository flowerRepository;

    @BeforeEach
    void setUp() {
        // 실제 JPA 저장소를 주입한 테스트 대상 생성
        flowerRepository = new FlowerRepositoryImpl(flowerJpaRepository);
    }

    @Nested
    @DisplayName("count 메서드는")
    class Describe_count {

        @Test
        @DisplayName("저장된 꽃이 없으면 0을 반환한다")
        void it_returns_zero_when_empty() {
            // given
            // 아무 것도 저장하지 않은 상태

            // when
            // 저장된 꽃 개수 조회
            long result = flowerRepository.count();

            // then
            // 초기 상태이므로 개수는 0
            assertThat(result).isZero();
        }

        @Test
        @DisplayName("꽃을 저장한 만큼 증가한 개수를 반환한다")
        void it_returns_saved_count() {
            // given
            // 꽃 두 개를 저장
            flowerRepository.save(new Flower("장미", "빨강", 5000));
            flowerRepository.save(new Flower("튤립", "노랑", 3000));

            // when
            // 저장된 꽃 개수 조회
            long result = flowerRepository.count();

            // then
            // 저장한 개수만큼 반환되어야 함
            assertThat(result).isEqualTo(2L);
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Test
        @DisplayName("전달받은 꽃을 그대로 반환한다")
        void it_returns_saved_flower() {
            // given
            // 저장할 꽃 객체 준비
            Flower flower = new Flower("장미", "빨강", 5000);

            // when
            // 꽃 저장 요청
            Flower result = flowerRepository.save(flower);

            // then
            // 저장 결과는 null이 아니고 원본과 동일해야 함
            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(flower);
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Test
        @DisplayName("저장된 꽃이 없으면 빈 리스트를 반환한다")
        void it_returns_empty_list_when_nothing_saved() {
            // given
            // 아무 것도 저장하지 않은 상태

            // when
            // 전체 꽃 목록 조회
            List<Flower> result = flowerRepository.findAll();

            // then
            // 저장된 것이 없으므로 빈 리스트여야 함
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("저장한 꽃들을 모두 포함하여 반환한다")
        void it_returns_all_saved_flowers() {
            // given
            // 꽃 두 개를 저장
            Flower rose = new Flower("장미", "빨강", 5000);
            Flower tulip = new Flower("튤립", "노랑", 3000);
            flowerRepository.save(rose);
            flowerRepository.save(tulip);

            // when
            // 전체 꽃 목록 조회
            List<Flower> result = flowerRepository.findAll();

            // then
            // 저장한 꽃이 모두 포함되어야 함
            assertThat(result).containsExactlyInAnyOrder(rose, tulip);
        }
    }
}
