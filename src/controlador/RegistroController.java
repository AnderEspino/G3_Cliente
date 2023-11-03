/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.ConnectException;
import excepciones.IncorrectPatternException;
import excepciones.PasswordDoesntMatchException;
import excepciones.UserAlreadyExistsException;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Sign;
import modelo.SocketFactory;
import modelo.User;

/**
 * Esta clase funciona como el controlador de la ventana de Registro.
 *
 * @author Diego, Adrian
 */
public class RegistroController {

    private Sign interf;
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
    private Button btn_verContra;
    @FXML
    private Button btn_verContra2;
    @FXML
    private ImageView img_ojo;
    @FXML
    private ImageView img_ojo2;
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
    private TextField txt_contraReve;
    @FXML
    private TextField txt_contraRepeReve;
    @FXML
    private PasswordField psw_contra;
    @FXML
    private PasswordField psw_contraRepe;
    private Stage stage;

    private String email, contraseña, zip, telefono, nombre;

    private static final String patronZip = "^\\d{5}$";
    private static final Pattern zipMatcher = Pattern.compile(patronZip);

    private static final String patronPhone = "^(\\+34|0034|34)?[6|7|9][0-9]{8}$";
    private static final Pattern phoneMatcher = Pattern.compile(patronPhone);

    private static final String patronContraseña = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).+$";
    private static final Pattern passwordMatcher = Pattern.compile(patronContraseña);

    private static final String patronEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern emailMatcher = Pattern.compile(patronEmail);
    protected static final Logger LOGGER = Logger.getLogger("/controlador/RegistroController");

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {
        LOGGER.info("Iniciando la ventana de Registro");
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
        //Los campos de mostrar la contraseña estarán deshabilitados e invisibles
        txt_contraReve.setDisable(true);
        txt_contraReve.setVisible(false);
        txt_contraRepeReve.setDisable(true);
        txt_contraRepeReve.setVisible(false);
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
        btn_registro.setTooltip(new Tooltip("Pulsa para registrarte"));
        //Evento de los botones de visualizar contraseña
        btn_verContra.setOnMouseClicked(event -> revelarContra(event));
        btn_verContra.setTooltip(new Tooltip("Visualizar/Ocultar contraseña"));
        btn_verContra2.setTooltip(new Tooltip("Visualizar/Ocultar contraseña"));
        btn_verContra2.setOnMouseClicked(event -> revelarContraRepe(event));

        stage.setOnCloseRequest(this::cerrarVentana);
        stage.show();
    }

    //Constructor de la escena
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void tienesCuenta(MouseEvent event) {
        /**
         * Método para cargar la ventana de Inicio de sesión desde la ventana de
         * Registro usamos un try-catch para capturar errores, luego mediante el
         * FXMLLoader cargamos la ventana de InicioSesion.fxml
         */
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesion.fxml"));
            Parent root = (Parent) loader.load();
            InicioSesionController signIn = ((InicioSesionController) loader.getController());
            signIn.setStage(stage);
            signIn.initStage(root);

        } catch (IOException e) {
            LOGGER.info("Ha ocurrido un error");
        }
    }

    private void revelarContra(MouseEvent event) {
        /*
          Método para revelar y ocultar las contraseñas de la ventana del primer botón
         */
        int contadorClics = 0;
        int MAX_CLICS = 1; // Número máximo de clics para alternar entre mostrar y ocultar contraseñas

        if (event.getButton()
                .equals(MouseButton.PRIMARY)) {
            contadorClics++;
            txt_contraReve.setDisable(false);
            if (contadorClics % MAX_CLICS == 0) {
                // Cada MAX_CLICS clics, se alterna entre mostrar y ocultar las contraseñas
                psw_contra.setVisible(!psw_contra.isVisible());
                txt_contraReve.setVisible(!txt_contraReve.isVisible());

                if (psw_contra.isVisible()) {
                    psw_contra.setText(txt_contraReve.getText());

                } else {
                    txt_contraReve.setText(psw_contra.getText());

                }

                img_ojo.setImage(new Image(psw_contra.isVisible() ? "/utilidades/abierto.png" : "/utilidades/cerrado.png"));
            }
        }

    }

    private void revelarContraRepe(MouseEvent event) {
        /*
          Método para revelar y ocultar las contraseñas de la ventana del segundo botón
         */
        int contadorClics = 0;
        int MAX_CLICS = 1; // Número máximo de clics para alternar entre mostrar y ocultar contraseñas

        if (event.getButton()
                .equals(MouseButton.PRIMARY)) {
            contadorClics++;
            txt_contraRepeReve.setDisable(false);
            if (contadorClics % MAX_CLICS == 0) {
                // Cada MAX_CLICS clics, se alterna entre mostrar y ocultar las contraseñas
                psw_contraRepe.setVisible(!psw_contraRepe.isVisible());
                txt_contraRepeReve.setVisible(!txt_contraRepeReve.isVisible());

                if (psw_contraRepe.isVisible()) {
                    psw_contraRepe.setText(txt_contraRepeReve.getText());

                } else {
                    txt_contraRepeReve.setText(psw_contraRepe.getText());

                }

                img_ojo2.setImage(new Image(psw_contraRepe.isVisible() ? "/utilidades/abierto.png" : "/utilidades/cerrado.png"));
            }
        }

    }

    private void registrarBotón(ActionEvent event) {
        /*
          Método para Registrar al usuario en la base de datos del programa, realiza validaciones de patrones y otros
          protocolos de seguridad
         */
        try {
            //Comprobamos que el nombre no excede de 15 carácteres
            if (txt_nombre.getText().trim().length() > 15) {
                txt_nombre.setText("");
                throw new IncorrectPatternException("El nombre de usuario es demasiado largo.");

            }
            //Comprobamos el formato del correo y si no excecde de 40 carácteres
            email = txt_email.getText();
            if (!emailMatcher.matcher(email).matches() || email.length() > 40) {
                txt_email.setText("");
                throw new IncorrectPatternException("El formato no está permitido (ej, xxx@xxx.xxx) y no debe tener mas de 40 caracteres.");
            }
            //Comprobamos que la contraseña se alfanumerica, tenga mayúsculas, minúsculas y tenga más de 8 carácteres
            contraseña = psw_contra.getText();
            if (!(passwordMatcher.matcher(contraseña).matches()) || contraseña.length() < 8) {
                psw_contra.setText("");
                txt_contraReve.setText("");
                throw new IncorrectPatternException("Formato erroneo, introduce más 8 carácteres alfanuméricos"
                        + " añade una minuscula o mayúscula al menos.");
                //Comprobamos que las contraseñas coinciden
            } else if (!psw_contra.getText().equals(psw_contraRepe.getText())) {
                psw_contraRepe.setText("");
                txt_contraRepeReve.setText("");
                throw new PasswordDoesntMatchException("Las contraseñas no coinciden.");
            }
            //Comprobamos que el código postal tenga un formato de 5 digitos
            zip = txt_zip.getText();
            if (!(zipMatcher.matcher(zip).matches())) {
                txt_zip.setText("");
                throw new IncorrectPatternException("El formato no está permitido, (ej, 45320).");
            }
            //Comprobamos que el teléfono tiene el patrón estándar español de 9 digitos
            telefono = "+34" + txt_tele.getText();
            if (!(phoneMatcher.matcher(telefono).matches())) {
                txt_tele.setText("");
                throw new IncorrectPatternException("El formato no está permitido, (ej, +34 643 567 453/ 945 564 234).");
            }
            /*
                Las excepciones de IncorrectPasswordException y IncorrectPatternException se mostrarán en el
                lbl_error, las excepciones genericas se mostraran en consola a través de un logger
             */
            User user = new User();
            //Añadimos el nombre al campo nombre del usuario
            user.setNombre(txt_nombre.getText());
            //Añadimos el campo de email al usuario
            user.setEmail(txt_email.getText());
            //Añadimos el campo de contraseña al usuario
            user.setContraseña(psw_contra.getText());
            //Añadimos el campo de la dirección al usuario
            user.setDireccion(txt_direccion.getText());
            //Añadimos el campo del telefono al usuario
            user.setTelefono(Integer.parseInt(txt_tele.getText()));
            //Añadimos el campo del código zip al usuario
            user.setZip_code(Integer.parseInt(txt_zip.getText()));

            //Añadimos los campos con valores predeterminados
            user.setCompañia(user.getCompañia());
            user.setActivo(user.isActivo());
            user.setFecha_ini(user.getFecha_ini());
            user.setPrivilege(user.getPrivilege());

            //LLmamos a la factoria, la cual tiene una instancia de la interfaz
            SocketFactory fac = new SocketFactory();
            //Recogemos el socket
            interf = fac.getSocket();
            //Ejecutamos el método correspondiente
            interf.excecuteLogin(user);

            //Mostramos al usuario que el registro ha sido satisfactorio
            Alert ventana = new Alert(Alert.AlertType.INFORMATION);
            ventana.setHeaderText(null);
            ventana.setTitle("Enhorabuena");
            ventana.setContentText("Has logrado registrarte");
            Optional<ButtonType> accion = ventana.showAndWait();
            if (accion.get() == ButtonType.OK) {
                txt_nombre.setText("");
                txt_email.setText("");
                psw_contra.setText("");
                psw_contraRepe.setText("");
                txt_contraReve.setText("");
                txt_contraRepeReve.setText("");
                txt_direccion.setText("");
                txt_zip.setText("");
                txt_tele.setText("");
                lbl_error.setText("");
                txt_nombre.requestFocus();
                event.consume();
            } else {
                txt_nombre.setText("");
                txt_email.setText("");
                psw_contra.setText("");
                psw_contraRepe.setText("");
                txt_contraReve.setText("");
                txt_contraRepeReve.setText("");
                txt_direccion.setText("");
                txt_zip.setText("");
                txt_tele.setText("");
                lbl_error.setText("");
                txt_nombre.requestFocus();
                event.consume();
            }

        } catch (IncorrectPatternException e) {
            txt_nombre.requestFocus();
            lbl_error.setVisible(true);
            lbl_error.setText(e.getMessage());
            LOGGER.severe(e.getMessage());
        } catch (PasswordDoesntMatchException e) {
            txt_nombre.requestFocus();
            lbl_error.setVisible(true);
            lbl_error.setText(e.getMessage());
            LOGGER.severe(e.getMessage());
        } catch (UserAlreadyExistsException e) {
            txt_nombre.requestFocus();
            lbl_error.setVisible(true);
            lbl_error.setText(e.getMessage());
            LOGGER.severe(e.getMessage());
        } catch (ConnectException e) {
            txt_nombre.requestFocus();
            lbl_error.setVisible(true);
            lbl_error.setText(e.getMessage());
            LOGGER.severe(e.getMessage());
        } catch (Exception e) {
            txt_nombre.requestFocus();
            lbl_error.setVisible(true);
            lbl_error.setText("Ha ocurrido un error inesperado");
            LOGGER.severe(e.getMessage());
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
                && (!psw_contra.getText().trim().isEmpty() || !txt_contraReve.getText().trim().isEmpty())
                && (!psw_contraRepe.getText().trim().isEmpty() || !txt_contraRepeReve.getText().trim().isEmpty())) {
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
