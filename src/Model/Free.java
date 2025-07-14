package Model;

public class Free extends PlanoSub{

    public Free() {
        super();
    }

    @Override
    public void atribuirPontos(Utilizador u) {
        u.adicionarPontos(5);
    }

    @Override
    public String toString() {return "Free";}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        return true; // todos os objetos Free são iguais entre si, pois não têm campos de estado
    }

    @Override
    public Free clone() {
        return new Free();
    }
}