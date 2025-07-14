package Model;

import java.io.Serializable;

public abstract class PlanoSub implements Serializable {

    public abstract PlanoSub clone();
    public abstract void atribuirPontos(Utilizador u);

    public boolean permiteCriarPlaylists() {
        return false;
    }

    public boolean permitePlaylistsPersonalizadas() {
        return false;
    }

    public boolean permiteBiblioteca(){
        return false;
    }

    public boolean permiteAvancarERetroceder(){ return false;}

    public String toString(){return null;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        PlanoSub p = (PlanoSub) o;
        return this.toString().equals(p.toString());
    }
}