/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *  Esta clase muestra un mensaje diciendo que el usuario introducido no existe.
 * @author Ander
 */
public class UserDoesntExistsException extends Exception {

    public UserDoesntExistsException(String msg) {
        super(msg);
    }
}
