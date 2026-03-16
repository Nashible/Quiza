package servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:postgresql://ep-purple-sun-abs2j3jh-pooler.eu-west-2.aws.neon.tech/neondb?sslmode=require";
    private static final String USUARIO = "neondb_owner";
    private static final String CONTRASENA = "npg_fObuhH7YTCe2";

    
    public static Connection getConnection() throws SQLException {
        try {
           
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver PostgreSQL no encontrado.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}