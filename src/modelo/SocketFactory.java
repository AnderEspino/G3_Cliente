/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Esta clase es una factoria que se encarga de crear sockets.
 *
 * @author Ander
 */
public class SocketFactory {

    /**
     * Con este método creamos el método getSocket, que recoger los datos del
     * socket creado en el SignerClient.
     *
     * @return sign
     */
    public Sign getSocket() {
        Sign sign;
        sign = new SignerClient();
        return sign;
    }
}
