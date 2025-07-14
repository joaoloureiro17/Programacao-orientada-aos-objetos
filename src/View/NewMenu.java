package View;

import java.util.*;

public class NewMenu {

    // Interfaces auxiliares

    /** Functional interface para handlers. */
    public interface Handler {  // mÃ©todo de tratamento
        public void execute();
    }

    /** Functional interface para pre-condiçoes. */
    /** Podia ser utilizado Predicate<T> */
    public interface PreCondition {
        public boolean validate();
    }

    // Variavel de classe para suportar leitura

    private static Scanner is = new Scanner(System.in);

    // Variaveis de instância
    private String nome;
    private List<String> opcoes;            // Lista de opcoes
    private List<PreCondition> disponivel;  // Lista de pre-condicoes
    private List<Handler> handlers;         // Lista de handlers

    // Construtor

    /**
     * Constructor for objects of class View.NewMenu
     */
    public NewMenu(String nome, String[] opcoes) {
        this.nome=nome;
        this.opcoes = Arrays.asList(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s-> {
            this.disponivel.add(()->true);
            this.handlers.add(()->System.out.println("\nAtenção: Opção não implementada!"));
        });
    }

    // Metodos de instância

    /**
     * Correr o View.NewMenu.
     *
     * Termina com a opção 0 (zero).
     */
    public void run() {
        int op;
        do {
            show();
            op = readOption();
            // testar prÃ©-condiÃ§Ã£o
            if (op>0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op>0) {
                // executar handler
                this.handlers.get(op-1).execute();
            }
        } while (op != 0);
    }

    /**
     * MÃ©todo que regista uma uma pre-condicao numa opcao do View.NewMenu.
     *
     * @param i Ã­ndice da opÃ§Ã£o (comeÃ§a em 1)
     * @param b prÃ©-condiÃ§Ã£o a registar
     */
    public void setPreCondition(int i, PreCondition b) {
        this.disponivel.set(i-1,b);
    }

    /**
     * MÃ©todo para registar um handler numa opÃ§Ã£o do View.NewMenu.
     *
     * @param i indice da opÃ§Ã£o  (comeÃ§a em 1)
     * @param h handlers a registar
     */
    public void setHandler(int i, Handler h) {
        this.handlers.set(i-1, h);
    }

    // MÃ©todos auxiliares

    /** Apresentar o View.NewMenu */
    private void show() {
        System.out.println("\n"+nome);
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"---");
        }
        System.out.println("0 - Sair");
    }

    /** Ler uma opÃ§Ã£o vÃ¡lida */
    private int readOption() {
        int op;
        //Scanner is = new Scanner(System.in);

        System.out.print("Opção: ");
        try {
            String line = is.nextLine();
            op = Integer.parseInt(line);
        }
        catch (NumberFormatException e) { // NÃ£o foi escrito um int
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        return op;
    }
}