/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static controlador.RegistroController.LOGGER;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.User;

/**
 * Esta clase funciona como el controlador de la ventana Usuario.
 *
 * @author Ander
 */
public class UsuarioController {

    @FXML
    private Pane pane;
    @FXML
    private Button btn_CerrarSesion;
    @FXML
    private Label lbl_Saludo;
    @FXML
    private ImageView img_Perfil;
    @FXML
    private Label lbl_usuario;
    @FXML
    private Label lbl_email;
    @FXML
    private Stage stage;

    private User user;

    /**
     * Initializes the controller class.
     *
     * @author Ander, Diego
     * @param root
     * @param usuario
     */
    public void initStage(Parent root, User usuario) {
        LOGGER.info("Iniciando Sesion");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Usuario");
        //El campo de usuario está deshabilitado.
        lbl_usuario.setDisable(true);
        //El campo de email está deshabilitado.
        lbl_email.setDisable(true);
        //El campo saludo esta deshabilitado.
        lbl_Saludo.setDisable(true);
        //El boton cerrar sesion esta habilitado.
        btn_CerrarSesion.setDisable(false);
        //Mediante esta accion llamamos al metodo cerrarSesion.
        btn_CerrarSesion.setOnAction(this::cerrarSesion);
        img_Perfil.setImage(new Image("/utilidades/perfil.png"));

        lbl_email.setText(usuario.getEmail());

        lbl_usuario.setText(usuario.getNombre());
        lbl_Saludo.setText("Hola " + usuario.getNombre());
        stage.setOnCloseRequest(this::handleCloseRequest);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Este metodo es una verificacion cuando el usuario le da al boton de
     * salir.
     *
     * @author Ander
     * @param event
     */
    private void handleCloseRequest(WindowEvent event) {
        //Creamos un nuevo objeto Alerta
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        //Mostramos una alerta de confirmacion.
        alert.setContentText("¿Estas seguro que deseas salir de la aplicacion?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }

    }

    /**
     * Mediante este metodo cerramos la sesion del usuario.
     *
     * @author Ander, Diego
     * @param event
     */
    public void cerrarSesion(ActionEvent event) {
        try {
            //Creamos un nuevo objeto Alerta
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Cerrar Sesion");
            //Mostramos una alerta de confirmacion.
            alert.setContentText("¿Estas seguro que deseas cerrar la sesion?");

            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                //Aqui recogemos la escena de la ventana del boton de cerrar sesion y la guardamos en una variable
                Stage ventanaActual = (Stage) btn_CerrarSesion.getScene().getWindow();
                //Cerramos la ventana
                ventanaActual.close();
                //Cargamos la vista de la ventana de inicio de sesion
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesion.fxml"));

                Parent root = loader.load();

                InicioSesionController inicio = loader.getController();
                inicio.setStage(stage);
                inicio.initStage(root);
                stage.show();
            } else {
                event.consume();
            }
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param user este es el usuario que se le pasa por el inicio de sesion
     */
    public void getUser(User user) {
        this.user = user;
    }
}


