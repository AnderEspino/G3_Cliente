package excepciones;

/**
 * Esta clase muestra un mensaje de error si se deja un campo vacio
 *
 * @author Diego
 *
 */
public class EmptyFieldException extends Exception {

    public EmptyFieldException(String msg) {
        super(msg);
    }
}
