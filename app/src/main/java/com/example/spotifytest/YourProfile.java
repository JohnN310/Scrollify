package com.example.spotifytest;

import java.util.ArrayList;
import java.util.List;

public class YourProfile {
    private String username;
    private String password;
    private String name;
    private String code;
    private List<Song> top5SongList;
    private List<Artist> top5ArtistList;
    private List<Genre> top5GenreList;

    public YourProfile() {
        top5SongList = new ArrayList<>();
        top5ArtistList = new ArrayList<>();
        top5GenreList = new ArrayList<>();
    }

    public YourProfile(String username, String password, String name, String code) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalToken() {
        return code;
    }

    public void setPersonalToken(String personalToken) {
        this.code = personalToken;
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
}


