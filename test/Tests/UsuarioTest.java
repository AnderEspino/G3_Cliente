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
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.not;
import org.junit.Assert;
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
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * Pruebas unitarias para la clase UsuarioController.
 * 
 * Estas pruebas verifican el comportamiento de los métodos en la clase UsuarioController.
 * 
 * @author Ander
 * @author Diego
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioTest extends ApplicationTest {

    private User user;

    @FXML
    private Pane pane;
    private Button btn_CerrarSesion;
    private Label lbl_Saludo;
    private Label lbl_usuario;
    private Label lbl_email;
    private Button btnInicioSesion;
    private Pane ventanaInicio;

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
        ventanaInicio = lookup("#ventanaInicio").query();
        btn_CerrarSesion = lookup("#btn_CerrarSesion").query();
        lbl_Saludo = lookup("#lbl_Saludo").query();
        lbl_usuario = lookup("#lbl_usuario").query();
        lbl_email = lookup("#lbl_email").query();
    }

    /**
     * Verifica que la ventana se abre correctamente después de iniciar sesión.
     *
     * @author Ander, Diego
     */
    @Test
    public void Test1_Comprobar_ventana_abierta() {
        clickOn("#textEmail").write("correo638@example.com");
        clickOn("#pswContraseña").write("ContraseñaSecreta01");
        clickOn("#btnInicioSesion");
        verifyThat("#pane", isVisible());
    }

    /**
     * Verifica que el saludo no sea nulo después de iniciar sesión.
     *
     * @author Diego
     */
    @Test
    public void Test2_Comprobar_Saludo() {
        String nulo = "Hola null";
        Assert.assertNotEquals(lbl_Saludo.getText(), nulo);

    }

    /**
     * Verifica que el nombre de usuario no sea nulo después de iniciar sesión.
     * @author Diego
     */
    @Test
    public void Test3_Comprobar_Nombre_Usuario() {
        Assert.assertNotEquals(lbl_usuario.getText(), null);

    }
    
    /**
     * Verifica que el email del usuario no sea nulo después de iniciar sesión.
     * @author Diego
     */
    @Test
    public void Test4_Comprobar_Email_Usuario() {
        Assert.assertNotEquals(lbl_email.getText(), null);

    }
    /**
     * Verifica que el botón de cerrar sesión funcione correctamente.
     * @author Ander
     */
    @Test
    public void Test5_Boton_cerrar_sesion() {
        clickOn("#btn_CerrarSesion");
        clickOn("Aceptar");
        verifyThat("#ventanaInicio", isVisible());

    }
}
