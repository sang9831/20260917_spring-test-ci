package com.example.springtestci.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("FlowerRepository 계약 테스트")
class FlowerRepositoryTest {

    private FlowerRepository flowerRepository;

    @BeforeEach
    void setUp() {
        // Mockito로 인터페이스 목 객체 생성
        flowerRepository = mock(FlowerRepository.class);
    }

    @Nested
    @DisplayName("count 메서드는")
    class Describe_count {

        @Test
        @DisplayName("저장된 꽃의 개수를 반환한다")
        void it_returns_count() {
            // given
            // count 호출 시 반환값 스텁 설정
            given(flowerRepository.count()).willReturn(3L);

            // when
            // 저장된 꽃 개수 조회
            long result = flowerRepository.count();

            // then
            // 스텁한 값과 일치하는지 검증
            assertThat(result).isEqualTo(3L);
            verify(flowerRepository, times(1)).count();
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Test
        @DisplayName("전달받은 꽃을 저장하고 반환한다")
        void it_returns_saved_flower() {
            // given
            // 저장할 꽃과 반환값 스텁 설정
            Flower flower = new Flower("장미", "빨강", 5000);
            given(flowerRepository.save(flower)).willReturn(flower);

            // when
            // 꽃 저장 요청
            Flower result = flowerRepository.save(flower);

            // then
            // 저장 결과와 호출 인자 검증
            assertThat(result).isEqualTo(flower);
            verify(flowerRepository, times(1)).save(flower);
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Test
        @DisplayName("저장된 꽃 목록을 반환한다")
        void it_returns_flower_list() {
            // given
            // 조회 결과로 반환할 꽃 목록 스텁 설정
            Flower flower = new Flower("튤립", "노랑", 3000);
            given(flowerRepository.findAll()).willReturn(List.of(flower));

            // when
            // 전체 꽃 목록 조회
            List<Flower> result = flowerRepository.findAll();

            // then
            // 스텁한 목록과 일치하는지 검증
            assertThat(result).containsExactly(flower);
            verify(flowerRepository, times(1)).findAll();
        }
    }
}
