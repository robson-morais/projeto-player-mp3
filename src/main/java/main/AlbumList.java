package main;

import java.util.ArrayList;
import java.util.List;

public class AlbumList {
    private List<Album> albums;

    public AlbumList(List<Album> albums) {
        this.albums = albums;
    }

    public AlbumList() {
        this.albums = new ArrayList<>();
    }

    public void addAlbum(Album album) {
        if (!this.contains(album.getTitle())) {
            this.albums.add(album);
        }
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<String> getAlbumsTitles() {
        List<String> albumsNames = new ArrayList<>();

        for (int y = 0; y < this.albums.size(); y++) {
            albumsNames.add(albums.get(y).getTitle() + "\n");
        }
        return albumsNames;
    }

    public boolean contains(String title) {
        boolean contains = false;
        for (Album album1: this.albums) {
            if (album1.getTitle().equalsIgnoreCase(title)){
                contains = true;
            }
        }
        return contains;
    }

    public  String searchAlbumTracks(String albumName){
       //todo
       return null;
    }

    public String testeSearchAlbumTracks(String name) {
        String albumFoundStatus = "";
        Album albumFound = new Album();
        if (contains(name)) {
            for (Album alb: this.getAlbums()) {
                if (alb.getTitle().equalsIgnoreCase(name)) {
                    albumFound = alb;
                    break;
                }
            }
            for (int y = 0; y < albumFound.getTracks().size(); y++) {
                albumFoundStatus += albumFound.getTracks().get(y).getName();
            }
        } else {
            albumFoundStatus = "O álbum não existe na lista.";
        }
        return albumFoundStatus;
    }

    public String [] showAlbumSongs(Album album){
        int quant = album.getTracks().size();
        String [] namesSongs =new String[quant];
        for (int u=0;u<quant;u++){
            namesSongs[u] = album.getTracks().get(u).getName();
        }
        return namesSongs;
    }

    /*public String searchAlbums(String albumName) {
        //Este método mostra todos os dados de um álbum, tendo como chave de pesquisa o seu título;
        // Estas informações, no primeiro caso de teste será exibida em uma única String formatada.

        String albums = "";

        // Verifica se o album existe na Biblioteca:
        if (contains(albumName)) {

            // Se sim, cria uma lista com as faixas do album encontradas dentre as faixas que foram recuperadas pelo gravador/leitor:
            List<Track> albumFoundedTracks = searchAlbumTracks(albumName);
            //Instancia um artista que será o artista mostrado como autor do álbum;
            String artist = null;
            for (Track t : albumFoundedTracks) {
                artist = t.getArtist();
                break;
            }


            // Formatando a String para exibir as informações do álbum:
            albums = "'"+albumName+"'";
            for (Track track: albumFoundedTracks) {
                albums +=  track.getName();
            }
            return albums;
        } else {
            return "This album is not saved in your Library yet.\nWould you like to add it now? ( Y / N )";
        }

    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }*/
}
