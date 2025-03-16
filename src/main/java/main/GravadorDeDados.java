package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GravadorDeDados {
    // Atributo da classe:
    private String arquivosMusicas;

    //Construtores do testesFX de dados:
    public GravadorDeDados(String arquivosMusicas){
        this.arquivosMusicas = arquivosMusicas;
    }

    public GravadorDeDados(){
        this("Tracks.txt");
    }

    public void gravaDados(List<Track> tracks) throws IOException{
        BufferedWriter escritor = null;
        FileWriter arquivoLendo = new FileWriter(this.arquivosMusicas);
        try {
            escritor = new BufferedWriter(arquivoLendo);
            for (Track track: tracks){
                String linhaDaMusica = track.getName()+"#"+track.getArtist()+"#"+track.getAlbum();
                escritor.write(linhaDaMusica+"\n");
            }
        } finally {
            if (escritor != null){
                escritor.close();
            }
        }
    }

    public List<Track> lerDados() throws IOException {
        BufferedReader leitor = null;
        try {
            leitor = new BufferedReader(new FileReader(this.arquivosMusicas));
            List<Track> lista = new ArrayList<>();
            String linhaLida = null;

            do {
                linhaLida = leitor.readLine();
                if (linhaLida != null) {
                    String[] dados = linhaLida.split("#");
                    Track track = new Track(dados[0], dados[1], dados[2]);
                    lista.add(track);
                }
            } while (linhaLida != null);
            return lista;
        } finally {
            if (leitor != null) {
                leitor.close();
            }

        }
    }


}