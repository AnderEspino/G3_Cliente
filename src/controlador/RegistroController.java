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
import javafx.scene.layout.Pane;

/**
 * Esta clase funciona como el controlador de la ventana de Registro.
 *
 * @author Ander, Diego, Adrian
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

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {

    }

}
