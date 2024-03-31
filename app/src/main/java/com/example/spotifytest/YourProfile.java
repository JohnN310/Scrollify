package com.example.spotifytest;

import java.util.ArrayList;
import java.util.List;

public class YourProfile {
    private String username;
    private String password;
    private String yourName;
    private String code;
    private String currentToken;
    private List<Song> top5SongList;
    private List<Artist> top5ArtistList;
    private List<Genre> top5GenreList;

    public YourProfile() {
        currentToken = "";
        top5SongList = new ArrayList<>();
        top5ArtistList = new ArrayList<>();
        top5GenreList = new ArrayList<>();
    }

    public YourProfile(String username, String password, String name, String code) {
        this();
        this.yourName = name;
        this.username = username;
        this.password = password;
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalCode() {
        return code;
    }

    public void setPersonalCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Song> getTop5SongList() {
        return top5SongList;
    }

    public List<Artist> getTop5ArtistList() {
        return top5ArtistList;
    }

    public List<Genre> getTop5GenreList() {
        return top5GenreList;
    }

    public String getYourName() {
        return yourName;
    }

    public void setYourName(String name) {
        this.yourName = name;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public boolean verifyLogin(String username, String password) {
        return (this.username.equals(username) && this.password.equals(password));
    }
}


