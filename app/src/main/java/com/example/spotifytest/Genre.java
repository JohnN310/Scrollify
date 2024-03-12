package com.example.spotifytest;

public class Genre {
    String genreName;
    int personalGenreRank;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre(String genreName, int personalGenreRank) {
        this(genreName);
        this.personalGenreRank = personalGenreRank;
    }

    public int getPersonalGenreRank() {
        return personalGenreRank;
    }

    public String getGenreName() {
        return genreName;
    }
}
