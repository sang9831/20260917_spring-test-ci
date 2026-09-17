package com.example.springtestci;

import com.example.springtestci.ui.FlowerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@DisplayName("Flower API 스모크 테스트")
class FlowerApiSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("애플리케이션 컨텍스트는")
    class Describe_context {

        @Test
        @DisplayName("정상적으로 구동된다")
        void it_starts_up() {
            // given & when
            // 스프링 컨텍스트가 이미 로드된 상태

            // then
            // 컨텍스트 로딩 실패 없이 테스트가 도달하면 성공
            assertThat(restTemplate).isNotNull();
        }
    }

    @Nested
    @DisplayName("꽃 API 전체 흐름은")
    class Describe_flower_api_flow {

        @Test
        @DisplayName("등록부터 조회까지 실제 서버를 통해 정상 동작한다")
        void it_works_end_to_end() {
            // given
            // 초기 저장된 꽃 개수 확인
            ResponseEntity<Long> beforeCount = restTemplate.getForEntity("/api/flowers/count", Long.class);
            assertThat(beforeCount.getStatusCode()).isEqualTo(HttpStatus.OK);
            long before = beforeCount.getBody();

            // when
            // 실제 HTTP 요청으로 꽃 등록
            FlowerDTO request = new FlowerDTO("장미", "빨강", 5000);
            ResponseEntity<FlowerDTO> saveResponse =
                    restTemplate.postForEntity("/api/flowers", request, FlowerDTO.class);

            // then
            // 등록 응답 검증
            assertThat(saveResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(saveResponse.getBody()).isEqualTo(request);

            // when
            // 등록 이후 개수와 목록 재조회
            ResponseEntity<Long> afterCount = restTemplate.getForEntity("/api/flowers/count", Long.class);
            ResponseEntity<FlowerDTO[]> listResponse =
                    restTemplate.getForEntity("/api/flowers", FlowerDTO[].class);

            // then
            // 개수가 1 증가하고 목록에 등록한 꽃이 포함되어야 함
            assertThat(afterCount.getBody()).isEqualTo(before + 1);
            assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(listResponse.getBody()).contains(request);
        }
    }
}
