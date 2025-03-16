package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GravadorAlbum {
    private String arquivosAlbums;

    public GravadorAlbum(String arquivosAlbums) {
        this.arquivosAlbums = arquivosAlbums;
    }

    public GravadorAlbum(){
        this("Albums.txt");
    }

    public void gravaAlbum(List<Album> albums) throws IOException {
        BufferedWriter escritor = null;
        FileWriter arquivoLendo = new FileWriter(this.arquivosAlbums);
        try {
            escritor = new BufferedWriter(arquivoLendo);
            for (Album album: albums){
                    String linhaDoAlbum = album.getTitle() + "#" + album.getArtist();
                    escritor.write(linhaDoAlbum + "\n");
            }
        } finally {
            if (escritor != null){
                escritor.close();
            }
        }
    }

    public List<Album> lerDadosAlbums() throws IOException {
        BufferedReader leitor = null;
        try {
            leitor = new BufferedReader(new FileReader(this.arquivosAlbums));
            List<Album> lista = new ArrayList<>();
            String linhaLida = null;

            do {
                linhaLida = leitor.readLine();
                if (linhaLida != null) {
                    String[] dados = linhaLida.split("#");
                    Album album  = new Album(dados[0], dados[1]);
                    lista.add(album);
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
