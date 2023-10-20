package applicaction;

import controlador.RegistroController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 2dam
 */
public class Aplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Registro.fxml"));
        Parent root = (Parent) loader.load();
        RegistroController signUp = ((RegistroController) loader.getController());
        signUp.setStage(stage);
        signUp.initStage(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
