import Controller.SpotifUMController;
import Model.SpotifUMGestor;
import Utils.Data;
import View.UI;

public class Main {
    public static void main(String[] args) {
        String ficheiroEstado = "estado.dat";

        SpotifUMGestor gestor = Data.carregarEstado(ficheiroEstado);

        SpotifUMController controller = new SpotifUMController(gestor);

        UI menu = new UI(controller);

        menu.run();
    }
}
