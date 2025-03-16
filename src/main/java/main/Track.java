package main;

public class Track implements Comparable<Track> {
    private String name;
    private String artist;
    private String album;


    public Track(String name, String artist, String album) {
        this.name = name;
        this.artist = artist;
        this.album = album;
    }

    public Track() {
        this("", "", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }



    @Override
    public String toString() {
        return"'"+this.name+"'"+ " by " + this.artist+"\n";
    }

    public String playback(){
        return"===========\nPlaying '"+this.name+"'\nby "+this.artist+"\n==========";
    }

    @Override
    public int compareTo(Track o) {
        return 0;
    }
}