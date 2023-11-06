/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Diego
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest extends ApplicationTest {

    @FXML
    private Pane pane;
    private Button btn_CerrarSesion;
    private Label lbl_Saludo;
    private Label lbl_usuario;
    private Label lbl_email;
    private User user;
    private Button btnInicioSesion;

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    /*@Override public void start(Stage stage) throws Exception {
       new Application().start(stage);
    }*/
    /**
     * Set up Java FX fixture for tests. This is a general approach for using a
     * unique instance of the application in the test.
     *
     * @throws java.util.concurrent.TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(applicaction.Application.class);
    }

    @Before
    public void getCampos() {
        pane = lookup("#pane").query();
        btn_CerrarSesion = lookup("#btn_CerrarSesion").query();
        lbl_Saludo = lookup("#lbl_Saludo").query();
        lbl_usuario = lookup("#lbl_usuario").query();
        lbl_email = lookup("#lbl_email").query();
    }

    @Test
    public void Test1_comprobar_ventana_abierta() {
        clickOn("#textEmail").write("ejemplo@example.com");
        clickOn("#pswContraseña").write("Contraseña01");
        clickOn("#btnInicioSesion");
        verifyThat("#pane", isVisible());
    }
}
