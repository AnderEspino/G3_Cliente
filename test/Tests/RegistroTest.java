/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.application.Platform;
import javafx.scene.Node;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Pruebas unitarias para la clase Registro.
 *
 * Estas pruebas verifican diferentes aspectos del proceso de registro de
 * usuarios.
 *
 * @author Diego
 * @author Adrian
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistroTest extends ApplicationTest {

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

    private TextField txt_nombre;
    private TextField txt_email;
    private Hyperlink lbl_hyperlinkCuenta;
    private Button btn_registro;
    private Button btn_verContra2;
    private TextField txt_direccion;
    private TextField txt_zip;
    private TextField txt_tele;
    private TextField txt_contraRepeReve;
    private PasswordField psw_contra;
    private PasswordField psw_contraRepe;
    private Label lbl_error;

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
        lblCuenta = lookup("#lblCuenta").query();
        pane = lookup("#pane").query();

        txt_nombre = lookup("#txt_nombre").query();
        txt_email = lookup("#txt_email").query();
        lbl_hyperlinkCuenta = lookup("#lbl_hyperlinkCuenta").query();
        btn_registro = lookup("#btn_registro").query();
        btn_verContra2 = lookup("#btn_verContra2").query();
        txt_direccion = lookup("#txt_direccion").query();
        txt_zip = lookup("#txt_zip").query();
        txt_tele = lookup("#txt_tele").query();
        txt_contraRepeReve = lookup("#txt_contraRepeReve").query();
        psw_contra = lookup("#psw_contra").query();
        psw_contraRepe = lookup("#psw_contraRepe").query();

    }

    public void limpiarCampos() {
        Platform.runLater(() -> {
            txt_nombre.setText("");
            txt_email.setText("");
            txt_direccion.setText("");
            txt_zip.setText("");
            txt_tele.setText("");
            txt_contraRepeReve.setText("");
            psw_contra.setText("");
            psw_contraRepe.setText("");

        });
    }

    /**
     * Verifica que la ventana de registro se abre correctamente.
     *
     * @author Adrian, Diego
     */
    //@Ignore
    @Test
    public void Test1_comprobar_ventana_abierta() {
        clickOn("#lblCuenta");
        verifyThat("#pane", isVisible());
    }

    /**
     * Verifica que el botón de registro esté deshabilitado con campos vacíos o
     * incompletos.
     *
     * @author Adrian, Diego
     */
    @Ignore
    @Test
    public void Test2_comprobar_boton_deshabilitado() {
        clickOn("#btn_registro");
        verifyThat("#btn_registro", isDisabled());
        clickOn("#txt_nombre").write("NombreUsuario");
        verifyThat("#btn_registro", isDisabled());
        clickOn("#txt_email").write("correo@example.com");
        verifyThat("#btn_registro", isDisabled());
        clickOn("#psw_contra").write("ContraseñaSecreta");
        verifyThat("#btn_registro", isDisabled());
        clickOn("#psw_contraRepe").write("ContraseñaSecreta");
        verifyThat("#btn_registro", isDisabled());
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        verifyThat("#btn_registro", isDisabled());
        clickOn("#txt_zip").write("12345");
        verifyThat("#btn_registro", isDisabled());
        txt_zip.clear();
        clickOn("#txt_tele").write("123456789");
        verifyThat("#btn_registro", isDisabled());
        //Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica que el botón de registro esté habilitado con campos completos y
     * válidos.
     *
     * @author Adrian, Diego
     */
    @Ignore
    @Test
    public void Test3_comprobar_boton_habilitado() {
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write("correo@example.com");
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("123456789");
        verifyThat("#btn_registro", isEnabled());
        //Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica que el patrón del nombre es valido.
     *
     * @author Adrian, Diego.
     */
    @Ignore
    @Test
    public void Test4_comprobar_patron_nombre() {
        clickOn("#txt_nombre").write("NombreUsuarioPatronMasDeCuarentaCaracteres");
        clickOn("#txt_email").write("correo@example.com");
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("123456789");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        //Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica que el patrón de email al registrar un usuario sea valido.
     *
     * @author Adrian, Diego
     */
    @Ignore
    @Test
    public void Test5_comprobar_patron_email() {
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write("correo");
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("123456789");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        clickOn("#txt_email").write("correo@example");
        verifyThat("#lbl_error", isVisible());
        clickOn("#txt_email").write("correo.example.es");
        verifyThat("#lbl_error", isVisible());
        //Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica que las contraseñas coincidan al intentar el registro.
     *
     * @author Adrian, Diego.
     */
    @Ignore
    @Test
    public void Test6_comprobar_contra_coincide() {
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write("correo@example.com");
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("123456789");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        //Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica que el patron de las contraseñas es valido.
     *
     * @author Adrian, Diego.
     */
    @Ignore
    @Test
    public void Test7_comprobar_patron_contraseña() {
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write("correo@example.com");
        clickOn("#psw_contra").write("ContraseñaSecreta");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("123456789");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        //Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica el patrón del campo ZIP al intentar el registro.
     *
     * @author Diego, Adrian
     */
    @Ignore
    @Test
    public void Test8_comprobar_patron_zip() {
        // Ingresar datos en los campos
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write("correo@example.com");
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_tele").write("123456789");
        // Caso de ZIP code con formato incorrecto (contiene guion bajo)
        clickOn("#txt_zip").write("12345-E2");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        // Caso de ZIP code con guion bajo (no válido)
        clickOn("#txt_zip").write("123_5");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        // Limpiar campos
        limpiarCampos();
    }

    /**
     * Verifica que el patrón del campo de teléfono es valido.
     *
     * @author Diego, Adrian
     */
    @Ignore
    @Test
    public void Test9_comprobar_patron_telefono() {
        // Ingresar datos en los campos
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write("correo@example.com");
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        // Caso de número de teléfono con formato incorrecto
        clickOn("#txt_tele").write("123-456-7890");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        // Caso de número de teléfono con demasiados dígitos
        clickOn("#txt_tele").write("1234567890123456");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());
        limpiarCampos();
    }

    /**
     * Verifica la visibilidad de los campos de contraseña y contraseña
     * repetida.
     *
     * @author Adrian, Diego.
     */
    @Ignore
    @Test
    public void TestA_OjosVisibles() {
        verifyThat("#psw_contra", isVisible());
        clickOn("#btn_verContra");
        verifyThat("#txt_contraReve", isVisible());
        clickOn("#btn_verContra");
        verifyThat("#psw_contra", isVisible());
        verifyThat("#psw_contraRepe", isVisible());
        clickOn("#btn_verContra2");
        verifyThat("#txt_contraRepeReve", isVisible());
        clickOn("#btn_verContra2");
        verifyThat("#psw_contraRepe", isVisible());
    }

    /**
     * Verifica que el enlace de cuenta redirige correctamente a la ventana de
     * inicio.
     *
     * @author Adrian, Diego
     */
    //@Test
    public void TestB_Comprobar_lblCuenta() {
        clickOn("#lbl_hyperlinkCuenta");
        verifyThat("#ventanaInicio", isVisible());

    }

    /**
     * Verifica que el proceso de registro es válido.
     *
     * @author Adrian, Diego
     */
    @Ignore
    @Test
    public void TestC_Registro_Correcto() {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(1000); // Generar un número aleatorio entre 0 y 999
        String correo = "correo" + numeroAleatorio + "@example.com";
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write(correo);
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("612345678");
        clickOn("#btn_registro");
        verifyThat("Has logrado registrarte", Node::isVisible);

    }

    /**
     * Método que prueba si el usuario a registrar ya existe en la base de
     * datos. En este caso Prueba@gmail.com ya exite en la bda
     *
     * @author Ander, Diego, Adrian
     */
    @Test
    public void TestD_Usuario_Ya_Existe() {
        String correo = "Prueba@gmail.com";
        clickOn("#txt_nombre").write("NombreUsuario");
        clickOn("#txt_email").write(correo);
        clickOn("#psw_contra").write("ContraseñaSecreta01");
        clickOn("#psw_contraRepe").write("ContraseñaSecreta01");
        clickOn("#txt_direccion").write("Calle Ejemplo 123");
        clickOn("#txt_zip").write("12345");
        clickOn("#txt_tele").write("612345678");
        clickOn("#btn_registro");
        verifyThat("#lbl_error", isVisible());

    }

}
