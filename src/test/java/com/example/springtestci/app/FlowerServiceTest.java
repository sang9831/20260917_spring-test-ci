package com.example.springtestci.app;

import com.example.springtestci.domain.Flower;
import com.example.springtestci.domain.FlowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("FlowerService 단위 테스트")
class FlowerServiceTest {

    @Mock
    private FlowerRepository flowerRepository;

    private FlowerUseCase flowerService;

    @BeforeEach
    void setUp() {
        // 목 저장소를 주입한 테스트 대상 생성
        flowerService = new FlowerService(flowerRepository);
    }

    @Nested
    @DisplayName("count 메서드는")
    class Describe_count {

        @Test
        @DisplayName("저장소에 위임하여 개수를 반환한다")
        void it_delegates_to_repository() {
            // given
            // 저장소가 반환할 개수 스텁 설정
            given(flowerRepository.count()).willReturn(5L);

            // when
            // 꽃 개수 조회 요청
            long result = flowerService.count();

            // then
            // 저장소 결과를 그대로 반환해야 함
            assertThat(result).isEqualTo(5L);
            verify(flowerRepository, times(1)).count();
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Test
        @DisplayName("저장소에 위임하여 저장된 꽃을 반환한다")
        void it_delegates_to_repository() {
            // given
            // 저장할 꽃과 저장소 반환값 스텁 설정
            Flower flower = new Flower("장미", "빨강", 5000);
            given(flowerRepository.save(flower)).willReturn(flower);

            // when
            // 꽃 저장 요청
            Flower result = flowerService.save(flower);

            // then
            // 저장소 결과를 그대로 반환하고 위임 호출을 검증
            assertThat(result).isEqualTo(flower);
            verify(flowerRepository, times(1)).save(flower);
        }
    }

    @Nested
    @DisplayName("count 메서드는 (CI 실패 검증용)")
    class Describe_count_ci_check {

        @Test
        @DisplayName("의도적으로 실패하여 CI 검증을 확인한다")
        void it_intentionally_fails() {
            // given
            // 저장소가 반환할 개수 스텁 설정
            given(flowerRepository.count()).willReturn(5L);

            // when
            // 꽃 개수 조회 요청
            long result = flowerService.count();

            // then
            // CI 실패 트리거를 위한 의도적 오답 검증
            assertThat(result).isEqualTo(999L);
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Test
        @DisplayName("저장소에 위임하여 꽃 목록을 반환한다")
        void it_delegates_to_repository() {
            // given
            // 저장소가 반환할 꽃 목록 스텁 설정
            Flower flower = new Flower("튤립", "노랑", 3000);
            given(flowerRepository.findAll()).willReturn(List.of(flower));

            // when
            // 전체 꽃 목록 조회 요청
            List<Flower> result = flowerService.findAll();

            // then
            // 저장소 결과를 그대로 반환해야 함
            assertThat(result).containsExactly(flower);
            verify(flowerRepository, times(1)).findAll();
        }
    }
}
