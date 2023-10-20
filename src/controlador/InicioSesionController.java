/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.IncorrectCredentialsException;
import excepciones.PasswordDoesntMatchException;
import excepciones.UserDoesntExistsException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.SocketFactory;

/**
 * Esta clase funciona como controlador de la ventana de Inicio de Sesion.
 *
 * @author Ander, Diego, Adrian
 */
public class InicioSesionController {

    @FXML
    private Stage stage;
    @FXML
    private Label lbl_Inicio;
    @FXML
    private Label error;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPassword;
    @FXML
    private TextField textEmail;
    @FXML
    private Button btnInicioSesion;
    @FXML
    private Label lblCuenta;
    @FXML
    private PasswordField pswContraseña;
    @FXML
    //Mediante este patron controlamos que la contraseña no contenga menos de 6 carácteres, que contenga al menos una letra y al menos una minuscula.
    private static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,}$";
    @FXML
    //Esta linea crea un objeto Pattern que valida si la contraseña cumple con el patron correcto.
    private static final Pattern PASSWORD__PATTERN = Pattern.compile(PASSWORD_REGEX);

    @FXML
    // Esta linea creamos un logger que se utiliza para registrar mensajes y eventos en una aplicación Java.
    private static final Logger LOGGER = Logger.getLogger("/controlador/InicioSesionController");

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {

        LOGGER.info("Iniciando la ventana de Inicio de Sesion");

        Scene scene = new Scene(root);

        //El boton inicio de sesion esta deshabilitado.
        btnInicioSesion.setDisable(true);
        //El campo email estará habilitado.
        textEmail.setDisable(false);
        //El campo contraseña estará habilitado.
        pswContraseña.setDisable(false);
        //El campo error esta habilitado.
        error.setDisable(false);
        //El texto cuenta esta habilitado.
        lblCuenta.setDisable(false);
        //El foco estará puesto en el campo email del usuario (textEmail).
        textEmail.requestFocus();

        //Hacemos que el lbl error no se vea
        error.setVisible(false);
        //El título de la ventana es “InicioSesion”.
        stage.setTitle("InicioSesion");

        //La ventana no es redimensionable
        stage.setResizable(false);

        stage.setScene(scene);
        //Mediante este evento llamamos al metodo de cambiar a la ventana de registro.
        lblCuenta.setOnMouseClicked(event -> {
            handleLblCuentaClick();
        });
        //Mediante esta propiedad llamamos al metodo camposInformados()
        textEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            camposInformados();
        });
        //Mediante esta propiedad llamamos al metodo camposInformados()
        pswContraseña.textProperty().addListener((observable, oldValue, newValue) -> {
            camposInformados();
        });
        stage.setOnCloseRequest(this::handleCloseRequest);
        stage.show();
    }

    /**
     * Metodo que se va a ejecutar una vez pulsado el boton signIn en el cual se
     * controlan todos los campos y excepciones posibles
     *
     * @param event Un parametro devuelto de una accion
     */
    @FXML
    private void handleSignInAction(ActionEvent event) {

        try {

            if (camposInformados() && maxCarecteres()) {
                /* User user = new User();
                user.setEmail(textEmail.getText());
                user.setPassword(pswContraseña.getText());

                SocketFactory fac = new LogicableFactory();

                fac.getDataTraffic().signIn(user);
                 */

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/controllers/VLogOut.fxml"));

                Parent root = loader.load();

                UsuarioController controller = ((UsuarioController) loader.getController());
                controller.setStage(stage);
                controller.initStage(root);

            }

        } catch (Exception ex) {
            //Hacemos que el lbl error se vea
            error.setVisible(true);
            // Manejo de excepciones
            LOGGER.log(Level.SEVERE, "Error durante el inicio de sesión", ex);
            error.setDisable(false);
            if (ex instanceof IncorrectCredentialsException) {
                error.setText("Email o contraseña incorrectos!");
            } else if (ex instanceof UserDoesntExistsException) {
                error.setText("El usuario no existe.");
            } else if (ex instanceof PasswordDoesntMatchException) {
                error.setText("La contraseña no coincide.");
            } else if (ex instanceof ConnectException) {
                error.setText("El servidor es inaccesible.");
            } else {
                error.setText("Ocurrió un error desconocido durante el inicio de sesión.");
            }
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Mediante este verificamos que no haya campos vacios
     *
     * @return boolean
     */
    private boolean camposInformados() {
        //Validamos que el campo email no esta vacio
        if (textEmail.getText().trim().isEmpty()) {
            error.setText("No puedes dejar el campo vacio");
            //Deshabilitamos el boton de inicio de sesion
            btnInicioSesion.setDisable(true);
            
            return false;
            //Validamos que el campo contraseña no esta vacio
        } else if (pswContraseña.getText().trim().isEmpty()) {
            error.setText("No puedes dejar el campo vacio");
            //Deshabilitamos el boton de inicio de sesion
            btnInicioSesion.setDisable(true);
            return false;
        } else {
            //Habilitamos el boton de inicio de sesion
            btnInicioSesion.setDisable(false);
            return true;
        }

    }

    /**
     * Este metodo sirve para validar la cantidad de caracteres que va a
     * contener el texto email y la contraseña.
     *
     * @author Ander
     */
    private boolean maxCarecteres() {
        //Comprobamos que el campo de email no contiene más de 40 caracteres 
        if (textEmail.getText().trim().length() >= 40) {
            //Hacemos que el lbl error se vea
            error.setVisible(true);
            error.setText("No se puede superar los 40 caracteres");
            return false;
            //Comprobamos si la contraseña cumple con el patron
        } else if (PASSWORD__PATTERN.matcher(pswContraseña.getText()).matches()) {
            //Hacemos que el lbl error se vea
            error.setVisible(true);
            error.setText("La contraseña debe de contener al menos 6 caracteres y una mayus y minuscula");
            return false;
        } else {
            return true;
        }

    }

    /**
     * Mediante este label podemos ir a la ventana de Registro.
     *
     * @author Ander
     */
    @FXML
    private void handleLblCuentaClick() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registro.fxml"));
            Parent root = loader.load();

            RegistroController registro = (RegistroController) loader.getController();
            registro.initStage(root);

            //Creamos el escenario para la nueva vista
            Stage nuevaVista = new Stage();
            //Creamos la escena para la nueva vista
            Scene scene = new Scene(root);
            nuevaVista.setScene(scene);
            nuevaVista.show();

            Stage ventanaActual = (Stage) lblCuenta.getScene().getWindow();
            ventanaActual.close();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error al cargar la nueva vista", ex);
        }
    }

    /**
     * Este metodo es una verificacion cuando el usuario le da al boton de
     * salir.
     *
     * @author Ander
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
            stage.close();
        } else {
            event.consume();
        }

    }

}
