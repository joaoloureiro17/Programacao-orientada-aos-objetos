package Model;

import java.io.Serializable;

public class Musica implements Serializable {

    private String nome;
    private String interprete;
    private String nomeDaEditora;
    private String letra;
    private String melodia;
    private String generoMusical;
    private int duracao;
    private int contador;

    public Musica() {
        this.nome="";
        this.interprete="";
        this.nomeDaEditora="";
        this.letra="";
        this.melodia="";
        this.generoMusical="";
        this.duracao=0;
        this.contador=0;
    }

    public Musica(String nome, String interprete, String nomeDaEditora, String letra, String melodia, String generoMusical, int duracao, int contador){
        this.nome = nome;
        this.interprete = interprete;
        this.nomeDaEditora = nomeDaEditora;
        this.letra = letra;
        this.melodia = melodia;
        this.generoMusical = generoMusical;
        this.duracao = duracao;
        this.contador=contador;
    }

    public Musica(Musica musica){
        this.nome = musica.getNome();
        this.interprete = musica.getInterprete();
        this.nomeDaEditora = musica.getNomeDaEditora();
        this.letra = musica.getLetra();
        this.melodia = musica.getMelodia();
        this.generoMusical = musica.getGeneroMusical();
        this.duracao = musica.getDuracao();
        this.contador=musica.getContador();
    }

    public String getNome() {return this.nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getInterprete() {return this.interprete;}

    public void setInterprete(String interprete) {this.interprete = interprete;}

    public String getNomeDaEditora() {return this.nomeDaEditora;}

    public void setNomeDaEditora(String nomeDaEditora) {this.nomeDaEditora = nomeDaEditora;}

    public String getLetra() {return this.letra;}

    public void setLetra(String letra) {this.letra = letra;}

    public String getMelodia() {return this.melodia;}

    public void setMelodia(String musica) {this.melodia = melodia;}

    public String getGeneroMusical() {return this.generoMusical;}

    public void setGeneroMusical(String generoMusical) {this.generoMusical = generoMusical;}

    public int getDuracao() {return this.duracao;}

    public void setDuracao(int duracao) {this.duracao = duracao;}

    public int getContador() {return this.contador;}

    public void setContador(int contador) {this.contador = contador;}

    public void incrementarContador() {
        this.contador++;
    }

    public String toString(){
        return "Nome: " + this.nome +"\n"+ "Interprete: " + this.interprete +"\n"+ "Editora: " + this.nomeDaEditora +"\n"
                + "Letra: "+ this.letra+ "\n"+ "Melodia: "+ this.melodia+ "\n"+ "Gênero Musical: "+ this.generoMusical+ "\n"
                + "Duracao: " + this.duracao + "\n"+ "Contador: " + this.contador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || (this.getClass() != o.getClass())) return false;
        Musica m = (Musica) o;
        return this.nome.equals(m.getNome()) && this.interprete.equals(m.getInterprete()) && this.nomeDaEditora.equals(m.getNomeDaEditora()) && this.letra.equals(m.getLetra()) && this.melodia.equals(m.getMelodia()) && this.generoMusical.equals(m.getGeneroMusical()) && this.duracao == m.getDuracao() && this.contador == m.getContador();
    }

    @Override
    public Musica clone() {
        return new Musica(this);
    }
}
