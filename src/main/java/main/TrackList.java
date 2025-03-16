package main;

import exceptions.TrackException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class TrackList {
    private List<Track> tracks;

    public TrackList(List<Track> tracks) {
        this.tracks = tracks;
    }
    public TrackList() {
        this.tracks = new ArrayList<>();
    }


    public void add(Track track) throws TrackException {
        if (this.tracks.contains(track)){
            throw new TrackException("Track already exists");
        } else {
            this.tracks.add(track);
        }
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public HashSet<String> getArtistsNames() {

        HashSet<String> lista1 = new HashSet<>();
        for (Track track : this.tracks) {
            lista1.add(track.getArtist());
        }
        return lista1;
    }

    public List<String> getAlbumsTitles(){
        List<String> albumsTitles = new ArrayList<>();
        for (Track track: this.tracks) {
            if ((albumsTitles.contains(track.getAlbum())) == false){
                albumsTitles.add(track.getAlbum()+"\n");
            }
        } return albumsTitles;
    }

    public List<Track> searchAlbumTracks(String album){
        List<Track> list = new ArrayList<>();
        for (Track track: this.tracks){
            if (track.getAlbum().equalsIgnoreCase(album)){
                list.add(track);
            }
        }
        return list;
    }

    public Track searchSingleTrack(String title) throws IOException{
        Track trackFound = null;
        for (Track track: this.tracks){
            if (track.getName().equalsIgnoreCase(title)) {
                trackFound = track;
            }
        }
        if (trackFound == null) {
            throw new IOException("This song doesn't exist in the list");
        } return trackFound;
    }

    public List<String> searchTracks(String trackName) {
        List<String> foundedTracks = new ArrayList<>();
        for (Track track: this.tracks){
            if (track.getName().equalsIgnoreCase(trackName)) {
                foundedTracks.add(track.toString());
            }
        }
        return foundedTracks;
    }

    public boolean searchArtist(String artistName) {
        boolean artistFound = false;
        for (Track track: this.tracks) {
            if (track.getArtist().equalsIgnoreCase(artistName)) {
                artistFound = true;
            }
        }
        return artistFound;
    }

    public List<String> searchArtistSongsNames(String artistName) {
        List<String> artistSongs = new ArrayList<>();
        for (Track track: this.tracks) {
            if (track.getArtist().equalsIgnoreCase(artistName)) {
                artistSongs.add(track.getName()+"\n");
            }
        }
        return artistSongs;
    }

    public List<Track> searchArtistSongs(String artistName) {
        List<Track> artistSongs = new ArrayList<>();
        for (Track track: this.tracks) {
            if (track.getArtist().equalsIgnoreCase(artistName)) {
                artistSongs.add(track);
            }
        }
        return artistSongs;
    }

    public HashSet<String> searchArtistAlbums(String artistName) {
        //Exibe os álbums de um artista;
        HashSet<String> artistAlbums = new HashSet<>();
        for (Track track: this.tracks) {
            if (track.getArtist().equalsIgnoreCase(artistName)) {
                artistAlbums.add(track.getAlbum());
            }
        }
        return artistAlbums;
    }
    public String searchAlbumInfo (String albumName, AlbumList albumList) throws IOException {
        String finalString = "";
        String albumFoundedTitle = "";
        String artistAlbumFounded = "";

        List<Track> tracksForAlbumFounded = new ArrayList<>();

        // Verifica se o album existe na Biblioteca:
        if (albumList.contains(albumName)) {

            for (Track track: this.tracks) {
                if (track.getAlbum().equalsIgnoreCase(albumName)) {
                    tracksForAlbumFounded.add(track);
                    artistAlbumFounded = track.getArtist();
                    albumFoundedTitle = track.getAlbum();
                }
            }
            // Formatando a string de exibição do álbum:
            finalString = "'"+albumFoundedTitle+"'"+" by "+artistAlbumFounded+"\n\n";
            for (int i = 0; i < tracksForAlbumFounded.size(); i++) {
                finalString += i+1+".  "+tracksForAlbumFounded.get(i).getName()+"\n";
            }
            finalString += "\nTotal : "+tracksForAlbumFounded.size()+" tracks";
        } else {
            finalString = "No matches...";
        }
        return finalString;
    }

    public String searchAlbumOrdenedTracks (String albumName, AlbumList albumList) {
        String finalString = "";

        List<Track> tracksForAlbumFounded = new ArrayList<>();

        // Verifica se o album existe na Biblioteca:
        if (albumList.contains(albumName)) {

            for (Track track: this.tracks) {
                if (track.getAlbum().equalsIgnoreCase(albumName)) {
                    tracksForAlbumFounded.add(track);
                }
            }
            // Formatando a string de exibição do álbum:
            for (int i = 0; i < tracksForAlbumFounded.size(); i++) {
                finalString += i+1+".  "+tracksForAlbumFounded.get(i).getName()+"\n";
            }
        } else {
            finalString = "No matches...";
        }
        return finalString;
    }

    public List<Track> lastPlayedSongs() { //Este método pega um quantidade de faixas para exibir seus títulos na página incial;
        List<Track> lastTracks = new ArrayList<>();
        Collections.shuffle(this.tracks);
        for (int k = 0; k < 4; k++){
            lastTracks.add(this.tracks.get(k));
        }
        return lastTracks;
    }

    public int countArtistSongs(String artistName) {
        int count = 0;
        for (String artist: getArtistsNames()) {
            if (artist.equalsIgnoreCase(artistName)) {
                for (Track track: this.tracks) {
                    if (track.getArtist().equalsIgnoreCase(artist)){
                        count +=1;
                    }
                }
            }
        } return count;
    }




}