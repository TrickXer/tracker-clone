package com.gamestats.gamestats.models;

import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class GetPlayerInfo {
    String username;
    String tagline;

    int playTime;
    int wins;
    int loses;
    double winRate;
    String currentRank;
    int kills;
    int deaths;
    int assists;
    double kdRatio;
    int headshots;
    int totalshots;
    double headshotPercentage;

    public void reset() {
        playTime = 0;
        wins = 0;
        loses = 0;
        kills = 0;
        deaths = 0;
        assists = 0;
        headshots = 0;
        totalshots = 0;
    }

    public void getInfo() throws Exception {
        URL url = new URL("https://api.henrikdev.xyz/valorant/v3/matches/ap/" + username + "/" + tagline);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseStatus = connection.getResponseCode();

        if (responseStatus != 200)
            return;

        String json = "";
        Scanner scan = new Scanner(url.openStream());

        while (scan.hasNext()) {
            json += scan.nextLine();
        }
        scan.close();

        JSONParser parse = new JSONParser();
        JSONObject body = (JSONObject) parse.parse(json);
        JSONArray data = (JSONArray) body.get("data");

        for (int j = 0; j < data.size(); j++) {
            JSONObject players = (JSONObject) ((JSONObject) data.get(j)).get("players");
            JSONObject blue = (JSONObject) ((JSONObject) ((JSONObject) data.get(j)).get("teams")).get("blue");
            JSONArray allPlayers = (JSONArray) players.get("all_players");

            try {
                if ((Boolean) blue.get("has_won") == true)
                    wins++;
            } catch (NullPointerException e) {
            }

            try {
                if ((Boolean) blue.get("has_won") == false)
                    loses++;
            } catch (NullPointerException e) {
            }

            for (int i = 0; i < allPlayers.size(); i++) {
                JSONObject player = (JSONObject) allPlayers.get(i);

                if (player.get("name").equals(username) && player.get("tag").equals(tagline)) {
                    JSONObject session = (JSONObject) player.get("session_playtime");

                    try {
                        playTime += (Long) session.get("minutes");
                    } catch (NullPointerException e) {
                    }

                    try {
                        kills += (Long) ((JSONObject) player.get("stats")).get("kills");
                    } catch (NullPointerException e) {
                    }

                    try {
                        deaths += (Long) ((JSONObject) player.get("stats")).get("deaths");
                    } catch (NullPointerException e) {
                    }

                    try {
                        assists += (Long) ((JSONObject) player.get("stats")).get("assists");
                    } catch (NullPointerException e) {
                    }

                    try {
                        headshots += (Long) ((JSONObject) player.get("stats")).get("headshots");
                    } catch (NullPointerException e) {
                    }

                    try {
                        totalshots += (Long) ((JSONObject) player.get("stats")).get("bodyshots");
                    } catch (NullPointerException e) {
                    }

                    try {
                        totalshots += (Long) ((JSONObject) player.get("stats")).get("headshots");
                    } catch (NullPointerException e) {
                    }

                    try {
                        totalshots += (Long) ((JSONObject) player.get("stats")).get("legshots");
                    } catch (NullPointerException e) {
                    }

                    try {
                        if (j == 0)
                            currentRank = (String) player.get("currenttier_patched");
                    } catch (NullPointerException e) {
                        currentRank = "Unranked";
                    }

                    break;
                }
            }
        }

        try {
            winRate = (((double)wins * 100) / (wins + loses));
        } catch (ArithmeticException e) {
        }

        try {
            headshotPercentage = ((double)headshots * 100 / totalshots);
        } catch (ArithmeticException e) {
            headshotPercentage = 18;
        }

        try {
            kdRatio = (((double)kills * 100) / deaths) / 100;
        } catch (ArithmeticException e) {
            kdRatio = kills;
        }

        // stats.put("playtime", playTime);
        // stats.put("wins", wins);
        // stats.put("loses", loses);
        // stats.put("winrate", winRate);
        // stats.put("current_rank", currentRank);
        // stats.put("kills", kills);
        // stats.put("deaths", deaths);
        // stats.put("assists", assists);
        // stats.put("kd_ratio", kdRatio);
        // stats.put("headshots", headshots);
        // stats.put("totalshots", totalshots);
        // stats.put("headshot_percentage", headshotPercentage);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
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

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public String getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(String currentRank) {
        this.currentRank = currentRank;
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

    public double getKdRatio() {
        return kdRatio;
    }

    public void setKdRatio(double kdRatio) {
        this.kdRatio = kdRatio;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public int getTotalshots() {
        return totalshots;
    }

    public void setTotalshots(int totalshots) {
        this.totalshots = totalshots;
    }

    public double getHeadshotPercentage() {
        return headshotPercentage;
    }

    public void setHeadshotPercentage(double headshotPercentage) {
        this.headshotPercentage = headshotPercentage;
    }
}
