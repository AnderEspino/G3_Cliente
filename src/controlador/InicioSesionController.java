/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Esta clase funciona como controlador de la ventana de Inicio de Sesion.
 *
 * @author Ander, Diego, Adrian
 */
public class InicioSesionController {

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

    /**
     * Initializes the controller class.
     */
    
      public void initStage(Parent root) {

    }
    

}
