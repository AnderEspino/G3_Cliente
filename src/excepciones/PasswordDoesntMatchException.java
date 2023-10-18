/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *  Esta clase muestra un mensaje de excepcion diciendo que la contraseña y las contraseña repetida introducidas 
 * en el SignUp no coinciden.
 * @author Ander
 */
public class PasswordDoesntMatchException extends Exception {

    public PasswordDoesntMatchException(String msg) {
        super(msg);
    }
}
