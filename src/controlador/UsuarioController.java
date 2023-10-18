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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Esta clase funciona como el controlador de la ventana Usuario.
 *
 * @author 2dam
 */
public class UsuarioController {

    @FXML
    private Pane pane;
    @FXML
    private Button btn_CerrarSesion;
    @FXML
    private Label lbl_Saludo;
    @FXML
    private Pane pane2;
    @FXML
    private ImageView img_Perfil;
    @FXML
    private Label lbl_usuario;
    @FXML
    private Label lbl_email;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {

    }

}
