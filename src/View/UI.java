package View;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import Model.*;
import Controller.*;

public class UI {
    private SpotifUMController controlador;
    Scanner sc = new Scanner(System.in);

    public UI(SpotifUMController controlador) {
        this.controlador = controlador;
    }

    public void run() {
        String[] opcoes = {
                "Login",
                "Criar conta"
        };

        NewMenu menu = new NewMenu("Menu Principal", opcoes);

        menu.setHandler(1, this::login);
        menu.setHandler(2, this::criarConta);

        menu.run();
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        int tipo = controlador.login(email, password);

        if (tipo == 1) {
            System.out.println("Login com sucesso!");
            Utilizador u = controlador.getUtilizador(email);
            menuUtilizador(u);
        } else if (tipo == 2) {
            System.out.println("Login como administrador realizado com sucesso!");
            menuAdministrador();
        } else {
            System.out.println("Credenciais inválidas!");
        }
    }

    private void menuAdministrador() {
        String[] opcoes = {
                "Adicionar álbum (e músicas)",
                "Estatísticas",
        };

        NewMenu menu = new NewMenu("Menu Administrador", opcoes);
        menu.setHandler(1, () -> adicionarAlbum());
        menu.setHandler(2, () -> estatisticas());
        menu.run();
    }

    private void adicionarAlbum() {
        System.out.print("Nome do álbum: ");
        String nomeAlbum = sc.nextLine();
        System.out.print("Interprete do álbum: ");
        String interpreteAlbum = sc.nextLine();
        System.out.print("Nome da editora: ");
        String editora = sc.nextLine();

        List<Musica> musicas = new ArrayList<>();
        String continuar = "s";

        while (continuar.equals("s")) {
            System.out.print("Nome da música: ");
            String nomeMusica = sc.nextLine();
            System.out.print("Letra: ");
            String letra = sc.nextLine();
            System.out.print("Melodia: ");
            String melodia = sc.nextLine();
            System.out.print("Género musical: ");
            String genero = sc.nextLine();
            System.out.print("Duração (segundos): ");
            int duracao = Integer.parseInt(sc.nextLine());

            System.out.print("A música é explícita? (s/n): ");
            String explicita = sc.nextLine().trim().toLowerCase();
            System.out.print("Associar um vídeo(link) a esta música (caso não queira, ponha apenas n): ");
            String multimedia = sc.nextLine();

            musicas.add(controlador.criarMusica(nomeMusica, interpreteAlbum, editora, letra, melodia, genero, duracao, explicita, multimedia));

            System.out.print("Adicionar outra música? (s/n): ");
            continuar = sc.nextLine().toLowerCase();
        }

        // Chama o método do controlador para adicionar o álbum com as músicas
        controlador.adicionarAlbum(nomeAlbum, interpreteAlbum, editora, musicas);

        System.out.println("Álbum e músicas adicionadas com sucesso!");
        System.out.println("Músicas no álbum:");

        for (Musica m : musicas) {
            System.out.println(" - " + m.getNome());
        }

        controlador.guardarEstado();
    }

    private void criarConta() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Morada: ");
        String morada = sc.nextLine();
        System.out.print("Data de nascimento (dd-MM-yyyy): ");
        String dataStr = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataNascimento = LocalDate.parse(dataStr, formatter);

        // Passa os dados para o Controller criar a conta
        controlador.criarConta(nome, email, password, morada, dataNascimento);

        controlador.guardarEstado();
    }

    private void menuUtilizador(Utilizador u) {
        String[] opcoes = {
                "Meu Perfil",
                "Playlists Públicas",
                "Albuns",
                "Criar uma playlist",
                "Ouvir musica",
        };

        NewMenu menu = new NewMenu("Menu Utilizador", opcoes);
        menu.setHandler(1, () -> verPerfil(u));
        menu.setHandler(2, () -> verPlaylistsPublicas(u));
        menu.setHandler(3, () -> verAlbuns(u));
        menu.setHandler(4, () -> criarPlaylists(u));
        menu.setHandler(5, () -> ouvirMusica(u));

        menu.run();
    }

    private void verPerfil(Utilizador u) {
        System.out.println("\n--- Perfil ---");
        System.out.println(u.toString());

        String[] opcoes = {
                "As minhas Playlists",
                "Biblioteca",
                "Criar Playlist Personalizada por Género",
                "Criar Playlist Personalizada por Género e apenas com músicas explicitas",
                "Criar Playlist Personalizada por Género e Tempo",
                "Mudar Plano de Subscrição",
        };

        NewMenu menu = new NewMenu("", opcoes);
        menu.setHandler(1, () -> verMinhasPlaylists(u));
        menu.setHandler(2, () -> verBiblioteca(u));
        menu.setHandler(3, () -> criarPlaylistFavoritos(u, false));
        menu.setHandler(4, () -> criarPlaylistFavoritos(u, true));
        menu.setHandler(5, () -> criarPlaylistPorGeneroComDuracao(u));
        menu.setHandler(6, () -> mudarPlanoSubscricao(u));

        menu.run();
    }

    private void verMinhasPlaylists(Utilizador u) {
        System.out.println("\n--- As minhas Playlists  ---");

        if (u.getPlano() instanceof Free) {
            controlador.criarPlaylistAleatoria(u);
        }

        Map<String, Playlist> playlists = u.getPlaylists();

        if (playlists.isEmpty()) {
            System.out.println("Não tem playlists.");
            return;
        }

        int index = 1;
        for (Playlist p : playlists.values()) {
            System.out.println(index++ + "- " + p.getNome());
            System.out.println(" Músicas na playlist:");
            for (Musica m : p.getMusicas()) {
                System.out.println(" - " + m.getNome() + " (Interprete: " + m.getInterprete() + ")");
            }
        }

        String[] opcoes = {
                "Ouvir Playlist",
        };

        NewMenu menu = new NewMenu("", opcoes);
        menu.setHandler(1, () -> ouvirPlaylist(u, false));

        menu.run();
    }

    private void criarPlaylistFavoritos(Utilizador u, boolean apenasExplicitas) {
        if (!controlador.permissaoPlaylistsPersonalizadas(u)) {
            System.out.println("O seu Plano de Subscrição não tem acesso a esta ferramenta. Caso deseje, pode mudar o seu Plano de Subscrição no seu perfil.");
            return;
        }

        boolean criada = controlador.criarPlaylistFavoritos(u, apenasExplicitas);

        if (criada) {
            System.out.println("Playlist criada com sucesso e adicionada às suas playlists.");
        } else {
            System.out.println("Não foi possível criar uma playlist de favoritos.");
        }
    }

    private void criarPlaylistPorGeneroComDuracao(Utilizador u) {
        if (!controlador.permissaoPlaylistsPersonalizadas(u)) {
            System.out.println("O seu Plano de Subscrição não tem acesso a esta ferramenta. Caso deseje, pode mudar o seu Plano de Subscrição no seu perfil.");
            return;
        }

        System.out.print("Insira o tempo máximo da playlist (em segundos): ");
        int tempoMaximoSegundos;

        try {
            tempoMaximoSegundos = Integer.parseInt(sc.nextLine());
            if (tempoMaximoSegundos <= 0) {
                System.out.println("Tempo inválido. Deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Deve introduzir um número inteiro.");
            return;
        }

        boolean criada = controlador.criarPlaylistPorGeneroComDuracao(u, tempoMaximoSegundos);

        if (criada) {
            System.out.println("Playlist criada com sucesso e adicionada às suas playlists.");
        } else {
            System.out.println("Não foi possível criar uma playlist com os critérios especificados.");
        }
    }

    private void ouvirPlaylist(Utilizador u, boolean publica) {

        System.out.print("\nPesquisa: ");
        String nomePlaylist = sc.nextLine().trim();

        Playlist p = controlador.obterPlaylistParaOuvir(u, nomePlaylist, publica);

        if (p == null) {
            System.out.println("Playlist não encontrada.");
            return;
        }

        reproduzirMusicas(u, p.getMusicas(), p.getNome());
        controlador.guardarEstado();
    }

    private void verBiblioteca(Utilizador u) {
        if (!controlador.permissaoBiblioteca(u)) {
            System.out.println("O seu Plano de Subscrição não tem acesso a esta ferramenta. Caso deseje, pode mudar o seu Plano de Subscrição no seu perfil.");
            return;
        }

        System.out.println("\n--- Biblioteca ---");
        List<Object> biblioteca = u.getBiblioteca();

        if (biblioteca.isEmpty()) {
            System.out.println("A sua biblioteca está vazia.");
            return;
        }

        int index = 1;
        for (Object o : biblioteca) {
            if (o instanceof Playlist) {
                System.out.println(index++ + "- " + ((Playlist) o).getNome());
            } else if (o instanceof Album) {
                System.out.println(index++ + "- " + ((Album) o).getNome());
            }
        }

        String[] opcoes = {
                "Remover Album/Playlist da minha biblioteca"
        };

        NewMenu menu = new NewMenu("", opcoes);
        menu.setHandler(1, () -> removerObjeto(u,biblioteca));
        menu.run();
    }

    private void removerObjeto(Utilizador u, List<Object> biblioteca) {
        System.out.print("Indique o número do item que deseja remover: ");
        int escolha;

        try {
            escolha = Integer.parseInt(sc.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (escolha < 0 || escolha >= biblioteca.size()) {
            System.out.println("Número inválido.");
            return;
        }

        Object removido = biblioteca.get(escolha);

        boolean sucesso = controlador.removerItemDaBiblioteca(u, removido);

        if (sucesso) {
            System.out.println("Item removido com sucesso.");
            controlador.guardarEstado();
        } else {
            System.out.println("Erro ao remover o item.");
        }
    }

    private void mudarPlanoSubscricao(Utilizador u) {
        System.out.println("\nPlanos disponíveis:");
        System.out.println("1- Free");
        System.out.println("2- PremiumBase");
        System.out.println("3- PremiumTop");
        System.out.print("Escolha o número do novo plano: ");

        String escolha = sc.nextLine();
        PlanoSub novoPlano = switch (escolha) {
            case "1" -> new Free();
            case "2" -> new PremiumBase();
            case "3" -> new PremiumTop();
            default -> null;
        };

        if (novoPlano == null) {
            System.out.println("Opção inválida. Plano não alterado.");
            return;
        }

        boolean sucesso = controlador.mudarPlanoDeUtilizador(u, novoPlano);
        if (sucesso) {
            System.out.println("Plano alterado com sucesso para: " + novoPlano.toString());
        } else {
            System.out.println("Já está subscrito ao plano " + novoPlano.toString() + ".");
        }

        controlador.guardarEstado();
    }

    private void ouvirMusica(Utilizador u) {
        System.out.print("Nome da música: ");
        String nome = sc.nextLine().trim();

        Musica musica = controlador.obterMusicaPorNome(nome);
        if (musica != null) {
            List<Musica> musicas = new ArrayList<>();
            musicas.add(musica);
            reproduzirMusicas(u, musicas, nome);
        } else {
            System.out.println("Música não encontrada.");
        }

        controlador.guardarEstado();
    }

    private void criarPlaylists(Utilizador u) {
        if (!controlador.permissaoCriarPlaylists(u)) {
            System.out.println("O seu Plano de Subscrição não tem acesso a esta ferramenta. Caso deseje, pode mudar o seu Plano de Subscrição no seu perfil.");
            return;
        }

        System.out.print("Nome da nova playlist: ");
        String nomePlaylist = sc.nextLine();

        List<Musica> todasMusicas = controlador.getMusicas();

        if (todasMusicas.isEmpty()) {
            System.out.println("Não existem músicas disponíveis no sistema. Não é possível criar playlist.");
            return;
        }

        List<Musica> selecionadas = new ArrayList<>();

        while (selecionadas.isEmpty()) {
            System.out.println("\nLista de músicas disponíveis:");
            for (int i = 0; i < todasMusicas.size(); i++) {
                Musica m = todasMusicas.get(i);
                System.out.println((i + 1) + " - " + m.getNome() + " (" + m.getInterprete() + ")");
            }
            System.out.println("Insira os números das músicas a adicionar (separados por vírgula): ");
            String[] escolhas = sc.nextLine().split(",");

            for (String escolha : escolhas) {
                try {
                    int indice = Integer.parseInt(escolha.trim()) - 1;
                    if (indice >= 0 && indice < todasMusicas.size()) {
                        selecionadas.add(todasMusicas.get(indice).clone());
                    } else {
                        System.out.println("Índice inválido: " + (indice + 1));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valor inválido: " + escolha);
                }
            }

            if (selecionadas.isEmpty()) {
                System.out.println("Tem de adicionar pelo menos uma música para criar a playlist.");
            }
        }

        System.out.print("Deseja tornar a playlist pública? (s/n): ");
        String resposta = sc.nextLine().trim().toLowerCase();

        controlador.criarPlaylists(u,nomePlaylist, selecionadas, resposta);

        System.out.println("Playlist '" + nomePlaylist + "' criada com sucesso!");
        controlador.guardarEstado();
    }

    private void verAlbuns(Utilizador u) {
        System.out.println("\n--- Álbuns Disponíveis ---");
        Map<String, Album> albuns = controlador.obterAlbunsDisponiveis();

        if (albuns.isEmpty()) {
            System.out.println("Não existem albuns disponíveis.");
            return;
        }

        int index = 1;
        for (Album a : albuns.values()) {
            System.out.println(index++ + "- " + a.getNome() + ", Interprete: " + a.getInterprete());
        }

        String[] opcoes = {
                "Ouvir Album",
                "Adicionar Album à Biblioteca"
        };

        NewMenu menu = new NewMenu("", opcoes);
        menu.setHandler(1, () -> ouvirAlbum(u));
        menu.setHandler(2, () -> adicionarAlbumBiblioteca(u));

        menu.run();
    }

    private void ouvirAlbum(Utilizador u) {
        System.out.print("\nPesquisa: ");
        String nomeAlbum = sc.nextLine().trim();

        String resultado = controlador.ouvirAlbum(u, nomeAlbum);
        if (resultado != null) {
            System.out.println(resultado);
            return;
        }

        Album album = controlador.getAlbum(nomeAlbum);
        List<Musica> musicas = album.getMusicas();
        reproduzirMusicas(u, musicas, album.getNome());
        controlador.guardarEstado();
    }

    private void adicionarAlbumBiblioteca(Utilizador u) {
        if (!controlador.permissaoBiblioteca(u)){
            System.out.println("O seu Plano de Subscrição não tem acesso a esta ferramenta. Caso deseje, pode mudar o seu Plano de Subscrição no seu perfil.");
            return;
        }

        System.out.print("\nPesquisa: ");
        String nomeAlbum = sc.nextLine().trim();

        String resultado = controlador.adicionarAlbumBiblioteca(u, nomeAlbum);
        if (resultado != null) {
            System.out.println(resultado);
            return;
        }
        controlador.guardarEstado();
    }

    private void verPlaylistsPublicas(Utilizador u) {
        System.out.println("\n--- Playlists Públicas ---");

        String resultado = controlador.verPlaylistsPublicas(u);
        if (resultado != null) {
            System.out.println(resultado);
            return;
        }

        Map<String, Playlist> publicas = controlador.getPlaylistsPublicas();
        int index = 1;
        for (Playlist p : publicas.values()) {
            System.out.println(index++ + "- " + p.getNome());
            System.out.println(" Músicas na playlist:");
            for (Musica m : p.getMusicas()) {
                System.out.println(" - " + m.getNome() + " (Interprete: " + m.getInterprete() + ")");
            }
        }

        String[] opcoes = {
                "Ouvir Playlist",
                "Adicionar Playlist à Biblioteca"
        };

        NewMenu menu = new NewMenu("", opcoes);
        menu.setHandler(1, () -> ouvirPlaylist(u, true));
        menu.setHandler(2, () -> adicionarPlaylistBiblioteca(u));

        menu.run();
    }

    private void adicionarPlaylistBiblioteca(Utilizador u) {
        if (!controlador.permissaoBiblioteca(u)) {
            System.out.println("O seu Plano de Subscrição não tem acesso a esta ferramenta. Caso deseje, pode mudar o seu Plano de Subscrição no seu perfil.");
            return;
        }
        System.out.print("\nPesquisa: ");
        String nomePlaylist = sc.nextLine().trim();
        String resultado = controlador.adicionarPlaylistBiblioteca(u, nomePlaylist);
        System.out.println(resultado);
    }

    private void reproduzirMusicas(Utilizador u, List<Musica> musicas, String nomePlaylistOuAlbum) {
        System.out.println("\n--- A ouvir: " + nomePlaylistOuAlbum + " ---");

        int i = 0;
        while (i < musicas.size()) {
            Musica m = musicas.get(i);

            // Verificação de idade se a música for explícita
            if (!controlador.maiorIdadeEexplicita(u,m)) {
                System.out.println("\n⚠️ Deve ser maior de idade para conseguir ouvir a música \"" + m.getNome() + "\".");
                i++;
                continue;
            }
            // Processar audição
            controlador.processarAudicao(u, m);

            System.out.println("\n--- Música: " + m.getNome() + " ---");
            if (controlador.musicaExplicita(m)) {
                System.out.println("⚠\uFE0F Atenção: Esta música é explícita e pode conter linguagem forte.");
            }
            System.out.println("\nInterprete: " + m.getInterprete());
            if (controlador.musicaMultimedia(m)) {
                System.out.println("\nVídeo: " + controlador.getVideoMusica(m));
            }
            System.out.println("\nLetra da música: \n" + m.getLetra().replace("\\n", "\n"));
            System.out.println("\n  ⬅\uFE0F (r-retroceder)  ⏹\uFE0F (0-parar reprodução) ➡\uFE0F (a-avançar)");

            boolean inputReceived = false;
            long start = System.currentTimeMillis();
            String input = "";

            while ((System.currentTimeMillis() - start) < 10000) {
                try {
                    if (System.in.available() > 0) {
                        input = sc.nextLine().trim().toLowerCase();
                        if (!input.isEmpty()) {
                            inputReceived = true;
                            break;
                        }
                    }
                    Thread.sleep(100);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            controlador.adicionarMusica(m);
            if (!inputReceived) {
                if (i < musicas.size() - 1) {i++;
                } else {break;
                }
            } else if (input.equals("0")) {
                System.out.println("\nReprodução parada.");
                break;
            } else if (controlador.permissaoAvancarERetroceder(u) && input.equals("a")) {
                if (i < musicas.size() - 1) {i++;
                } else {break;
                }
            } else if (controlador.permissaoAvancarERetroceder(u) && input.equals("r")) {
                if (i > 0) {i--;}
            } else {
                System.out.println("Comando inválido.");
            }
        }
        controlador.adicionarUtilizador(u);
        controlador.guardarEstado();
    }

    private void estatisticas() {
        Musica maisOuvida = controlador.getMusicaMaisReproduzida();
        if (maisOuvida != null) {
            System.out.println("\n Música mais reproduzida:");
            System.out.println("Nome: " + maisOuvida.getNome());
            System.out.println("Intérprete: " + maisOuvida.getInterprete());
            System.out.println("Número de reproduções: " + maisOuvida.getContador());
        } else {
            System.out.println("\nAinda não existem músicas reproduzidas.");
        }

        Utilizador u = controlador.getUtilizadorComMaisPlaylists();
        if (u != null && !u.getPlaylists().isEmpty()) {
            System.out.println("\n Utilizador com mais playlists:");
            System.out.println("Nome: " + u.getNome());
            System.out.println("Número de playlists: " + u.getPlaylists().size());
            System.out.println("Playlists:");
            for (String nomePlaylist : u.getPlaylists().keySet()) {
                System.out.println(" -" + nomePlaylist);
            }
        } else {
            System.out.println("\nAinda não existem playlists criadas por utilizadores.");
        }

        Map<String, Playlist> publicas = controlador.getPlaylistsPublicas();
        if (!publicas.isEmpty()) {
            System.out.println("\n Número de playlists públicas: " + publicas.size());
            System.out.println("Playlists públicas:");
            for (String nome : publicas.keySet()) {
                System.out.println(" -" + nome);
            }
        } else {
            System.out.println("\nAinda não existem playlists públicas.");
        }

        // Utilizador com mais pontos
        Utilizador topPontos = controlador.getUtilizadorComMaisPontos();
        if (topPontos != null) {
            System.out.println("\nUtilizador com mais pontos: " + topPontos.getNome() + " (" + topPontos.getPontos() + " pontos)\n");
        } else {
            System.out.println("\nNenhum utilizador encontrado.\n");
        }

        System.out.println("- Intérprete mais ouvido: " + controlador.getInterpreteMaisOuvido());
        System.out.println("- Género mais ouvido: " + controlador.getGeneroMaisReproduzido());

        System.out.println("Insira a data de início (formato: yyyy-MM-dd):");
        LocalDate inicio = LocalDate.parse(sc.nextLine());
        System.out.println("Insira a data de fim (formato: yyyy-MM-dd):");
        LocalDate fim = LocalDate.parse(sc.nextLine());

        Utilizador maisAtivo = controlador.getUtilizadorMaisAtivo(inicio, fim);
        if (maisAtivo != null) {
            System.out.println("\nUtilizador com mais músicas ouvidas no intervalo:");
            System.out.println("Nome: " + maisAtivo.getNome());
        } else {
            System.out.println("\nNenhum utilizador ouviu músicas nesse período.");
        }
    }
}