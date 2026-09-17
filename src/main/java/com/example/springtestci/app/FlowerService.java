package com.example.springtestci.app;

import com.example.springtestci.domain.Flower;
import com.example.springtestci.domain.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerService implements FlowerUseCase {

    private final FlowerRepository flowerRepository;

    @Override
    public long count() {
        // 저장소에 위임하여 개수 조회
        return flowerRepository.count();
    }

    @Override
    public Flower save(Flower flower) {
        // 저장소에 위임하여 꽃 저장
        return flowerRepository.save(flower);
    }

    @Override
    public List<Flower> findAll() {
        // 저장소에 위임하여 전체 목록 조회
        return flowerRepository.findAll();
    }
}