package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Utilizador implements Serializable {

    private String nome;
    private String password;
    private String email;
    private String morada;
    private LocalDate dataNascimento;
    private int pontos;
    private PlanoSub plano;
    private Map<String, Playlist> playlists;
    private Map<String, List<LocalDate>> musicasOuvidas;
    private List<Object> biblioteca;

    public Utilizador(){
        this.nome="";
        this.password="";
        this.email="";
        this.morada="";
        this.dataNascimento=null;
        this.pontos=0;
        this.plano= new Free();
        this.playlists = new HashMap<>();
        this.musicasOuvidas = new HashMap<>();
        this.biblioteca = new ArrayList<>();
    }

    public Utilizador(String nome, String password, String email, String morada, LocalDate dataNascimento) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.morada = morada;
        this.dataNascimento=dataNascimento;
        this.pontos = 0;
        this.plano = new Free();
        this.playlists = new HashMap<>();
        this.musicasOuvidas = new HashMap<>();
        this.biblioteca = new ArrayList<>();
    }

    // Construtor com playlists, sem ser iniciado a vazio
    public Utilizador(String nome, String password, String email, String morada, LocalDate dataNascimento, int pontos, PlanoSub plano,Map<String, Playlist> playlists, Map<String, Integer> musicasOuvidas) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.morada = morada;
        this.dataNascimento=dataNascimento;
        this.pontos=pontos;
        this.plano= plano;
        this.playlists = playlists.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.musicasOuvidas = new HashMap<>();
    }

    public Utilizador(Utilizador utilizador) {
        this.nome=utilizador.getNome();
        this.password=utilizador.getPassword();
        this.email=utilizador.getEmail();
        this.morada=utilizador.getMorada();
        this.dataNascimento=utilizador.getdataNascimento();
        this.pontos= utilizador.getPontos();
        this.plano= utilizador.getPlano();
        this.playlists = utilizador.getPlaylists();
        this.musicasOuvidas = utilizador.getMusicasOuvidas();
        this.biblioteca = utilizador.getBiblioteca();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public LocalDate getdataNascimento(){ return this.dataNascimento;}

    public void setdataNascimento(LocalDate dataNascimento){this.dataNascimento=dataNascimento;}

    public int getPontos() {return this.pontos;}

    public void setPontos(int pontos) {this.pontos = pontos;}

    public PlanoSub getPlano() {return this.plano;}

    public void setPlano(PlanoSub plano) {this.plano = plano;}

    public Map<String, Playlist> getPlaylists() {
        return this.playlists.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public void setPlaylists(Map<String, Playlist> playlists) {
        this.playlists = playlists.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Map<String, List<LocalDate>> getMusicasOuvidas() {
        return this.musicasOuvidas.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
    }

    public void setMusicasOuvidas(Map<String, List<LocalDate>> musicasOuvidas) {
        this.musicasOuvidas = musicasOuvidas.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
    }

    public List<Object> getBiblioteca() {
        return new ArrayList<>(this.biblioteca);
    }

    public void setBiblioteca(List<Object> biblioteca) {
        if (biblioteca != null) {
            this.biblioteca = new ArrayList<>(biblioteca);
        }
    }

    public void mudarPlano(PlanoSub novoPlano) {
        this.plano = novoPlano;
    }

    public void adicionarPontos(int pt){
        this.pontos += pt;
    }

    public void adicionarPlaylist(Playlist p) {
        if (p == null || p.getNome() == null) return;

        this.playlists.put(p.getNome(), p.clone());
    }

    public void registarAudicao(String nomeMusica) {
        if (!this.musicasOuvidas.containsKey(nomeMusica)) {
            this.musicasOuvidas.put(nomeMusica, new ArrayList<>());
        }
        this.musicasOuvidas.get(nomeMusica).add(LocalDate.now());
    }

    public void adicionarBiblioteca(Object item) {
        if (item != null) {
            this.biblioteca.add(item);
        }
    }

    public boolean removerItemBiblioteca(Object item) {
        return this.biblioteca.remove(item);
    }

    public String toString(){
        return "Nome: "+ this.nome +"\n" +"Email: "+this.email+ "\n"+ "Morada: "+this.morada+"\n"+"DataNascimento:"+ this.dataNascimento+"\n"+"Pontos: "+ this.pontos+ "\n"+ "Plano: "+ this.plano;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Utilizador other = (Utilizador) obj;
        return this.getPontos() == other.getPontos() && Objects.equals(this.nome, other.nome) && Objects.equals(this.email, other.email) && Objects.equals(this.morada, other.morada) && Objects.equals(this.plano, other.plano) && Objects.equals(this.playlists, other.playlists) && Objects.equals(this.musicasOuvidas, other.musicasOuvidas) && Objects.equals(this.biblioteca, other.biblioteca) && Objects.equals(this.dataNascimento, other.dataNascimento);
    }

    @Override
    public Utilizador clone() {
        return new Utilizador(this);
    }
}