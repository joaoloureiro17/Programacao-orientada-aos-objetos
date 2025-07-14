package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {

    private String nome;
    private List<Musica> musicas;

    public Playlist(){
        this.nome="";
        this.musicas=new ArrayList<>();
    }

    public Playlist(String nome, List<Musica> musicas) {
        this.nome=nome;
        setMusicas(musicas);
    }

    public Playlist(Playlist playlist) {
        this.nome=playlist.nome;
        this.musicas=playlist.musicas;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musica> getMusicas() {
        return this.musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = new ArrayList<>();
        for (Musica musica : musicas) {
            this.musicas.add(musica.clone());
        }
    }

    public void adicionarMusica(Musica musica) {
        if (musica != null) {
            this.musicas.add(musica.clone());
        }
    }

    public String toString() {
        return "Nome da Playlist: " + this.nome + "\n"+ "Musicas: " + this.musicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Playlist p = (Playlist) o;
        return this.nome.equals(p.nome) && this.musicas.equals(p.musicas);
    }

    @Override
    public Playlist clone() {
        return new Playlist(this);
    }
}
