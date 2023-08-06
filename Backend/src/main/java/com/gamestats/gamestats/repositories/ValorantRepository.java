package com.gamestats.gamestats.repositories;

import java.util.Optional;
import jakarta.transaction.Transactional;
import com.gamestats.gamestats.entities.Valorant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValorantRepository extends JpaRepository<Valorant, Integer> {

    Optional<Valorant> findByUsernameAndTagline(String username, String tagline);

    @Transactional
    @Modifying
    @Query("delete from Valorant where username = :username and tagline = :tagline")
    public Object deleteUser(@Param("username") String username, @Param("tagline") String tagline);

    public Iterable<Valorant> findByUsernameContainingOrderByUsernameAsc(String like);
}
