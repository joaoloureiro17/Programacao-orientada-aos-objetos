package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Album implements Serializable {

    private String nome;
    private String interprete;
    private List<Musica> musicas;

    public Album(){
        this.nome= "";
        this.interprete= "";
        this.musicas= new ArrayList<>();
    }

    public Album(String nome, String interprete, List<Musica> musicas){
        this.nome= nome;
        this.interprete= interprete;
        setMusicas(musicas);
    }

    public Album(Album album) {
        this.nome = album.getNome();
        this.interprete = album.getInterprete();
        this.musicas = album.getMusicas();
    }

    public String getNome(){return this.nome;}

    public void setNome(String nome){this.nome = nome;}

    public String getInterprete(){return this.interprete;}

    public void setInterprete(String interprete){this.interprete = interprete;}

    public List<Musica> getMusicas() {
        return this.musicas.stream().map(Musica::clone).collect(Collectors.toList());
    }
    public void setMusicas(List<Musica> musicas){
        this.musicas = new ArrayList<>();
        for(Musica m : musicas) {
            this.musicas.add(m.clone());
        }
    }

    public String toString(){return "Nome do Album :"+ this.nome+ "\n"+ "Interprete: " +this.interprete+ "\n"+ "Musicas: " +this.musicas;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Album a = (Album) o;
        return this.nome.equals(a.getNome()) && this.interprete.equals(a.getInterprete()) && this.musicas.equals(a.getMusicas());
    }

    @Override
    public Album clone() {
        return new Album(this);
    }
}