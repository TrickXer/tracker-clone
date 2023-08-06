package com.gamestats.gamestats.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamestats.gamestats.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    public Optional<Image> findByName(String nameString);
}
