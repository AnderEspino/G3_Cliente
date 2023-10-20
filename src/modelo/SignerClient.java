/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import excepciones.IncorrectCredentialsException;
import excepciones.UserAlreadyExistsException;
import excepciones.UserDoesntExistsException;
import excepciones.UserNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase es el socket del cliente. Dependiendo del metodo que le envie el
 * controlador ejectuta un metodo o otro.
 *
 * @author Ander
 */
public class SignerClient implements Sign {

    private static final ResourceBundle RETO1 = ResourceBundle.getBundle("modelo.Config");
    private static final int PUERTO = Integer.parseInt(RETO1.getString("PORT"));
    private static final String HOST = ResourceBundle.getBundle("modelo.Config").getString("IP");
    MessageType mt;
    private Message msg = null;

    @Override
    public User excecuteLogin(User user) throws excepciones.ConnectException, UserAlreadyExistsException {
        MessageType mst;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            //Enviamos el objecto encapsulado al servidor
            Socket socketCliente = new Socket(HOST, PUERTO);
            oos = new ObjectOutputStream(socketCliente.getOutputStream());
            msg = new Message();
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNUP_REQUEST);
            oos.writeObject(msg);

            //Recibimos el objeto encapsulado del servidor
            ois = new ObjectInputStream(socketCliente.getInputStream());
            msg = (Message) ois.readObject();
            user = msg.getUser();
            //Declaramos una variable int, pues las enumeraciones devuelven valores int
            int decision = msg.getMsg().ordinal();
            oos.close();
            ois.close();
            socketCliente.close();
            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return user;
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistsException("El usuario ya existe");
                case ERROR_RESPONSE:
                    throw new ConnectException("Ha ocurrido algun error en el servidor");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    /**
     * Conecta con el 'SignerServer', recibe la información de la ventana
     * 'iniciar sesión' y verifica que el usuario esté en la base de datos.
     *
     * @param user objeto de tipo Usuario
     * @throws IncorrectCredentialsException esta excepción se lanza cuando hay
     * un error en la contraseña
     * @throws UserNotFoundException esta excepción se lanza cuando no se
     * encuentra el usuario
     * @throws ConnectException esta excepción se lanza cuando hay un error en
     * el servidor
     * @return usuario
     */
    @Override
    public User executeSignIn(User user) throws excepciones.UserNotFoundException, excepciones.ConnectException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            //Enviamos el objecto encapsulado al servidor
            Socket socketCliente = new Socket(HOST, PUERTO);
            oos = new ObjectOutputStream(socketCliente.getOutputStream());
            msg = new Message();
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNIN_REQUEST);
            oos.writeObject(msg);

            //Recibimos el objeto encapsulado del servidor
            ois = new ObjectInputStream(socketCliente.getInputStream());
            msg = (Message) ois.readObject();
            user = msg.getUser();
            int decision = msg.getMsg().ordinal();
            oos.close();
            ois.close();
            socketCliente.close();
            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return user;
                case USER_NOT_FOUND_RESPONSE:
                    throw new UserNotFoundException("El usuario no se ha encontrado");
                case ERROR_RESPONSE:
                    throw new ConnectException("Ha ocurrido un error en el servidor");

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}
