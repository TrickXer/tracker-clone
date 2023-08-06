package com.gamestats.gamestats.repositories;

import java.util.Optional;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamestats.gamestats.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UserRepository extends JpaRepository<User, Integer> {

        // @Query("select id, username, tagline from User")
        // public User findUser();

        @Query("from User where username = :username and tagline = :tagline and password = :password")
        public Optional<User> loginUser(@Param("username") String username, @Param("tagline") String tagline,
                        @Param("password") String password);

        @Transactional
        @Modifying
        @Query("update User set password = :password where username = :username and tagline = :tagline")
        public Object updateUser(@Param("username") String username, @Param("tagline") String tagline,
                        @Param("password") String password);

        @Transactional
        @Modifying
        @Query("delete from User where username = :username and tagline = :tagline")
        public Object deleteUser(@Param("username") String username, @Param("tagline") String tagline);

        public Iterable<User> findByUsernameContainingOrderByUsernameAsc(String like);

        public Optional<User> findByEmail(String email);
}
