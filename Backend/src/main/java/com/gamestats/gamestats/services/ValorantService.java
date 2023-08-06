package com.gamestats.gamestats.services;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.gamestats.gamestats.entities.User;
import com.gamestats.gamestats.entities.Valorant;
import com.gamestats.gamestats.models.GetPlayerInfo;
import com.gamestats.gamestats.repositories.ValorantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Service
public class ValorantService {

    @Autowired
    ValorantRepository valorantRepo;

    public Iterable<Valorant> getAll(int page) {
        PageRequest pages = PageRequest.of(page, 10);
        return valorantRepo.findAll(pages);
    }

    public Iterable<Valorant> getAll(String name) {
        return valorantRepo.findByUsernameContainingOrderByUsernameAsc(name);
    }

    public Valorant getPlayerById(int id) {
        Optional<Valorant> player = valorantRepo.findById(id);
        if (player.isPresent()) {
            return player.get();
        }
        return null;
    }

    public Valorant getPlayerByName(String username, String tagline) {
        Optional<Valorant> player = valorantRepo.findByUsernameAndTagline(username, tagline);
        if (player.isPresent()) {
            return player.get();
        }
        return null;
    }

    public Boolean saveLoginPlayer(GetPlayerInfo loginplayer, User user, int id) {
        Valorant player = new Valorant(loginplayer.getUsername(), loginplayer.getTagline(), loginplayer.getPlayTime(),
                loginplayer.getWins(), loginplayer.getLoses(), loginplayer.getWinRate(), loginplayer.getCurrentRank(),
                loginplayer.getKills(), loginplayer.getDeaths(), loginplayer.getAssists(), loginplayer.getKdRatio(),
                loginplayer.getHeadshots(), loginplayer.getHeadshotPercentage());
        try {
            user.setValorant(player);
            valorantRepo.save(player);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Valorant updatePlayerStats(Valorant player) {
        return valorantRepo.save(player);
    }

    public void deletePlayerStats(int id) {
        valorantRepo.deleteById(id);
    }

    public Iterable<Valorant> sortBy(String sort, int page) {
        if (sort == null) {
            return getAll(page);
        }
        String sortby[] = sort.split(",", 0);
        try {
            if (sortby[1].equals("desc")) {
                PageRequest pages = PageRequest.of(page, 1, Direction.DESC, sortby[0]);
                return valorantRepo.findAll(pages);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        PageRequest pages = PageRequest.of(page, 1, Sort.by(sortby[0]));
        return valorantRepo.findAll(pages);
    }

    public void deleteUser(String name, String tagline) {
        valorantRepo.deleteUser(name, tagline);
    }
}
