package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SpotifUMGestor implements Serializable {
    private Map<String, Utilizador> utilizadores;
    private Map<String, Album> albuns;
    private Map<String, Musica> musicas;
    private Map<String, Playlist> playlists;

    public SpotifUMGestor() {
        this.utilizadores = new HashMap<>();
        this.albuns = new HashMap<>();
        this.musicas = new HashMap<>();
        this.playlists = new HashMap<>();
    }

    public SpotifUMGestor(Map<String, Utilizador> utilizadores, Map<String, Album> albums, Map<String, Musica> musicas, Map<String,Playlist> playlists) {
        this.utilizadores = utilizadores;
        this.albuns = albums;
        this.musicas = musicas;
        this.playlists = playlists;

    }

    public SpotifUMGestor(SpotifUMGestor spotifUMGestor) {
        this.utilizadores = spotifUMGestor.getUtilizadores();
        this.albuns = spotifUMGestor.getAlbuns();
        this.musicas = spotifUMGestor.getMusicas();
        this.playlists = spotifUMGestor.getPlaylists();
    }

    public Map<String, Utilizador> getUtilizadores() {
        return this.utilizadores.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public Map<String, Album> getAlbuns() {
        return this.albuns.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public Map<String, Musica> getMusicas() {
        return this.musicas.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public Map<String, Playlist> getPlaylists() {
        return this.playlists.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public void setAlbuns(Map<String, Album> albuns) {
        this.albuns = albuns.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public void setMusicas(Map<String, Musica> musicas) {
        this.musicas = musicas.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public void setPlaylists(Map<String, Playlist> playlists) {
        this.playlists = playlists.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().clone(),(a,b)->a, HashMap::new));
    }

    public void adicionarUtilizador(Utilizador u) {
        this.utilizadores.put(u.getEmail(), u.clone());
    }

    public void adicionarAlbum(Album u) {
        this.albuns.put(u.getNome(), u.clone());
    }

    public void adicionarMusica(Musica u) {
        this.musicas.put(u.getNome(), u.clone());
    }

    public void adicionarPlaylist(Playlist u){
        this.playlists.put(u.getNome(), u.clone());
    }

    public int loginCorreto(String email, String password) {
        if (email.equals("administrador") ) {
            Administrador admin = new Administrador();
            if(password.equals(admin.getPassword())) { return 2;}
        }
        for (Map.Entry<String, Utilizador> entry : utilizadores.entrySet()) {
            Utilizador u = entry.getValue().clone();
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return 1;
            }
        }
        return 0;
    }

    public void adicionarPlaylistAUtilizador(String email, Playlist p) {
        Utilizador u = this.utilizadores.get(email);
        if (u != null) u.adicionarPlaylist(p);
    }

    public Musica getMusica(String nome) {
        Musica m = this.musicas.get(nome);
        if (m != null) {
            return m.clone();
        } else {
            return null;
        }
    }

    public Album getAlbum (String nome) {
        Album a = this.albuns.get(nome);
        if (a != null) {
            return a.clone();
        }else {
            return null;
        }
    }

    public Playlist getPlaylist (String nome) {
        Playlist p = this.playlists.get(nome);
        if (p != null) {
            return p.clone();
        }else{
            return null;
        }
    }

    /// Estatisticas///////
    public Musica getMusicaMaisReproduzida() {
        Musica maisReproduzida = null;
        int max = -1;

        for (Map.Entry<String, Musica> entry : getMusicas().entrySet()) {
            Musica m = entry.getValue();
            if (m.getContador() > max) {
                max = m.getContador();
                maisReproduzida = m;
            }
        }

        return maisReproduzida;
    }

    public Utilizador getUtilizadorComMaisPlaylists() {
        Utilizador top = null;
        int maxPlaylists = -1;

        for (Utilizador u : getUtilizadores().values()) {
            int numPlaylists = u.getPlaylists().size();
            if (numPlaylists > maxPlaylists) {
                maxPlaylists = numPlaylists;
                top = u;
            }
        }

        return top;
    }

    public Map<String, Playlist> getPlaylistsPublicas() {
        return getPlaylists();
    }

    public String getInterpreteMaisOuvido() {
        Map<String, Integer> contagemInterpretes = new HashMap<>();

        for (Musica m : getMusicas().values()) {
            String interprete = m.getInterprete();
            int contador = m.getContador();

            if (contagemInterpretes.containsKey(interprete)) {
                contagemInterpretes.compute(interprete, (k, valorAtual) -> valorAtual + contador);
            } else {
                contagemInterpretes.put(interprete, contador);
            }
        }

        String maisOuvido = null;
        int max = -1;
        for (Map.Entry<String, Integer> entry : contagemInterpretes.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maisOuvido = entry.getKey();
            }
        }

        if (maisOuvido != null) {
            return "\n Intérprete mais ouvido: " + maisOuvido + " com " + max + " reproduções.";
        } else {
            return "\n Nenhum intérprete encontrado.";
        }

    }

    public String getGeneroMaisReproduzido() {
        Map<String, Integer> contagemGeneros = new HashMap<>();
        for (Musica m : getMusicas().values()) {
            String genero = m.getGeneroMusical();
            int contador = m.getContador();
            if (contagemGeneros.containsKey(genero)) {
                contagemGeneros.put(genero, contagemGeneros.get(genero) + contador);
            } else {
                contagemGeneros.put(genero, contador);
            }
        }

        String maisReproduzido = null;
        int max = -1;

        for (Map.Entry<String, Integer> entry : contagemGeneros.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maisReproduzido = entry.getKey();
            }
        }

        if (maisReproduzido != null) {
            return "\n Género mais reproduzido: " + maisReproduzido + " com " + max + " reproduções.";
        } else {
            return "\n Nenhum género musical encontrado.";
        }
    }

    public Utilizador getUtilizadorComMaisPontos() {
        Utilizador utilizadorComMaisPontos = null;
        int maxPontos = -1;

        for (Utilizador u : getUtilizadores().values()) {
            int pontos = u.getPontos();
            if (pontos > maxPontos) {
                maxPontos = pontos;
                utilizadorComMaisPontos = u;
            }
        }
        return utilizadorComMaisPontos;
    }

    public Utilizador getUtilizadorMaisAtivo(LocalDate inicio, LocalDate fim) {
        Utilizador maisAtivo = null;
        int maxAudicoes = 0;

        for (Utilizador u : getUtilizadores().values()) {
            int total = 0;

            for (List<LocalDate> datas : u.getMusicasOuvidas().values()) {
                for (LocalDate data : datas) {
                    if ((inicio == null || !data.isBefore(inicio)) &&
                            (fim == null || !data.isAfter(fim))) {
                        total++;
                    }
                }
            }
            if (total > maxAudicoes) {
                maxAudicoes = total;
                maisAtivo = u;
            }
        }
        return maisAtivo;
    }

    public String calcularGeneroFavorito(Utilizador u) {
        Map<String, List<LocalDate>> ouvidas = u.getMusicasOuvidas();
        Map<String, Integer> generoContagem = new HashMap<>();

        for (String nomeMusica : ouvidas.keySet()) {
            Musica m = getMusicas().get(nomeMusica);
            if (m != null) {
                String genero = m.getGeneroMusical();
                generoContagem.put(genero, generoContagem.getOrDefault(genero, 0) + ouvidas.get(nomeMusica).size());
            }
        }
        if (generoContagem.isEmpty()) {
            System.out.println("Ainda não ouviste músicas suficientes para gerar uma playlist.");
            return null;
        }
        return Collections.max(generoContagem.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public List<Musica> filtrarMusicasPorGenero(String genero,boolean apenasExplicitas) {
        return this.musicas.values().stream()
                .filter(m -> m.getGeneroMusical().equalsIgnoreCase(genero))
                .filter(m -> !apenasExplicitas || m instanceof MusicaExplicita)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Musica> selecionarMusicasTempo(int tempoMaxSegundos, List<Musica> candidatas) {
        int tempoAtual = 0;
        List<Musica> escolhidas = new ArrayList<>();

        for (Musica m : candidatas) {
            if (tempoAtual + m.getDuracao() <= tempoMaxSegundos) {
                escolhidas.add(m);
                tempoAtual += m.getDuracao();
            } else {
                break;
            }
        }
        return escolhidas;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        SpotifUMGestor that = (SpotifUMGestor) obj;
        return Objects.equals(utilizadores, that.utilizadores) && Objects.equals(albuns, that.albuns) && Objects.equals(musicas, that.musicas) && Objects.equals(playlists, that.playlists);
    }

    @Override
    public SpotifUMGestor clone(){
        return new SpotifUMGestor(this);
    }
}