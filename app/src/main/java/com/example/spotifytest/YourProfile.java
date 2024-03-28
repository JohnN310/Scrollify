package com.example.spotifytest;

import java.util.ArrayList;
import java.util.List;

public class YourProfile {
    private List<Song> top5SongList;
    private List<Artist> top5ArtistList;
    private List<Genre> top5GenreList;

    public YourProfile() {
        top5SongList = new ArrayList<>();
        top5ArtistList = new ArrayList<>();
        top5GenreList = new ArrayList<>();
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


