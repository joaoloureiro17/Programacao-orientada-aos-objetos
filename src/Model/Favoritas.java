package Model;

import java.util.List;

public class Favoritas extends Playlist{

    private String genero;

    public Favoritas() {
        super();
        this.genero="";
    }

    public Favoritas(String nome, List<Musica> musicas, String genero){
        super(nome, musicas);
        this.genero = genero;
    }

    public Favoritas(Favoritas f) {
        super(f);
        this.genero=f.getGenero();
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {return super.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Favoritas f = (Favoritas) o;
        return super.equals(f);
    }

    @Override
    public Favoritas clone() {
        return new Favoritas(this);
    }
}
