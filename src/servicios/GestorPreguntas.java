package servicios;

import modelo.Jugador;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorPreguntas {

    private static final String ARCHIVO_RANKING = "ranking.dat";
    private List<Jugador> ranking = new ArrayList<>();

    public GestorPreguntas() {

        ranking.addAll(cargarRankingArchivo());

        ranking = sincronizarConDB(ranking);
    }

    public List<Jugador> getRanking() {
        return ranking;
    }

    public void agregarJugador(Jugador j) {

    boolean actualizado = false;

    for (Jugador existente : ranking) {
        if (existente.equals(j)) {
           
            if (j.getPuntuacion() > existente.getPuntuacion()) {
                ranking.remove(existente);
                ranking.add(j);
            }
            actualizado = true;
            break;
        }
    }

    if (!actualizado) {
        ranking.add(j);
    }

    guardarRankingArchivo();
    guardarRankingDB(j);
}

    private void guardarRankingArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_RANKING))) {
            oos.writeObject(ranking);
        } catch (IOException e) {
            System.err.println("Error al guardar ranking en archivo .dat");
            e.printStackTrace();
        }
    }

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

    private void guardarRankingDB(Jugador j) {
        try (Connection con = ConexionBD.getConnection()) {

            int idUsuario = -1;
            String sqlUsuario = "SELECT id_usuario FROM Usuario WHERE nombre = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlUsuario)) {
                ps.setString(1, j.getNombre());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idUsuario = rs.getInt("id_usuario");
                } else {

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

    private List<Jugador> sincronizarConDB(List<Jugador> rankingArchivo) {
        List<Jugador> rankingFinal = new ArrayList<>(rankingArchivo);
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "SELECT u.nombre, p.tema, p.puntuacion FROM Puntuacion p "
                    + "JOIN Usuario u ON p.id_usuario = u.id_usuario";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                Set<String> existentes = new HashSet<>();
                
                for (Jugador j : rankingArchivo) {
                    existentes.add(j.getNombre() + "|" + j.getTema());
                }

                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String tema = rs.getString("tema");
                    int puntuacion = rs.getInt("puntuacion");

                    String clave = nombre + "|" + tema;
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
