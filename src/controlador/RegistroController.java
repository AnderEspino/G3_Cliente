/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.IncorrectPatternException;
import excepciones.PasswordDoesntMatchException;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Esta clase funciona como el controlador de la ventana de Registro.
 *
 * @author Diego, Adrian
 */
public class RegistroController {

    @FXML
    private Pane pane;
    @FXML
    private Label lbl_registro;
    @FXML
    private Label lbl_error;
    @FXML
    private Label lbl_nombre;
    @FXML
    private Label lbl_mail;
    @FXML
    private Label lbl_passwrd;
    @FXML
    private Label lbl_repeContra;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_email;
    @FXML
    private Label lbl_hyperlinkCuenta;
    @FXML
    private Button btn_registro;
    @FXML
    private Label lbl_direcion;
    @FXML
    private TextField txt_direccion;
    @FXML
    private Label lbl_zip;
    @FXML
    private Label lbl_tele;
    @FXML
    private TextField txt_zip;
    @FXML
    private TextField txt_tele;
    @FXML
    private PasswordField psw_contra;
    @FXML
    private PasswordField psw_contraRepe;
    @FXML
    private Stage stage;

    private String email, contraseña, zip, telefono, nombre;

    private static final String patronZip = "^[0-9]{5}$";
    private static final Pattern zipMatcher = Pattern.compile(patronZip);

    private static final String patronPhone = "(6|7|8|9)\\\\d{8}$";
    private static final Pattern phoneMatcher = Pattern.compile(patronPhone);

    private static final String patronContraseña = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    private static final Pattern passwordMatcher = Pattern.compile(patronContraseña);

    private static final String patronEmail = "^([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{10,40}$";
    private static final Pattern emailMatcher = Pattern.compile(patronEmail);
    protected static final Logger LOGGER = Logger.getLogger("/controlador/RegistroController");

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //La ventana no es redimensionable
        stage.setResizable(false);
        //La ventana tiene como título "Registro"
        stage.setTitle("Registro");
        //El foco estrá en el nombre
        txt_nombre.requestFocus();
        //El botón está deshabilitado
        btn_registro.setDisable(true);
        //El campo de mostrar errores es invisible
        lbl_error.setVisible(false);
        //El campo del hyper-link esthá habilitado
        lbl_hyperlinkCuenta.setDisable(false);
        //Evento hyper-enlace que te envia a la ventana de inicio de sesion
        lbl_hyperlinkCuenta.setOnMouseClicked(this::tienesCuenta);
        //Eventos que habilitan la validacion de los textareas
        txt_nombre.textProperty().addListener(this::estanVacios);
        txt_email.textProperty().addListener(this::estanVacios);
        psw_contra.textProperty().addListener(this::estanVacios);
        psw_contraRepe.textProperty().addListener(this::estanVacios);
        txt_direccion.textProperty().addListener(this::estanVacios);
        txt_zip.textProperty().addListener(this::estanVacios);
        txt_tele.textProperty().addListener(this::estanVacios);
        //Evento del botón registrarse
        btn_registro.setOnAction(this::registrarBotón);
        stage.setOnCloseRequest(this::cerrarVentana);
        stage.show();
    }

    //Constructor de la escena
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void tienesCuenta(MouseEvent event) {
        /*
          Método para cargar la ventana de Inicio de sesión desde la ventana de Registro
          usamos un try-catch para capturar errores, luego mediante el FXMLLoader cargamos la ventana de
          InicioSesion.fxml
         */
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InicioSesion.fxml"));
            Parent root = (Parent) loader.load();
            InicioSesionController signIn = ((InicioSesionController) loader.getController());
            signIn.initStage(root);
        } catch (IOException e) {
            LOGGER.info("Ha ocurrido un error");
        }
    }

    private void registrarBotón(ActionEvent event) {

        try {
            //Comprobamos que el nombre no excede de 15 carácteres
            if (txt_nombre.getText().trim().length() > 15) {
                throw new IncorrectPatternException("El nombre de usuario es demasiado largo");

            }
            //Comprobamos el formato del correo y si no excecde de 40 carácteres
            email = txt_email.getText();
            if (!(emailMatcher.matcher(email).matches()) || txt_email.getText().length() > 40) {
                throw new IncorrectPatternException("El formato no está permitido (ej, xxx@xxx.xxx)");
            }
            //Comprobamos que la contraseña se alfanumerica, tenga mayúsculas, minúsculas y tenga más de 8 carácteres
            contraseña = psw_contra.getText();
            if (!(passwordMatcher.matcher(contraseña).matches())) {
                throw new IncorrectPatternException("El formato no está permitido, introduce más 8 carácteres alfanuméricos"
                        + "añade una minuscula o mayúscula al menos");
                //Comprobamos que las contraseñas coinciden
            } else if (!psw_contra.getText().equals(psw_contraRepe.getText())) {
                throw new PasswordDoesntMatchException("Las contraseñas no coinciden");
            }
            //Comprobamos que el código postal tenga un formato de 5 digitos
            zip = txt_zip.getText();
            if (!(zipMatcher.matcher(zip).matches())) {
                throw new IncorrectPatternException("El formato no está permitido, (ej, 45320");
            }
            //Comprobamos que el teléfono tiene el patrón estándar español de 9 digitos
            telefono = txt_tele.getText();
            if (!(phoneMatcher.matcher(telefono).matches())) {
                throw new IncorrectPatternException("El formato no está permitido, (ej, 643 567 453/ 945 564 234");
            }
            /*
                Las excepciones de IncorrectPasswordException y IncorrectPatternException se mostrarán en el
                lbl_error, las excepciones genericas se mostraran en consola a través de un logger
             */
        } catch (IncorrectPatternException e) {
            txt_nombre.setText("");
            txt_email.setText("");
            psw_contra.setText("");
            psw_contraRepe.setText("");
            txt_direccion.setText("");
            txt_zip.setText("");
            txt_tele.setText("");
            lbl_error.setVisible(true);
            lbl_error.setText(e.getMessage());
        } catch (PasswordDoesntMatchException e) {
            txt_nombre.setText("");
            txt_email.setText("");
            psw_contra.setText("");
            psw_contraRepe.setText("");
            txt_direccion.setText("");
            txt_zip.setText("");
            txt_tele.setText("");
            lbl_error.setVisible(true);
            lbl_error.setText(e.getMessage());
        } catch (Exception e) {
            lbl_error.setVisible(true);
            lbl_error.setText("Ha ocurrido un error inesperado");
            LOGGER.info(e.getMessage());
        }

    }

    private void estanVacios(ObservableValue observable, Object oldValue, Object newValue) {
        /*
           Comprueban que los campos de texto no están vacios, si lo están el botón estará deshabilitado
           si por el contrario, no están vacios, el botón de registro se habilita
         */
        if (!txt_nombre.getText().trim().isEmpty()
                && !txt_email.getText().trim().isEmpty()
                && !txt_direccion.getText().trim().isEmpty()
                && !txt_tele.getText().trim().isEmpty()
                && !txt_zip.getText().trim().isEmpty()
                && !psw_contra.getText().trim().isEmpty()
                && !psw_contraRepe.getText().trim().isEmpty()) {
            btn_registro.setDisable(false);
        } else {
            /*
                Cada vez que salte un error y se muestre en el lbl_error si se escribe en cualquiera
                de los campos el texto se vaciará y se hará invisible
             */
            btn_registro.setDisable(true);
            lbl_error.setText("");
            lbl_error.setVisible(false);
        }
    }

    private void cerrarVentana(Event event) {
        /*
            Método para cerrar la ventana desde el botón X
         */
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        ventana.setHeaderText(null);
        ventana.setTitle("Advertencia");
        ventana.setContentText("¿Estas seguro que deseas salir de la aplicacion?");
        Optional<ButtonType> accion = ventana.showAndWait();
        if (accion.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            event.consume();
        }
    }
}
