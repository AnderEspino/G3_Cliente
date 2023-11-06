package Tests;

import java.util.concurrent.TimeoutException;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ander, Diego
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InicioSesionTest extends ApplicationTest {

    private Label error;
    private Button btn_verContra;
    private ImageView img_ojo;
    private TextField txt_contraReve;
    private TextField textEmail;
    private Button btnInicioSesion;
    private PasswordField pswContraseña;
    private Stage stage;
    private Hyperlink lblCuenta;
    private Pane pane;
    private Pane pane2;

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
        textEmail = lookup("#textEmail").query();
        pswContraseña = lookup("#pswContraseña").query();
        btnInicioSesion = lookup("#btnInicioSesion").query();
        btn_verContra = lookup("#btn_verContra").query();
        txt_contraReve = lookup("#txt_contraReve").query();
        lblCuenta = lookup("#lblCuenta").query();
        pane = lookup("#pane").query();
        pane2 = lookup("#pane2").query();

    }

    /**
     * Método de prueba para comprobar si la ventana de inicio está abierta.
     *
     * @author Ander
     */
    @Test
    public void Test1_comprobar_ventana_abierta() {
        verifyThat("#ventanaInicio", isVisible());
    }

    /**
     * Método de prueba para verificar si el botón de inicio está habilitado
     * cuando se ingresan credenciales válidas.
     *
     * @author Ander
     */
    @Test
    public void Test2_comprobar_boton_inicio_habilitado() {
        getCampos();
        clickOn("#textEmail").write("ejemplo@gmail.com");
        clickOn("#pswContraseña").write("Contraseña01");
        verifyThat("#btnInicioSesion", isEnabled());
        textEmail.clear();
        pswContraseña.clear();
    }

    /**
     * Método de prueba para verificar que el botón de inicio está deshabilitado
     * cuando no se ingresan credenciales.
     *
     * @author Ander
     */
    @Test
    public void Test3_comprobar_boton_inicio_deshabilitado() {
        getCampos();
        verifyThat("#btnInicioSesion", isDisabled());
        clickOn("#textEmail").write("ejemplo@gmail.com");
        clickOn("#btnInicioSesion");
        verifyThat("#btnInicioSesion", isDisabled());
        textEmail.clear();
        clickOn("#pswContraseña").write("Contraseña01");
        clickOn("#btnInicioSesion");
        verifyThat("#btnInicioSesion", isDisabled());
        pswContraseña.clear();
    }

    /**
     * Método de prueba para verificar el patrón incorrecto del correo
     * electrónico.
     *
     * @author Ander
     */
    @Test
    public void Test4_PatronEmailIncorrecto() {
        clickOn("#textEmail").write("ejemplo");
        clickOn("#pswContraseña").write("Contraseña01");
        clickOn("#btnInicioSesion");
        verifyThat("#error", isVisible());
        textEmail.clear();
        pswContraseña.clear();

        clickOn("#textEmail").write("ejemplo.com");
        clickOn("#pswContraseña").write("Contraseña01");
        clickOn("#btnInicioSesion");
        verifyThat("#error", isVisible());
        textEmail.clear();
        pswContraseña.clear();

    }

    /**
     * Método de prueba para verificar si el correo electrónico excede el número
     * máximo de caracteres permitidos.
     *
     * @author Ander
     */
    @Test
    public void Test5_MaxCaracteres() {
        clickOn("#textEmail").write("emailconpatronmasde40caracteres@gmail.com");
        clickOn("#pswContraseña").write("Contraseña01");
        clickOn("#btnInicioSesion");
        verifyThat("#error", isVisible());
        textEmail.clear();
        pswContraseña.clear();
    }

    /**
     * Método de prueba para verificar si se muestra un error al ingresar una
     * contraseña incorrecta.
     *
     * @author Ander
     */
    @Test
    public void Test6_ContraseñaIncorrecta() {

        clickOn("#textEmail").write("ejemplo@example.com");
        clickOn("#pswContraseña").write("contra");
        clickOn("#btnInicioSesion");
        verifyThat("#error", isVisible());
        textEmail.clear();
        pswContraseña.clear();

        clickOn("#textEmail").write("ejemplo@example.com");
        clickOn("#pswContraseña").write("Contraseña");
        clickOn("#btnInicioSesion");
        verifyThat("#error", isVisible());
        textEmail.clear();
        pswContraseña.clear();

        clickOn("#textEmail").write("ejemplo@example.com");
        clickOn("#pswContraseña").write("contraseña01");
        clickOn("#btnInicioSesion");
        verifyThat("#error", isVisible());
        textEmail.clear();
        pswContraseña.clear();

    }

    /**
     * Método de prueba para verificar la visibilidad del campo de contraseña.
     *
     * @author Ander
     */
    @Test
    public void Test7_OjoVisible() {
        verifyThat("#pswContraseña", isVisible());
        clickOn("#btn_verContra");
        verifyThat("#txt_contraReve", isVisible());
        clickOn("#btn_verContra");
        verifyThat("#pswContraseña", isVisible());

    }

    /**
     * Método de prueba para verificar si el hipervínculo de la cuenta está
     * habilitado y muestra el panel correspondiente.
     *
     * @author Ander
     */
    @Test
    public void Test9_hyperlink_cuenta_habilitado() {
        clickOn("#lblCuenta");
        verifyThat("#pane", isVisible());
    }

    
    @Test
    public void TestA_inicio_sesion_funcional() {
        clickOn("#textEmail").write("antoni@gmail.com");
        clickOn("#pswContraseña").write("Abcd*1234");
        clickOn("#btnInicioSesion");
        verifyThat("#pane2", isVisible());

    }
     
}
