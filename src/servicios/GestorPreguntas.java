package servicios;

import modelo.Jugador;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Clase GestorPreguntas
 * Encargada de manejar el ranking, guarda los datos en serilizable y BD
*/
public class GestorPreguntas {

    private static final String ARCHIVO_RANKING = "ranking.dat";
    private List<Jugador> ranking = new ArrayList<>();

    public GestorPreguntas() {
//Añadimos las preguntas del archivo
        ranking.addAll(cargarRankingArchivo());
//Las juntamos con las de la BD online
        ranking = sincronizarConDB(ranking);
    }
/**Devuelve la lista actual, usado por la interfaz para mostrar la tabla*/
    public List<Jugador> getRanking() {
        return ranking;
    }
/**Añade o actualiza un jugador del ranking*/
    public void agregarJugador(Jugador j) {
//variable para saber si ya existia el jugador
    boolean actualizado = false;
//Recorreos la lista para buscar si ya existe el jugador
    for (Jugador existente : ranking) {
        if (existente.equals(j)) {
 //Si existe comparamos y nos quedamos con el de mayor puntuación
            if (j.getPuntuacion() > existente.getPuntuacion()) {
                ranking.remove(existente);
                ranking.add(j);
            }
            actualizado = true;
            break;
        }
    }
//Si no existia se añade el nuevo jugador
    if (!actualizado) {
        ranking.add(j);
    }
//Lo guardamos en archivo y BD
    guardarRankingArchivo();
    guardarRankingDB(j);
}
/**Guarda todo el ranking en un archivo Serializable*/
    private void guardarRankingArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_RANKING))) {
            oos.writeObject(ranking);
        } catch (IOException e) {
            System.err.println("Error al guardar ranking en archivo .dat");
            e.printStackTrace();
        }
    }
/**Leeos el .dat y lo convertimos a una lista de jugadores*/
    private List<Jugador> cargarRankingArchivo() {
        File archivo = new File(ARCHIVO_RANKING);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Jugador>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error al cargar ranking desde archivo .dat");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
/**Guardamos la puntuación del jugador en la Base de Datos*/
    private void guardarRankingDB(Jugador j) {
        try (Connection con = ConexionBD.getConnection()) {

            int idUsuario = -1;
            String sqlUsuario = "SELECT id_usuario FROM Usuario WHERE nombre = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlUsuario)) {
                ps.setString(1, j.getNombre());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {//Si existe le asignamos el id
                    idUsuario = rs.getInt("id_usuario");
                } else {
                  //Si no existe insertamos al usuario
                    String insertUsuario = "INSERT INTO Usuario (nombre) VALUES (?)";
                    try (PreparedStatement ps2 = con.prepareStatement(insertUsuario, Statement.RETURN_GENERATED_KEYS)) {
                        ps2.setString(1, j.getNombre());
                        ps2.executeUpdate();
                        ResultSet keys = ps2.getGeneratedKeys();
                        if (keys.next()) {
                            idUsuario = keys.getInt(1);
                        }
                    }
                }
            }
           //Insertamos los datos de la tabla puntuación
            if (idUsuario != -1) {
                String insertPuntuacion = "INSERT INTO Puntuacion (id_usuario, puntuacion, tema) VALUES (?, ?, ?)";
                try (PreparedStatement ps3 = con.prepareStatement(insertPuntuacion)) {
                    ps3.setInt(1, idUsuario);
                    ps3.setInt(2, j.getPuntuacion());
                    ps3.setString(3, j.getTema());
                    ps3.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos, se mantiene en archivo .dat");

        }
    }
/**Une los rankigs del archivo con la Base de Datos*/
    private List<Jugador> sincronizarConDB(List<Jugador> rankingArchivo) {
        //Trabajamos sobre una copia de la lista
        List<Jugador> rankingFinal = new ArrayList<>(rankingArchivo);
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "SELECT u.nombre, p.tema, p.puntuacion FROM Puntuacion p "
                    + "JOIN Usuario u ON p.id_usuario = u.id_usuario";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
     //Usamos un Set porque necesitamos una clave única para no tener duplicados
                Set<String> existentes = new HashSet<>();
                
                for (Jugador j : rankingArchivo) {
                    existentes.add(j.getNombre() + "|" + j.getTema());
                }
             //Obtenemos los datos de cada fila
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String tema = rs.getString("tema");
                    int puntuacion = rs.getInt("puntuacion");
                //Creamos la clave única
                    String clave = nombre + "|" + tema;
                  //Nos aseguramos que no haya duplicados
                    if (!existentes.contains(clave)) {
                        rankingFinal.add(new Jugador(nombre, tema, puntuacion));
                        existentes.add(clave);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("No se pudo sincronizar con la DB, se mantiene ranking del archivo .dat");
        }
        return rankingFinal;
    }
}
