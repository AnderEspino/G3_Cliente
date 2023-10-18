/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 * Esta clase muestra un mensaje de error en el patrón.
 *
 * @author Ander
 */
public class IncorrectPatternException extends Exception {

    public IncorrectPatternException(String msg) {
        super(msg);
    }
}
