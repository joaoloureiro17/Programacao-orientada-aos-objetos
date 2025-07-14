package Utils;

import Model.SpotifUMGestor;

import java.io.*;

public class Data {

    public static void guardarEstado(SpotifUMGestor gestor, String nomeFicheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            oos.writeObject(gestor);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o estado: " + e.getMessage());
        }
    }

    public static SpotifUMGestor carregarEstado(String nomeFicheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFicheiro))) {
            return (SpotifUMGestor) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o estado: " + e.getMessage());
            return new SpotifUMGestor();
        }
    }
}
