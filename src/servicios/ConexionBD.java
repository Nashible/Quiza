package servicios;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase ConexionBD Se encarga de establecer la conexión con la Base de Datos en
 * linea
 *
 * @author Antonio Donoso
 * @version 1.0
 */
public class ConexionBD {

    private static String URL;
    private static String USUARIO;
    private static String CONTRASENA;
    //static para usarlo una vez y lo primero al usar la clase
    static {
        try {//Creo el objeto properties
            Properties props = new Properties();
            //Para leer el contenido desde dentro del proyecto/Jar
            InputStream is = ConexionBD.class.getResourceAsStream("/recursos/config.properties");
           //Leemos el contenido del archivo y lo metemos en memoria
            props.load(is);
        //Obtenemos cada propiedad del archivo y lo guardamos en variables
            URL = props.getProperty("db.url");
            USUARIO = props.getProperty("db.user");
            CONTRASENA = props.getProperty("db.password");

        } catch (Exception e) {
            System.err.println("Error cargando configuración de BD");
            e.printStackTrace();
        }
    }
/**Establece la conexión con Neon Console*/
    public static Connection getConnection() throws SQLException {
        try {
            //Carga el driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver PostgreSQL no encontrado.");
            e.printStackTrace();
        }//Hacemos la conexión con las Base de Datos y nos la devuelve lista para usar
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
