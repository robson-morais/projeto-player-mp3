package baseversion;

import main.*;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main2_test {
    public static void main(String[] args) throws IOException {

        AlbumList albumsList = null;
        TrackList trackList = null;

        // Instanciando os gravadores de dados:
        GravadorDeDados recorder = new GravadorDeDados();
        GravadorAlbum recorderAlbum = new GravadorAlbum();

        // Recuperando a lista geral de faixas salvas da última execução:
        try {
            // Recuperando as últimas faixas salvas:
            List<Track> tracksRecovered = recorder.lerDados();
            trackList = new TrackList(tracksRecovered);

        } catch (IOException e) {
            trackList = new TrackList();
        }

        // Recuperando a lista de albuns salvos da última execução:
        try {
            // Recuperando o toString dos albums:
            List<Album> albumsRecovered = recorderAlbum.lerDadosAlbums();
            albumsList = new AlbumList(albumsRecovered);
        } catch (IOException e) {
            albumsList = new AlbumList();
            JOptionPane.showMessageDialog(null, "Erro ao recuperar albums.");
        }
        String username = "r.morais";
        int artistCount = 0;
        boolean sair = false;
        while (!sair) {
            int i = Integer.parseInt(JOptionPane.showInputDialog(
                    "=========================| MusicPlayer |=========================\nWelcome Back, "+username+"!\n" +
                            "                                                                                                            (1) YOUR LIBRARY\n" +
                            "                                                                                                            (2) EXIT\n" +
                            "\nLast added tracks: ==============================================\n\n"));

            switch (i) {
                case 1:
                    while (i != 7) {
                        i = Integer.parseInt(JOptionPane.showInputDialog(
                                "=========================| MusicPlayer |=========================\n \n" +
                                        trackList.getTracks().size() + " Tracks      1 PlayLists                                                                            (6) PROFILE\n" +
                                        albumsList.getAlbums().size() + " Albums       3 Artists                                                                                 (7) BACK\n" +
                                        "\n(Menu) =======================================================\n" +
                                        "(1) Search\n" +
                                        "(2) My Songs\n" +
                                        "(3) My Albums\n" +
                                        "(4) My PlayLists\n" +
                                        "(5) My Artists\n \n"));
                        switch (i) {
                            case 1:
                                while (i != 4) {
                                    i = Integer.parseInt(JOptionPane.showInputDialog(
                                            "=========================| Your Library |=========================\n \n" +
                                                    trackList.getTracks().size() + " Tracks      1 PlayLists                                                                              (4) BACK\n" +
                                                    albumsList.getAlbums().size() + " Albums       3 Artists\n" +
                                                    "\n============= (Search) ========================================\n" +
                                                    "                               (1) Song\n" +
                                                    "                               (2) Album\n" +
                                                    "                               (3) Artist\n\n\n \n"));
                                    switch (i) {
                                        case 1:
                                            String keySong = JOptionPane.showInputDialog("Type the song's name: ");
                                            List<String> foundedTracks = trackList.searchTracks(keySong);
                                            if (foundedTracks.size() > 0) {
                                                JOptionPane.showMessageDialog(null, "Songs found:  =====================\n \n" + foundedTracks + "\n \n============================");
                                            } else {
                                                JOptionPane.showMessageDialog(null, "No matches...");
                                            }
                                            break;
                                        case 2:
                                            String keyAlbum = JOptionPane.showInputDialog("Album title: ");

                                            JOptionPane.showMessageDialog(null, "Album found:  =====================\n\n"+trackList.searchAlbumInfo(keyAlbum,albumsList));

                                            break;
                                        case 3:
                                            String keyArtist = JOptionPane.showInputDialog("Type artist name: ");
                                            if (trackList.searchArtist(keyArtist)) {
                                                JOptionPane.showMessageDialog(null, keyArtist + "'s =====================\n \nDownloaded albums:\n" + trackList.searchArtistAlbums(keyArtist) +
                                                        "\n \nDownloaded songs:\n" + trackList.searchArtistSongs(keyArtist));
                                            }
                                            break;
                                    }
                                }
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(null, "Downloaded songs:  =====================\n \n" + trackList.getTracks());
                                break;
                            case 3:
                                if (albumsList != null) {
                                    JOptionPane.showMessageDialog(null, "Downloaded Albums:  ====================\n \n" + albumsList.getAlbumsTitles());
                                }
                                break;
                            case 4:
                                // PlayLists:
                                JOptionPane.showMessageDialog(null, "Your PlayLists:  =====================\n \nDownloaded songs: " + trackList.getTracks().size() + "\n\n");
                                break;
                            case 5:
                                    JOptionPane.showMessageDialog(null, "Saved artists:\n" + trackList.getArtistsNames());
                                    artistCount += trackList.getArtistsNames().size();
                                break;
                            case 6:
                                while (i != 2) {
                                    i = Integer.parseInt(JOptionPane.showInputDialog(
                                            "=========================| MusicPlayer |=========================\n \n" +
                                                    "Username: "+username+"                                                                                              (2) BACK\n" +
                                                    "Joined in October, 2024\n" +
                                                    "\n(Settings) =======================================================\n" +
                                                    "(1) Change username\n" +
                                                    "( ) \n" +
                                                    "( ) \n" +
                                                    "( ) \n" +
                                                    "( ) \n \n"));
                                    switch (i) {
                                        case 1:
                                            username = JOptionPane.showInputDialog("New username: ");
                                            break;
                                    }

                                }
                                break;
                        }
                    }
                    break;
                case 2:
                    sair = true;
                    break;
                /*case 7:
                    String nome = JOptionPane.showInputDialog("Título: ");
                    String artista = JOptionPane.showInputDialog("Artista: ");
                    String album = JOptionPane.showInputDialog("Album: ");
                    Track track = new Track(nome, artista, album);
                    try {
                        trackList.add(track);
                        JOptionPane.showMessageDialog(null, "Track sucessufully added!");
                    } catch (TrackException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;*/
            }
            try {
                recorder.gravaDados(trackList.getTracks());
                recorderAlbum.gravaAlbum(albumsList.getAlbums());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
}
