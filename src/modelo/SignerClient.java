/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import excepciones.IncorrectCredentialsException;
import excepciones.UserAlreadyExistsException;
import excepciones.UserDoesntExistsException;
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

    private static final ResourceBundle archivo = ResourceBundle.getBundle("utilidades.Config");
    private static final int PUERTO = Integer.parseInt(archivo.getString("PORT"));
    private static final String HOST = ResourceBundle.getBundle("utilidades.Config").getString("IP");
    private static final Logger LOGGER = Logger.getLogger("/modelo/SignerClient");
    MessageType mt;
    private Message msg = null;

    @Override
    public User excecuteLogin(User user) throws excepciones.ConnectException, UserAlreadyExistsException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            LOGGER.info("Iniciando registro...");
            //Instanciamos el socket
            Socket socketCliente = new Socket(HOST, PUERTO);
            //Creamos el output y preparamos el encapsulador para enviarlo al servidor
            oos = new ObjectOutputStream(socketCliente.getOutputStream());
            msg = new Message();
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNUP_REQUEST);
            oos.writeObject(msg);

            //Recibimos el objeto encapsulado del servidor
            ois = new ObjectInputStream(socketCliente.getInputStream());
            msg = (Message) ois.readObject();
            user = msg.getUser();
            //Cerramos las conexiónes
            oos.close();
            socketCliente.close();
            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return user;
                case USER_ALREADY_EXISTS_RESPONSE:
                    throw new UserAlreadyExistsException("El usuario ya existe");
                case ERROR_RESPONSE:
                    throw new ConnectException("Ha ocurrido algun error en el servidor");
                case MAX_THREAD_USER:
                    throw new Exception("Demasiados usuarios en el servidor.");
            }
            //Control de errores
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) { 
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Devuleve un objeto user
        return user;
    }

    /**
     * Conecta con el 'SignerServer', recibe la información de la ventana
     * 'iniciar sesión' y verifica que el usuario esté en la base de datos.
     *
     * @param user objeto de tipo Usuario
     * @throws IncorrectCredentialsException esta excepción se lanza cuando hay
     * un error en la contraseña
     * @return usuario
     */
    @Override
    public User executeSignIn(User user) throws excepciones.ConnectException, IncorrectCredentialsException {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            //Instanciamos el socket
            LOGGER.info("Iniciando Sesión...");
            Socket socketCliente = new Socket(HOST, PUERTO);
            //Creamos el output y preparamos el encapsulador para enviarlo al servidor
            oos = new ObjectOutputStream(socketCliente.getOutputStream());
            msg = new Message();
            msg.setUser(user);
            msg.setMsg(MessageType.SIGNIN_REQUEST);
            oos.writeObject(msg);

            //Recibimos el objeto encapsulado del servidor
            ois = new ObjectInputStream(socketCliente.getInputStream());
            msg = (Message) ois.readObject();
            user = msg.getUser();
            //Cerramos las conexiones
            oos.close();
            ois.close();
            socketCliente.close();
            //Dependiendo de el mensaje que reciva lanza o escribe un mensaje nuevo
            switch (msg.getMsg()) {
                case OK_RESPONSE:
                    return user;
                case INCORRECT_CREDENTIALS_RESPONSE:
                    throw new IncorrectCredentialsException("Email o contraseña incorrectos.");
                case ERROR_RESPONSE:
                    throw new ConnectException("Ha ocurrido un error en el servidor.");
                
                case MAX_THREAD_USER:
                    throw new Exception("Demasiados usuarios en el servidor.");

            }
            //Control de excepciones
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) { 
            Logger.getLogger(SignerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Devuelve un obejto user
        return user;
    }
}
