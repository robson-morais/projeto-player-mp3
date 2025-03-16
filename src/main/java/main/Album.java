package main;

import java.io.IOException;
import java.util.List;

public class Album extends TrackList{
    private String title;
    private String artist;
    private List<Track> tracks;

    public Album(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public Album() {
        this("","");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTracks(List<Track> tracks) throws IOException{
        if (tracks != null){
            this.tracks = tracks;
        } throw new IOException("A lista está vazia");

    }
    public String [] showOrderTracks(){
        String [] order = new String[this.tracks.size()+1];
        List<Track> thisTracks = this.tracks.stream().toList();
        order[0] = "'"+this.getTitle()+"' by "+this.artist;
        for (int t = 0; t<this.tracks.size();t++){
            order[t+1] = t+1+". "+thisTracks.get(t).getName();
        }
        return order;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "'" + this.title + "' by " + this.artist + "\n";
    }
}
