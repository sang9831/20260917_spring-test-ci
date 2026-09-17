package com.example.springtestci.infra;

import com.example.springtestci.domain.Flower;
import com.example.springtestci.domain.FlowerRepository;

import java.util.List;

public class FlowerRepositoryImpl implements FlowerRepository {
    @Override
    public long count() {
        return 0;
    }

    @Override
    public Flower save(Flower flower) {
        return null;
    }

    @Override
    public List<Flower> findAll() {
        return List.of();
    }
}
