package com.example.spotifytest;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class YourProfile {
    private String username;
    private String password;
    private String name;
    private String code;
    private String friends;
    private String invites;
    private List<Song> top5SongList;
    private List<Artist> top5ArtistList;
    private List<Genre> top5GenreList;
    private List<SongWrapped> pastWrappedList;

    public YourProfile() {
        top5SongList = new ArrayList<>();
        top5ArtistList = new ArrayList<>();
        top5GenreList = new ArrayList<>();
        pastWrappedList = new ArrayList<>();
    }

    public YourProfile(String username, String password, String name, String code, String friends, String invites) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.code = code;
        this.friends = friends;
        this.invites = invites;
    }

    public YourProfile(String username, String password, String name, String code) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String string) {
        this.code = code;
    }

    public String getFriends() { return friends; }

    public void setFriends(String friends) { this.friends = friends; }
    public String getInvites() { return invites; }

    public void setInvites(String invite) { this.invites = invite; }

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

    public void savePastWrapped(String date, String timeRange, List<String> top5) {
        SongWrapped newWrapped = new SongWrapped(date, timeRange, top5);
        this.pastWrappedList.add(newWrapped);
    }
}


