package Model;

public class PremiumBase extends PlanoSub{

    public PremiumBase() {
        super();
    }

    @Override
    public void atribuirPontos(Utilizador u) {
        u.adicionarPontos(10);
    }

    @Override
    public boolean permiteCriarPlaylists() {
        return true;
    }

    @Override
    public boolean permiteBiblioteca(){
        return true;
    }

    @Override
    public boolean permiteAvancarERetroceder(){ return true;}

    @Override
    public String toString() {return "Premium Base";}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        PremiumBase p = (PremiumBase) o;
        return this.toString().equals(p.toString());
    }

    @Override
    public PremiumBase clone() {
        return new PremiumBase();
    }
}