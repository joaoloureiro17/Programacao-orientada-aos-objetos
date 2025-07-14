package Model;

public class MusicaExplicita extends Musica {

    public MusicaExplicita(){
        super();
    }

    public MusicaExplicita(String nome, String interprete, String nomeDaEditora, String letra, String melodia, String generoMusical, int duracao, int contador) {
        super(nome, interprete, nomeDaEditora, letra, melodia, generoMusical, duracao, contador);
    }

    public MusicaExplicita(Musica m) {
        super(m);
    }

    @Override
    public String toString() {return super.toString();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        MusicaExplicita m = (MusicaExplicita) o;
        return super.equals(m);
    }

    @Override
    public Musica clone() {
        return new MusicaExplicita(this);
    }
}