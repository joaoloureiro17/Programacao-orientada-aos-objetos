package Model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneroETempo extends Playlist{

    private String genero;
    private int duracao; //duracao total da playlist

    public GeneroETempo() {
        super();
        this.genero = "";
        this.duracao = 0;
    }

    public GeneroETempo(String nome, List<Musica> musicas, String genero, int tempoMaxSegundos) {
        super(nome, musicas);
        this.genero=genero;
        this.duracao=tempoMaxSegundos;
    }

    public GeneroETempo(GeneroETempo c) {
        super(c);
        this.genero= c.getGenero();
        this.duracao= c.getDuracao();
    }

    public String getGenero() {return genero;}

    public void setGenero(String genero) {this.genero = genero;}

    public int getDuracao() {return duracao;}

    public void setDuracao(int duracao) {this.duracao = duracao;}

    @Override
    public String toString() {return super.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        GeneroETempo g = (GeneroETempo) o;
        return this.getNome().equals(g.getNome()) && this.getMusicas().equals(g.getMusicas());
    }

    @Override
    public GeneroETempo clone() {
        return new GeneroETempo(this);
    }
}
