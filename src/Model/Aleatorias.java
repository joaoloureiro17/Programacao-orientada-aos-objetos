package Model;

import java.util.Collections;
import java.util.List;

public class Aleatorias extends Playlist {

    public Aleatorias() {
        super();
    }

    public Aleatorias(Aleatorias c) {
        super(c);
    }

    public Aleatorias(String nome, List<Musica> todas) {
        super(nome, todas);
    }

    @Override
    public String toString() {return super.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Aleatorias a = (Aleatorias) o;
        return super.equals(a);
    }

    @Override
    public Aleatorias clone() {
        return new Aleatorias(this);
    }
}
