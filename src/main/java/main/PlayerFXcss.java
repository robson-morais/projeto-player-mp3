package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerFXcss extends Application {

    private String username = "r0bstark";
    private AlbumList albumsList;
    private TrackList trackList;
    private GravadorDeDados recorder;
    private GravadorAlbum recorderAlbum;
    private String imagespath = PlayerFXcss.class.getResource("/images/").toString();

    MediaPlayer mediaPlayer; // Variável para controlar o MediaPlayer

    @Override
    public void start(Stage primaryStage) throws IOException {

        //Inicializando os gravadores de dados
        recorder = new GravadorDeDados();
        recorderAlbum = new GravadorAlbum();
        initializeData(); //Recuperando dados salvos

        //Tela Inicial:
        Label wecomeInfo = new Label("Bem-vindo,\n" + username + "!");
        wecomeInfo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label info1 = new Label("Tocadas recentemente:  ==========");
        info1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        Image image = new Image(imagespath + "profile/profile.jpg"); //--> imagem anexada no projeto
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);  // Largura da imagem
        imageView.setFitHeight(50);

        //Botões:
        Button libraryButton = new Button("BIBLIOTECA");
        Button exitButton = new Button("SAIR");
        Button profile = new Button();
        profile.setGraphic(imageView);

        libraryButton.setStyle("-fx-padding: 5px 10px; -fx-background-color: #006400; -fx-text-fill: white;");
        exitButton.setStyle("-fx-padding: 5px 10px; -fx-background-color: #006400; -fx-text-fill: white;");
        profile.setStyle("-fx-padding: 0;");

        HBox header = new HBox(100,wecomeInfo, profile);
        HBox buttons = new HBox(30, libraryButton, exitButton);
        buttons.setStyle("-fx-alignment: center;");
        VBox lastTracks = new VBox(10);
        ScrollPane lastTracksScroll = new ScrollPane(lastTracks);
        lastTracksScroll.setFitToWidth(true);

        VBox layout = new VBox(5, header, info1, lastTracksScroll,buttons);
        layout.setStyle("-fx-padding: 20px;");

        // Adicionando tracks recentes no início:
        if (trackList.getTracks() != null) {
            for (Track track: trackList.lastPlayedSongs()) {
                Button trackButton = new Button(track.getName() + "\nby "+track.getArtist());
                trackButton.setOnAction(e -> playTrack(track.getName()));
                trackButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");
                // Imagem do botão:
                Image trackCover = new Image(imagespath + "covers/" + track.getAlbum() + ".jpeg");
                ImageView trackCoverView = new ImageView(trackCover);
                trackCoverView.setFitWidth(30);
                trackCoverView.setFitHeight(30);
                trackButton.setGraphic(trackCoverView);
                lastTracks.getChildren().add(trackButton);}
        }

        Scene scene1 = new Scene(layout,300,300);
        primaryStage.setTitle("MusicPlayer");
        primaryStage.setScene(scene1);
        primaryStage.show();

        libraryButton.setOnAction(e -> showLibrary(primaryStage));
        exitButton.setOnAction(e -> primaryStage.close());
        profile.setOnAction(e-> profile(primaryStage));

    }
    // Criar uma nova cena para a biblioteca
    private void showLibrary(Stage primaryStage) {

        // Criando os elementos:
        Label searchLabel = new Label("Pesquisar: ");
        Label downloads = new Label("Seus Downloads");

        TextField inputField = new TextField();
        inputField.setPromptText("Digite sua pesquisa aqui: ");
        //TODO

        // Criando botões:
        Button searchButton = new Button("\uD83D\uDD0E");
        Button backButton = new Button(" Voltar ");
        Button songsButton = new Button (" Músicas ");
        Button albumsButton = new Button ("  Álbuns  ");
        Button artistsButton = new Button(" Artistas ");

        // Estilizando os elementos

        inputField.setStyle("-fx-padding: 4px 5px; -fx-font-size: 11px;");

        // Estilizando os botões:
        searchButton.setStyle("-fx-padding: 1px 4px; -fx-background-color: #006400; -fx-text-fill: white;");
        songsButton.setStyle("-fx-padding: 5px 80px; -fx-background-color: #006400; -fx-text-fill: white;");
        albumsButton.setStyle("-fx-padding: 5px 80px; -fx-background-color: #006400; -fx-text-fill: white;");
        artistsButton.setStyle("-fx-padding: 5px 80px; -fx-background-color: #006400; -fx-text-fill: white;");
        backButton.setStyle("-fx-padding: 5px 20px; -fx-text-fill: green;");

        searchLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-alignment: center;");
        downloads.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Colocando os elementos na cena:
        HBox searchHeader = new HBox(5, searchButton, inputField);
        searchHeader.setStyle("-fx-alignment: center;");

        VBox downloadsXsearch = new VBox(10,searchLabel,searchHeader,downloads);
        downloadsXsearch.setStyle("-fx-alignment: center;");

        VBox content = new VBox(5,songsButton, albumsButton, artistsButton);
        content.setStyle("-fx-alignment: center;");

        VBox libraryLayout = new VBox(30, downloadsXsearch,content,backButton);
        libraryLayout.setStyle("--fx-padding: 20px;-fx-alignment: center;");

        Scene libraryScene = new Scene(libraryLayout, 300,300);
        primaryStage.setScene(libraryScene);
        primaryStage.setTitle("Biblioteca");

        // Dando ações aos botões:
        backButton.setOnAction(e -> {
            try {
                this.start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        searchButton.setOnAction(e -> {
            String searchText = inputField.getText();
            try {
                showSearchResults(searchText, primaryStage);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        });

        albumsButton.setOnAction(e -> showAllAlbumListButtons(primaryStage));

        songsButton.setOnAction(e -> showAllSongs(trackList.getTracks()));

        artistsButton.setOnAction(e-> showAllArtists(primaryStage));
    }

    private void showAllSongs(List<Track> tracks){

        VBox songs = new VBox(10);
        VBox songsLayout = new VBox(10);

        Button back = new Button("Back");
        back.setStyle("-fx-padding: 10px 20px; -fx-background-color: #006400; -fx-text-fill: white;");

        //Ajustando todas as faixas para que seja feita a rolagem na tela:
        ScrollPane scrollPane = new ScrollPane(songs);

        // Orndenando as faixas:
        Collections.sort(tracks, Comparator.comparing(Track::getName));

        if (tracks != null) {
            for (Track track: tracks) {

                Button trackbutton = new Button(track.getName() + "\n" + track.getArtist());
                trackbutton.setOnAction(e -> playTrack(track.getName()));
                trackbutton.setStyle("-fx-font-size: 11px; -fx-background-color: white; -fx-font-weight: bold;");

                    Image image = new Image(imagespath + "covers/" + track.getAlbum() + ".jpeg");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setStyle("-fx-alignment: center;");
                    trackbutton.setGraphic(imageView);

                    HBox imageXinfo = new HBox(10,trackbutton);
                    songs.getChildren().add(imageXinfo);

            }
            scrollPane.setFitToWidth(true);
            songsLayout.getChildren().add(scrollPane);
            songsLayout.getChildren().add(back);
        }

        Scene scene = new Scene(songsLayout,300,300);
        Stage stage = new Stage();
        stage.setTitle("Músicas");
        back.setOnAction(e -> stage.close());

        stage.setScene(scene);
        stage.show();
    }

    private void showSearchResults (String searchText, Stage stage) throws IOException {
        VBox resultsLayout = new VBox(15);

        Button backButton = new Button(" Back ");
        backButton.setStyle("-fx-padding: 10px 20px; -fx-background-color: #006400; -fx-text-fill: white;");
        backButton.setOnAction(e -> showLibrary(stage));

        VBox resultsList = new VBox(10);
        ScrollPane results = new ScrollPane(resultsList);
        results.setFitToWidth(true);

        if (trackList.getTracks() != null) {
            for (Track track: trackList.getTracks()) {
                if (track.getName().toLowerCase().startsWith(searchText.toLowerCase())) {
                    Button trackButton = new Button(track.getName()+"\n song by "+track.getArtist());
                    trackButton.setOnAction(e -> playTrack(track.getName()));
                    trackButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");
                    // Imagem do botão:
                    Image trackCover = new Image(imagespath + "covers/" + track.getAlbum() + ".jpeg");
                    ImageView trackCoverView = new ImageView(trackCover);
                    trackCoverView.setFitWidth(50);
                    trackCoverView.setFitHeight(50);
                    trackButton.setGraphic(trackCoverView);
                    resultsList.getChildren().add(trackButton);
                }
            }
        }
        if (albumsList.getAlbums() != null) {
            for (Album albu: albumsList.getAlbums()) {
                if (albu.getTitle().toLowerCase().startsWith(searchText.toLowerCase())) {
                    Button albumButton = new Button(albu.getTitle() + "\nalbum by " + albu.getArtist());
                    albumButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");
                    // Criando a capa do álbum:
                    Image image = new Image(imagespath + "/covers/" + albu.getTitle() + ".jpeg");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    albumButton.setGraphic(imageView);
                    albumButton.setOnAction(e -> showAlbumPage(albu.getTitle(),stage));
                    resultsList.getChildren().add(albumButton);
                }
            }
        }
        // Para o resultado ser um artista:
        for (String artist: trackList.getArtistsNames()) {
            if (artist.toLowerCase().startsWith(searchText.toLowerCase())) {
                Button artistButton = new Button(artist + "\nartist");
                Image artistFile = new Image(imagespath + "/artists/" + artist + ".jpeg");
                ImageView artistImage = new ImageView(artistFile);
                artistImage.setFitWidth(50);
                artistImage.setFitHeight(50);
                artistButton.setGraphic(artistImage);
                artistButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");
                artistButton.setOnAction(e-> showArtistPage(artist));
                resultsList.getChildren().add(artistButton);
            }
        }
        resultsLayout.getChildren().add(results);
        resultsLayout.getChildren().add(backButton);
        Scene scene = new Scene(resultsLayout,300,300);
        stage.setScene(scene);
        stage.setTitle("Results for '" + searchText + "'");
    }

    private void showAlbumPage (String albumName, Stage stage) {
        Stage albumPageStage = new Stage();

        VBox albumLayout = new VBox(10);

        HBox coverTitleArtist = new HBox(10);

        Button backButton = new Button(" Back ");
        backButton.setStyle("-fx-padding: 10px 20px; -fx-background-color: #006400; -fx-text-fill: white;");

        String albumTitleScene = "";
        if (albumsList != null) {

            for (Album album2 : albumsList.getAlbums()) {

                if (album2.getTitle().equalsIgnoreCase(albumName)) {

                    Label albumTitle = new Label("\n" + album2.getTitle());
                    albumTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    Label albumArtist = new Label (album2.getArtist() + "\n" + trackList.searchAlbumTracks(albumName).size() + " downloaded tracks");
                    albumArtist.setStyle("-fx-font-size: 14px; -fx-font-weight: normal;");

                    VBox albumXartist = new VBox(10,albumTitle,albumArtist);

                    ScrollPane scrollTracks = scrollAlbumSongsButtons(album2.getTitle(),albumPageStage);
                    scrollTracks.setFitToWidth(true);

                    // Criando a capa do álbum:
                    Image image = new Image(imagespath + "covers/" + album2.getTitle() + ".jpeg");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    // Adicionando a capa, o titulo, o artista, e o total de tracks no HBox:
                    coverTitleArtist.getChildren().add(imageView);
                    coverTitleArtist.getChildren().add(albumXartist);

                    albumLayout.getChildren().add(coverTitleArtist);
                    albumLayout.getChildren().add(scrollTracks);
                    albumTitleScene = album2.getTitle();
                    break;
                }
            }
            albumLayout.getChildren().add(backButton);
        }
        Scene albumPage = new Scene(albumLayout,300,300);
        backButton.setOnAction(e -> albumPageStage.close());
        albumPageStage.setScene(albumPage);
        albumPageStage.setTitle("Álbum: " + albumTitleScene);
        albumPageStage.show();
    }

    private ScrollPane scrollAlbumSongsButtons (String albumName1, Stage stage) {

        VBox vboxTracks = new VBox(10);

        List<Track> tracks = trackList.searchAlbumTracks(albumName1);

        int c = 1;

        for (Track track3: tracks) {
            if (track3.getAlbum().equalsIgnoreCase(albumName1)) {

                Button trackButton = new Button((c++) + ". " + track3.getName());
                trackButton.setOnAction(e -> playTrack(track3.getName()));
                trackButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: normal;");
                vboxTracks.getChildren().add(trackButton);
            }
        }

        ScrollPane vboxTracksscroll = new ScrollPane(vboxTracks);
        vboxTracksscroll.setFitToWidth(true);

        return vboxTracksscroll;
    }

    private void playTrack (String trackName) {
        // Para qualquer música que esteja tocando, parar antes de reproduzir outra
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Stage playBack = new Stage();

        for (Track track : trackList.getTracks()) {
            if (track.getName().equalsIgnoreCase(trackName)) {
                // Construa o caminho para o arquivo de áudio
                String filePath = "C:/Users/Robson/Music/" + track.getAlbum() + "/" + track.getName() + ".mp3";
                File file = new File(filePath);

                if (file.exists()) {
                    // Cria o objeto Media e MediaPlayer
                    Media media = new Media(file.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);

                    // Label para as informações da faixa:
                    Label songInfoLabel = new Label("\n\n" + track.getName() + "\n"+ track.getArtist());
                    songInfoLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

                    // Botões Play e Pause
                    Button playButton = new Button("\u23F5");
                    playButton.setOnAction(e -> mediaPlayer.play());
                    Button pauseButton = new Button("\u23F8");
                    pauseButton.setOnAction(e -> mediaPlayer.pause());
                    Button stopButton = new Button("\u23F9");
                    stopButton.setOnAction(e -> mediaPlayer.stop());

                    Button backButton = new Button("Back");
                    backButton.setStyle("-fx-padding: 5px 10px; -fx-background-color: #006400; -fx-text-fill: white; -fx-alignment: center-left;");

                    // Barra de progresso
                    Slider progressBar = new Slider();
                    progressBar.setMin(0);
                    progressBar.setMax(100);
                    progressBar.setValue(0);
                    progressBar.setStyle("-fx-padding: 5px;");

                    // Temporizador para exibir o tempo atual e duração total
                    Label timeLabel = new Label("00:00 / 00:00");

                    // Listener para atualizar a barra de progresso e o temporizador
                    mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                        Duration currentTime = mediaPlayer.getCurrentTime();
                        Duration totalDuration = mediaPlayer.getTotalDuration();

                        progressBar.setMax(totalDuration.toSeconds());
                        progressBar.setValue(currentTime.toSeconds());
                        timeLabel.setText(formatTime(currentTime, totalDuration));
                    });

                    // Define o comportamento ao término da faixa para resetar a barra de progresso
                    mediaPlayer.setOnEndOfMedia(() -> {
                        progressBar.setValue(0);
                        timeLabel.setText("00:00 / 00:00");
                    });

                    // Imagem do álbum
                    Image image = new Image(imagespath + "covers/" + track.getAlbum() + ".jpeg");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setStyle("-fx-alignment: center;");

                    // Layout HBox para os botões
                    HBox controlBox = new HBox(10, playButton, pauseButton, stopButton, timeLabel);
                    controlBox.setStyle("-fx-alignment: center;"); // Centraliza os botões no HBox

                    // Layout VBox para centralizar a imagem, barra de progresso e botões
                    VBox root = new VBox(10, imageView, songInfoLabel, progressBar, controlBox, backButton);
                    root.setStyle("-fx-alignment: center;"); // Centraliza tudo no VBox


                    // Configura a cena e exibe o player
                    Scene scene = new Scene(root, 300,300);
                    playBack.setScene(scene);
                    playBack.setTitle(track.getArtist() + " - " + track.getName());

                    backButton.setOnAction(e-> {playBack.close();});

                    playBack.show();
                    // Inicia a reprodução da faixa
                    mediaPlayer.play();
                    return;
                } else {
                    System.out.println("A faixa não está em seus downloads locais");
                }
            }
        }
        System.out.println("A faixa não está em seus downloads.");

    }

    private void showArtistPage(String artistName) {

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-padding: 10px 20px; -fx-background-color: #006400; -fx-text-fill: white;");

        HBox info = new HBox(10);
        VBox artistLayout = new VBox(10);
        artistLayout.getChildren().add(info);
        String artistNameScene = "";

        if (trackList.getTracks() != null) {
            for (String artist: trackList.getArtistsNames()) {
                int cont = trackList.countArtistSongs(artist);
                if (artist.toLowerCase().startsWith(artistName.toLowerCase())) {

                    VBox infoNameXsongs = new VBox(5);

                    Label infoName = new Label(artist);
                    infoName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                    infoNameXsongs.getChildren().add(infoName);
                    Label infoSongs = new Label(cont + " songs\n" + trackList.searchArtistAlbums(artist).size() + " albums");
                    infoSongs.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    infoNameXsongs.getChildren().add(infoSongs);

                    Image artistFile = new Image(imagespath + "/artists/" + artist + ".jpeg");
                    ImageView artistImage = new ImageView(artistFile);
                    artistImage.setFitWidth(100);
                    artistImage.setFitHeight(100);

                    info.getChildren().add(artistImage);
                    info.getChildren().add(infoNameXsongs);

                    artistNameScene = artist;
                }
            }
            List<Track> artistTracks = trackList.searchArtistSongs(artistName);
            Collections.shuffle(artistTracks);

            VBox firstSongs = new VBox(5);
            ScrollPane firstSongsSP = new ScrollPane(firstSongs);
            firstSongsSP.setFitToWidth(true);

            for (int y = 0; y < artistTracks.size(); y++) {
                Track track1 = artistTracks.get(y);
                Button trackButton = new Button(track1.getName());
                trackButton.setOnAction(e -> playTrack(track1.getName()));
                trackButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");
                // Imagem do botão:
                Image trackCover = new Image(imagespath + "covers/" + track1.getAlbum() + ".jpeg");
                ImageView trackCoverView = new ImageView(trackCover);
                trackCoverView.setFitWidth(30);
                trackCoverView.setFitHeight(30);
                trackButton.setGraphic(trackCoverView);

                firstSongs.getChildren().add(trackButton);
            }
            artistLayout.getChildren().add(firstSongsSP);
            artistLayout.getChildren().add(backButton);
        }

        Stage artistStage = new Stage();
        backButton.setOnAction(e -> artistStage.close());
        Scene scene = new Scene(artistLayout,300,300);
        artistStage.setScene(scene);
        artistStage.setTitle("Artista: " + artistNameScene);
        artistStage.show();


    }

    private void showAllArtists(Stage stage){
        VBox list = new VBox(10);
        ScrollPane fullList = new ScrollPane(list);
        fullList.setFitToWidth(true);

        Button backButton = new Button(" Back ");
        backButton.setStyle("-fx-padding: 10px 20px; -fx-background-color: #006400; -fx-text-fill: white;");
        backButton.setOnAction(e -> showLibrary(stage));

        for (String artist: trackList.getArtistsNames()) {
            Button artistButton = new Button(artist + "\n" + trackList.countArtistSongs(artist) + " songs, " + trackList.searchArtistAlbums(artist).size() + " albums");
            Image artistFile = new Image(imagespath + "/artists/" + artist + ".jpeg");
            ImageView artistImage = new ImageView(artistFile);
            artistImage.setFitWidth(50);
            artistImage.setFitHeight(50);
            artistButton.setGraphic(artistImage);
            artistButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");
            artistButton.setOnAction(e-> showArtistPage(artist));
            list.getChildren().add(artistButton);
        }
        VBox artistsLayout = new VBox(10);
        artistsLayout.getChildren().add(fullList);
        artistsLayout.getChildren().add(backButton);
        Scene scene = new Scene(artistsLayout,300,300);
        stage.setScene(scene);
        stage.setTitle("Artistas");
        stage.show();
    }

    // Método para formatar o tempo
    private String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed % 60;

        int intDuration = (int) Math.floor(duration.toSeconds());
        int durationMinutes = intDuration / 60;
        int durationSeconds = intDuration % 60;

        if (duration.isUnknown()) {
            return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
        } else {
            return String.format("%02d:%02d / %02d:%02d",
                    elapsedMinutes, elapsedSeconds,
                    durationMinutes, durationSeconds);
        }
    }

    //todo: lista todos os álbuns baixados em forma de botões:
    private void showAllAlbumListButtons(Stage stage) {

        Button backButton = new Button(" Back ");
        backButton.setStyle("-fx-padding: 10px 20px; -fx-background-color: #006400; -fx-text-fill: white;");

        // Ações do botões:
        backButton.setOnAction(e -> showLibrary(stage));

        VBox ListOfAlbumsButtons = new VBox(10);
        VBox ListLayout = new VBox(15);

        if (albumsList.getAlbums() != null) {
            for (Album album: albumsList.getAlbums()) {

                Button albumButton = new Button(album.getTitle() + "\n" + album.getArtist());
                albumButton.setStyle("-fx-font-size: 12px; -fx-background-color: white; -fx-font-weight: bold;");

                // Criando a capa do álbum:

                System.out.println("A capa do album para "+ album.getTitle() + " foi encontrada");
                //Imagem do álbum
                Image image = new Image(imagespath + "covers/" + album.getTitle() + ".jpeg");
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                albumButton.setGraphic(imageView);
                albumButton.setOnAction(e -> showAlbumPage(album.getTitle(),stage));
                ListOfAlbumsButtons.getChildren().add(albumButton);
            }
            ScrollPane scrollAlbums = new ScrollPane(ListOfAlbumsButtons);
            scrollAlbums.setFitToWidth(true);

            ListLayout.getChildren().add(scrollAlbums);
            ListLayout.getChildren().add(backButton);
        } else {
            Label infoLabel = new Label("A lista da Álbums baixados está vazia");
            infoLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
            ListOfAlbumsButtons.getChildren().add(infoLabel);
        }
        ListOfAlbumsButtons.setStyle("--fx-padding: 20px;");

        Scene scene = new Scene(ListLayout,300,300);
        stage.setTitle("Álbuns");
        stage.setScene(scene);
    }

    private void profile (Stage primaryStage) {

        Button changeUsername = new Button ("      Change username      ");
        Button changeSystemLanguage = new Button ("Change System Language");
        Button back = new Button ("Back");

        Label profile = new Label ("Profile");
        Label userName = new Label (username);
        Label profileInfo = new Label ("Joined Oct, 2024");

        profile.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        userName.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        profileInfo.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        //todo Configurando os botões:
        back.setStyle("-fx-padding: 5px 10px; -fx-background-color: #0064cc; -fx-text-fill: white;");
        changeUsername.setStyle("-fx-padding: 5px 50px; -fx-background-color: #006400; -fx-text-fill: white;");
        changeSystemLanguage.setStyle("-fx-padding: 5px 50px; -fx-background-color: #006400; -fx-text-fill: white;");

        //TODO username e profileInfo em um vbox
        VBox usernameXinfo = new VBox(5,userName,profileInfo);
        usernameXinfo.setStyle("--fx-padding: 20px;-fx-alignment: center;");

        // TODO Profile + (username + info) em um vbox
        VBox profileHeaderAndInfo = new VBox(5, profile,usernameXinfo);
        profileHeaderAndInfo.setStyle("--fx-padding: 20px;-fx-alignment: center;");

        VBox profileLayout = new VBox(40,profileHeaderAndInfo,changeUsername,changeSystemLanguage,back);
        profileLayout.setStyle("--fx-padding: 20px;-fx-alignment: center;");

        Scene scene = new Scene(profileLayout, 300,300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Perfil");

        // todo Buttons actions:
        back.setOnAction(e -> {
            try {
                this.start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        changeUsername.setOnAction(e-> username = JOptionPane.showInputDialog("Username: "));
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initializeData() {
        try {
            List<Track> tracksRecovered = recorder.lerDados();
            trackList = new TrackList(tracksRecovered);
        } catch (IOException e) {
            trackList = new TrackList();
        }

        try {
            List<Album> albumsRecovered = recorderAlbum.lerDadosAlbums();
            albumsList = new AlbumList(albumsRecovered);
        } catch (IOException e) {
            albumsList = new AlbumList();
            System.out.println("Erro ao recuperar albums.");
        }
    }
}