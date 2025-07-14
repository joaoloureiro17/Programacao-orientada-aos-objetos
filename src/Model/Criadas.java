package Model;

import java.util.List;

public class Criadas extends Playlist {

    private Boolean visibilidade; // true = pública, false = privada

    public Criadas() {
        super();
        this.visibilidade = false;
    }

    public Criadas(String nome, List<Musica> musicas, Boolean visibilidade) {
        super(nome, musicas);
        this.visibilidade = visibilidade;
    }

    public Criadas(Criadas c) {
        super(c);
        this.visibilidade = c.visibilidade;
    }

    public Boolean getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(Boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

    @Override
    public String toString() {
        return super.toString() + "\nVisibilidade: " + (visibilidade ? "Pública" : "Privada");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Criadas c = (Criadas) o;
        return super.equals(c) && this.visibilidade.equals(c.getVisibilidade());
    }

    @Override
    public Criadas clone() {
        return new Criadas(this);
    }
}

