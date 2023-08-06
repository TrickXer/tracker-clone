package com.gamestats.gamestats.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "player_stats")
@JsonPropertyOrder({
        "valorant_id",
        "userName",
        "tagLine",
        "playTime",
        "wins",
        "loses",
        "winRate",
        "currentRank",
        "kills",
        "deaths",
        "assists",
        "kdRatio",
        "headshots",
        "headshotPercentage"
})
public class Valorant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "valorant_id")
    @JsonProperty("valorant_id")
    private int id;
    private String tagline;
    private String username;
    private int playtime;
    private int wins;
    private int loses;
    @Column(precision = 10, scale = 2)
    private double winrate;
    private String currentRank;
    private int kills;
    private int deaths;
    private int assists;
    private double kdRatio;
    private int headshots;
    private double headshotPercentage;

    public Valorant() {
        this.currentRank = "Unranked";
    }

    public Valorant(String username, String tagline, int playtime, int wins, int loses, double winrate,
            String current_rank, int kills, int deaths, int assists, double kd_ratio, int headshots,
            double headshotPercentage) {
        this.username = username;
        this.tagline = tagline;
        this.playtime = playtime;
        this.wins = wins;
        this.loses = loses;
        this.winrate = winrate;
        if (current_rank == "Unrated")
            this.currentRank = "Unranked";
        else this.currentRank = current_rank;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.kdRatio = kd_ratio;
        this.headshots = headshots;
        this.headshotPercentage = headshotPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("in_game_name")
    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    @JsonProperty("in_game_tag")
    public String getTagLine() {
        return tagline;
    }

    public void setTagLine(String tagline) {
        this.tagline = tagline;
    }

    @JsonProperty("playtime")
    public int getPlayTime() {
        return playtime;
    }

    public void setPlayTime(int playtime) {
        this.playtime = playtime;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    @JsonProperty("win_rate")
    public double getWinRate() {
        return winrate;
    }

    public void setWinRate(double winrate) {
        this.winrate = winrate;
    }

    @JsonProperty("current_rank")
    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String current_rank) {
        this.currentRank = current_rank;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @JsonProperty("kd_ratio")
    public double getKdRatio() {
        return kdRatio;
    }

    public void setKdRatio(double kd_ratio) {
        this.kdRatio = kd_ratio;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    @JsonProperty("headshot_%")
    public double getHeadshotPercentage() {
        return headshotPercentage;
    }

    public void setHeadshotPercentage(double headshot_percentage) {
        this.headshotPercentage = headshot_percentage;
    }

    @Override
    public String toString() {
        return "Valorant [id=" + id + ", userName=" + username + ", tagLine=" + tagline + ", playTime=" + playtime
                + ", wins=" + wins + ", loses=" + loses + ", winRate=" + winrate + ", currentRank=" + currentRank
                + ", kills=" + kills + ", deaths=" + deaths + ", assists=" + assists + ", kdRatio=" + kdRatio
                + ", headshots=" + headshots + ", headshotPercentage=" + headshotPercentage + "]";
    }
}
