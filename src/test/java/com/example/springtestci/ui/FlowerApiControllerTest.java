package com.example.springtestci.ui;

import com.example.springtestci.app.FlowerUseCase;
import com.example.springtestci.domain.Flower;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlowerApiController.class)
@DisplayName("FlowerApiController 단위 테스트")
class FlowerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FlowerUseCase flowerUseCase;

    @Nested
    @DisplayName("GET /api/flowers는")
    class Describe_findAll {

        @Test
        @DisplayName("유스케이스에 위임하여 꽃 목록을 반환한다")
        void it_returns_flower_list() throws Exception {
            // given
            // 유스케이스가 반환할 꽃 목록 스텁 설정
            Flower flower = new Flower("장미", "빨강", 5000);
            given(flowerUseCase.findAll()).willReturn(List.of(flower));

            // when & then
            // 전체 꽃 목록 조회 요청 및 응답 검증
            mockMvc.perform(get("/api/flowers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("장미"))
                    .andExpect(jsonPath("$[0].color").value("빨강"))
                    .andExpect(jsonPath("$[0].price").value(5000));

            verify(flowerUseCase, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("POST /api/flowers는")
    class Describe_save {

        @Test
        @DisplayName("요청 본문의 꽃을 저장하고 저장된 꽃을 반환한다")
        void it_saves_and_returns_flower() throws Exception {
            // given
            // 요청 DTO와 저장 결과 스텁 설정
            FlowerDTO requestDto = new FlowerDTO("튤립", "노랑", 3000);
            Flower savedFlower = new Flower("튤립", "노랑", 3000);
            given(flowerUseCase.save(savedFlower)).willReturn(savedFlower);

            // when & then
            // 꽃 저장 요청 및 응답 검증
            mockMvc.perform(post("/api/flowers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("튤립"))
                    .andExpect(jsonPath("$.color").value("노랑"))
                    .andExpect(jsonPath("$.price").value(3000));

            verify(flowerUseCase, times(1)).save(savedFlower);
        }
    }

    @Nested
    @DisplayName("GET /api/flowers/count는")
    class Describe_count {

        @Test
        @DisplayName("유스케이스에 위임하여 저장된 꽃 개수를 반환한다")
        void it_returns_count() throws Exception {
            // given
            // 유스케이스가 반환할 개수 스텁 설정
            given(flowerUseCase.count()).willReturn(2L);

            // when & then
            // 꽃 개수 조회 요청 및 응답 검증
            mockMvc.perform(get("/api/flowers/count"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("2"));

            verify(flowerUseCase, times(1)).count();
        }
    }
}
