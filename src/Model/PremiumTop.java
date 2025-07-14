package Model;

public class PremiumTop extends PlanoSub{

    public PremiumTop() {
        super();
    }

    @Override
    public void atribuirPontos(Utilizador u) {
        int bonus= (int) Math.round(u.getPontos()*0.025);
        u.adicionarPontos(bonus);
    }

    @Override
    public boolean permiteCriarPlaylists() {
        return true;
    }

    @Override
    public boolean permitePlaylistsPersonalizadas() {
        return true;
    }

    @Override
    public boolean permiteBiblioteca(){
        return true;
    }

    @Override
    public boolean permiteAvancarERetroceder(){ return true;}

    @Override
    public String toString() {return "Premium Top";}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PremiumTop that = (PremiumTop) o;
        return toString().equals(that.toString());
    }

    @Override
    public PremiumTop clone() {
        return new PremiumTop();
    }
}