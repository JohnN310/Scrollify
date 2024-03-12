package com.example.spotifytest;

public class Artist {
    private String artistName;
    private int monthlyListeners; //number of monthly listeners the artist has

    private int personaArtistRank; //the rank of the song in your library
    public Artist(String artistName, int monthlyListeners) {
        this.artistName = artistName;
        this.monthlyListeners = monthlyListeners;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getMonthlyListeners() {
        return monthlyListeners;
    }

    public int getPersonaArtistRank() {
        return personaArtistRank;
    }
}
