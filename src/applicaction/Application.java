package applicaction;

import controlador.InicioSesionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Esta clase es la que se encarga de iniciar la aplicacion.
 *
 * @author Ander
 */
public class Application extends javafx.application.Application {

    //Clase main del cliente, inicializa la ventana de inicial de nuestra aplicación
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesion.fxml"));
        Parent root = (Parent) loader.load();
        InicioSesionController inicio = ((InicioSesionController) loader.getController());
        inicio.setStage(primaryStage);
        inicio.initStage(root);
    }

    /**
     * @param args Los argumentos de la linea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
