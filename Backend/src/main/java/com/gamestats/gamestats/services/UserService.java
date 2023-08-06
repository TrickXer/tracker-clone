package com.gamestats.gamestats.services;

import java.util.Optional;

import com.gamestats.gamestats.entities.User;
import com.gamestats.gamestats.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    ValorantService valorantservice;

    public Iterable<User> getAll(int page) {
        PageRequest pages = PageRequest.of(page, 10);
        return userRepo.findAll(pages);
    }

    public User getByEmail(String email) {
        Optional<User> user = userRepo.findByEmail(email);

        if (user.isPresent())
            return user.get();
        return null;
    }

    public Iterable<User> getAll(String like) {
        return userRepo.findByUsernameContainingOrderByUsernameAsc(like);
    }

    public User loginUser(User loginUser) {
        Optional<User> user = userRepo.loginUser(loginUser.getUsername(), loginUser.getTagline(),
                loginUser.getPassword());
        if (user.isPresent())
            return user.get();
        return null;
    }

    public User getUser(int id) {
        Optional<User> user = userRepo.findById(id);
        return user.get();
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

    public Boolean deleteUser(int id, String password) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            if ((user.get().getPassword()).equals(password))
                userRepo.deleteById(id);
            return (user.get().getPassword()).equals(password);
        }
        return null;
    }

    public void deleteUser(String name, String tagline) {
        userRepo.deleteUser(name, tagline);
        valorantservice.deleteUser(name, tagline);
    }
}
