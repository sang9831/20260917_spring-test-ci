package com.example.springtestci.app;

import com.example.springtestci.domain.Flower;

import java.util.List;

public interface FlowerUseCase {
    long count();
    Flower save(Flower flower);
    List<Flower> findAll();
}
