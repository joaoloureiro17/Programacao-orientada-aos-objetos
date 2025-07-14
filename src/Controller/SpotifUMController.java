package Controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.*;
import Utils.*;


public class SpotifUMController {
    private SpotifUMGestor gestor;

    public SpotifUMController(SpotifUMGestor gestor) {
        this.gestor = gestor;
    }

    //Metodo para efetuar o login, conforme o email e a password recebidos como parametro
    public int login(String email, String password) {
        return gestor.loginCorreto(email, password);
    }

    //Metodo utilizado para obter um determinado utilizador atraves do seu email
    public Utilizador getUtilizador(String email) {
        return gestor.getUtilizadores().get(email);
    }

    //Metodo usado para estatisticas, para obter a musica mais reproduzida pelos utilizadores
    public Musica getMusicaMaisReproduzida() {
        return gestor.getMusicaMaisReproduzida();
    }

    //Metodo usado para estatisticas, para obter o utilizador, com mais playlists
    public Utilizador getUtilizadorComMaisPlaylists() {
        return gestor.getUtilizadorComMaisPlaylists();
    }

    //Metodo usado para obter todas as playlist publicas
    public Map<String, Playlist> getPlaylistsPublicas() {
        return gestor.getPlaylistsPublicas();
    }

    //Metodo usado para estatisticas, para obter o utilizador com mais pontos
    public Utilizador getUtilizadorComMaisPontos() {
        return gestor.getUtilizadorComMaisPontos();
    }

    //Metodo usado para estatisticas, para obter o iternprete mais reproduzido pelos utilizadores
    public String getInterpreteMaisOuvido() {
        return gestor.getInterpreteMaisOuvido();
    }

    //Metodo usado para estatisticas, para obter o genero mais reproduzido pelos utilizadores
    public String getGeneroMaisReproduzido() {
        return gestor.getGeneroMaisReproduzido();
    }

    //Metodo usado para estatisticas, para obter o utilizador que mais musicas ouviu num determinado intervalo de tempo
    public Utilizador getUtilizadorMaisAtivo(LocalDate inicio, LocalDate fim) {
        return gestor.getUtilizadorMaisAtivo(inicio, fim);
    }

    //Metodo para obter um Album presente no mapa de albuns do gestor
    public Album getAlbum(String nomeAlbum){
        return gestor.getAlbum(nomeAlbum);
    }

    //Metodo para criar a Musica
    public Musica criarMusica(String nomeMusica, String interpreteAlbum, String editora, String letra,
                              String melodia, String genero, int duracao, String explicita, String multimedia) {
        Musica musica;
        if (explicita.equals("s")) {
            musica = new MusicaExplicita(nomeMusica, interpreteAlbum, editora, letra, melodia, genero, duracao, 0);
        } else if (!multimedia.trim().equalsIgnoreCase("n")) {
            musica = new MusicaMultimedia(nomeMusica, interpreteAlbum, editora, letra, melodia, genero, duracao, 0, multimedia);
        } else {
            musica = new Musica(nomeMusica, interpreteAlbum, editora, letra, melodia, genero, duracao, 0);
        }
        Data.guardarEstado(gestor, "estado.dat");
        return musica;

    }

    //Metodo para adicionar o album e as musicas
    public void adicionarAlbum(String nomeAlbum, String interpreteAlbum, String editora, List<Musica> musicas) {
        Album album = new Album(nomeAlbum, interpreteAlbum, musicas);

        // Adiciona o álbum ao gestor
        gestor.adicionarAlbum(album);
        Data.guardarEstado(gestor, "estado.dat");

        // Adiciona cada música ao gestor
        for (Musica musica : musicas) {
            gestor.adicionarMusica(musica);
            Data.guardarEstado(gestor, "estado.dat");
        }
    }

    // Metodo para criar e adicionar o utilizador
    public void criarConta(String nome, String email, String password, String morada, LocalDate dataNascimento) {
        if (gestor.getUtilizadores().containsKey(email)) {
            System.out.println("Já existe uma conta com esse email.");
            return;
        }

        Utilizador novo = new Utilizador(nome, password, email, morada, dataNascimento);
        gestor.adicionarUtilizador(novo);
        Data.guardarEstado(gestor, "estado.dat");
        System.out.println("Conta criada com sucesso.");
    }

    // Metodo para criar e adicionar a playlist aleatória
    public void criarPlaylistAleatoria(Utilizador u) {
        List<Musica> todasAsMusicas = new ArrayList<>(gestor.getMusicas().values());
        if (todasAsMusicas.size() >= 5) {
            Collections.shuffle(todasAsMusicas);
            List<Musica> selecionadas = todasAsMusicas.stream().limit(5).collect(Collectors.toList());
            Aleatorias aleatoria = new Aleatorias("Aleatória " + u.getNome(), selecionadas);
            u.adicionarPlaylist(aleatoria);
            gestor.adicionarPlaylistAUtilizador(u.getEmail(), aleatoria);
            Data.guardarEstado(gestor, "estado.dat");
        }
    }

    //Metodo para criar a playlist de favoritas de um determinado utilizador
    public boolean criarPlaylistFavoritos(Utilizador u, boolean apenasExplicitas) {
        String genero = gestor.calcularGeneroFavorito(u);
        List<Musica> candidatas = gestor.filtrarMusicasPorGenero(genero, apenasExplicitas);

        if (candidatas.size() >= 5) {
            Collections.shuffle(candidatas);
            List<Musica> selecionadas = candidatas.stream().limit(5).collect(Collectors.toList());
            Favoritas favoritas = new Favoritas("Favorita " + u.getNome(), selecionadas, genero);
            u.adicionarPlaylist(favoritas);
            gestor.adicionarPlaylistAUtilizador(u.getEmail(), favoritas);
            Data.guardarEstado(gestor, "estado.dat");
            return true;
        }
        Data.guardarEstado(gestor, "estado.dat");
        return false;
    }

    //Metodo para criar a playlist de favoritas de um determinado utilizador, tendo em atencao o tempo que a mesma dura
    public boolean criarPlaylistPorGeneroComDuracao(Utilizador u, int tempoMaximoSegundos) {
        String genero = gestor.calcularGeneroFavorito(u);
        List<Musica> candidatas = gestor.filtrarMusicasPorGenero(genero,false);

        if (candidatas.size() >= 5) {
            List<Musica> escolhidas= gestor.selecionarMusicasTempo(tempoMaximoSegundos,candidatas);
            GeneroETempo generoETempo = new GeneroETempo("Genero e Tempo " + u.getNome(),escolhidas,genero,tempoMaximoSegundos);
            u.adicionarPlaylist(generoETempo);
            gestor.adicionarPlaylistAUtilizador(u.getEmail(), generoETempo);
            Data.guardarEstado(gestor, "estado.dat");
            return true;
        }
        Data.guardarEstado(gestor, "estado.dat");
        return false;
    }

    //Metodo para obter a playlist que o utilizador pretende ouvir
    public Playlist obterPlaylistParaOuvir(Utilizador u, String nomePlaylist, boolean publica) {
        if (publica) {
            return gestor.getPlaylists().get(nomePlaylist);
        } else {
            return u.getPlaylists().get(nomePlaylist);
        }
    }

    //Metodo para remover uma playlist ou um album da biblioteca do utilizador
    public boolean removerItemDaBiblioteca(Utilizador u, Object item) {
        if (u.removerItemBiblioteca(item)) {
            gestor.adicionarUtilizador(u); // Atualiza o utilizador com a nova biblioteca
            Data.guardarEstado(gestor, "estado.dat");
            return true;
        }
        Data.guardarEstado(gestor, "estado.dat");
        return false;
    }

    //Metodo para o utilizador poder trocar o seu plano
    public boolean mudarPlanoDeUtilizador(Utilizador u, PlanoSub novoPlano) {
        if (u.getPlano().getClass().equals(novoPlano.getClass())) {
            return false; // O plano já é o mesmo
        }

        if (novoPlano instanceof PremiumTop) {
            u.adicionarPontos(100); // Adiciona pontos para PremiumTop
        }

        u.mudarPlano(novoPlano);
        gestor.adicionarUtilizador(u);
        Data.guardarEstado(gestor, "estado.dat");
        return true; // Mudança de plano foi bem-sucedida
    }

    //Metodo para obter a musica atraves do seu nome
    public Musica obterMusicaPorNome(String nome) {
        Data.guardarEstado(gestor, "estado.dat");
        return this.gestor.getMusica(nome); // Retorna a música se encontrada, senão null
    }

    //Metodo que retorna uma mapa com todos os albuns presentes no programa
    public Map<String, Album> obterAlbunsDisponiveis() {
        Data.guardarEstado(gestor, "estado.dat");
        return gestor.getAlbuns(); // Retorna os álbuns disponíveis
    }

    //Metodo para um determinado utilizador ouvir um album
    public String ouvirAlbum(Utilizador u, String nomeAlbum) {
        Album album = gestor.getAlbum(nomeAlbum);
        if (album == null) {
            return "Álbum não encontrado.";
        }
        Data.guardarEstado(gestor, "estado.dat");
        return null; // Caso o álbum seja encontrado, retornamos null e deixamos a reprodução de música para a UI
    }

    //Metodo que adiciona um album a biblioteca do utilizador
    public String adicionarAlbumBiblioteca(Utilizador u, String nomeAlbum) {
        Album album = gestor.getAlbum(nomeAlbum);
        if (album == null) {
            return "Álbum não encontrado.";
        }

        u.adicionarBiblioteca(album.clone());
        gestor.adicionarUtilizador(u);
        Data.guardarEstado(gestor, "estado.dat");
        return "Álbum '" + album.getNome() + "' adicionado à sua biblioteca.";
    }


    //Metodo que permite ver quais sao as playlists publicas presentes no programa
    public String verPlaylistsPublicas(Utilizador u) {
        Map<String, Playlist> publicas = gestor.getPlaylists();

        if (publicas.isEmpty()) {
            return "Não existem playlists públicas disponíveis.";
        }
        Data.guardarEstado(gestor, "estado.dat");
        return null;
    }

    //Metodo que adiciona uma playlist a biblioteca do utilizador
    public String adicionarPlaylistBiblioteca(Utilizador u, String nomePlaylist) {
        Playlist p = gestor.getPlaylist(nomePlaylist);

        if (p == null) {
            return "Playlist não encontrada.";
        }

        u.adicionarBiblioteca(p);
        gestor.adicionarUtilizador(u);
        Data.guardarEstado(gestor, "estado.dat");
        return "Playlist '" + p.getNome() + "' adicionada à sua biblioteca.";

    }

    //Metodo para adicionar uma musica ao mapa com todas as musicas do gestor
    public void adicionarMusica(Musica m){
        gestor.adicionarMusica(m);
        Data.guardarEstado(gestor, "estado.dat");
    }

    //Metodo para adicionar um utilizador ao mapa com todos os utilizadores do gestor
    public void adicionarUtilizador(Utilizador u){
        gestor.adicionarUtilizador(u);
        Data.guardarEstado(gestor, "estado.dat");
    }

    //Metodo para obter as musicas presentes no mapa de musicas do gestor
    public List<Musica> getMusicas() {
        return new ArrayList<>(gestor.getMusicas().values());
    }

    //Metodo para adicionar playlists ao mapa com todas as playlist do gestor
    public void adicionarPlaylist(Playlist nova) {
        gestor.adicionarPlaylist(nova);
    }

    //Metodo para adicionar a playlist ao determinada utilizador
    public void adicionarPlaylistAUtilizador(String email, Playlist nova) {
        gestor.adicionarPlaylistAUtilizador(email, nova);
    }

    //Metodo para processar a audicao da musica, com atribuicao de pontos, conforme o plano do utilizador
    public void processarAudicao(Utilizador u, Musica m) {
        u.registarAudicao(m.getNome());
        m.incrementarContador();
        u.getPlano().atribuirPontos(u);
    }

    //Metodo que retorna um mapa com as playlists de um determinado utilizador
    public Map<String, Playlist> getPlaylists(Utilizador u) {
        return u.getPlaylists(); // se quiseres segurança, podes devolver uma cópia
    }

    //Metodo para verificar se o utilizador em questao, tem acesso a sua biblioteca pessoal, tendo em conta o seu plano
    public boolean permissaoBiblioteca(Utilizador u) {
        return u.getPlano().permiteBiblioteca();
    }

    //Metodo para verificar se o utilizador em questao, pode criar playlists, tendo em conta o seu plano
    public boolean permissaoCriarPlaylists(Utilizador u) {
        return u.getPlano().permiteCriarPlaylists();
    }

    //Metodo para verificar se o utilizador em questao, pode criar playlists personalizadas, tendo em conta o seu plano
    public boolean permissaoPlaylistsPersonalizadas(Utilizador u) {
        return u.getPlano().permitePlaylistsPersonalizadas();
    }

    //Metodo para verificar se um utilizado tem ou nao, mais de 18 anos
    public boolean maiorIdade(Utilizador u) {
        if (u.getdataNascimento() == null) return false; // Segurança
        LocalDate hoje = LocalDate.now();
        Period idade = Period.between(u.getdataNascimento(), hoje);
        return idade.getYears() >= 18;
    }

    //Metodo para verificar se o utilizador que quer ouvir uma musica explicita, e de facto maior de idade, para a poder reproduzir
    public boolean maiorIdadeEexplicita(Utilizador u, Musica m){
        return !(m instanceof MusicaExplicita) || maiorIdade(u);
    }

    //Metodo para verificar se a musica passada como parametro e uma musica explicita
    public boolean musicaExplicita(Musica m){
        return (m instanceof MusicaExplicita);
    }

    //Metodo para verificar se a musica passada como parametro e uma musica multimedia
    public boolean musicaMultimedia(Musica m){
        return (m instanceof MusicaMultimedia);
    }

    //Metodo que retorna true ou false, caso o Utilizador possa ou nao avancar e retroceder na audicao de uma musica
    public boolean permissaoAvancarERetroceder(Utilizador u) {
        return u.getPlano().permiteAvancarERetroceder();
    }

    //Metodo utilizado para guardar todo o estado em binario, no respetivo ficheiro
    public void guardarEstado(){
        Data.guardarEstado(gestor, "estado.dat");
    }

    //Metodo utilizado para criar playlists, tendo a opcao de a tornar, ou nao publica
    public void criarPlaylists(Utilizador u,String nomePlaylist, List<Musica> selecionadas, String resposta){
        Criadas nova;
        if (resposta.equals("s") || resposta.equals("sim")) {
            nova = new Criadas(nomePlaylist, selecionadas, true);
            adicionarPlaylist(nova);
        }
        else{
            nova = new Criadas(nomePlaylist, selecionadas, false);
        }
        u.adicionarPlaylist(nova);
        adicionarPlaylistAUtilizador(u.getEmail(), nova);
    }

    //Metodo utilizado para obter o link do video da musica multimedia
    public String getVideoMusica(Musica m){
        MusicaMultimedia mm = (MusicaMultimedia) m;
        return mm.getLinkVideo();
    }
}