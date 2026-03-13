package servicios;

import modelo.Jugador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPreguntas {

    private static final String ARCHIVO_RANKING = "ranking.dat";
    private List<Jugador> ranking = new ArrayList<>();

    public GestorPreguntas() {
        ranking = cargarRanking();
    }

    public List<Jugador> getRanking() {
        return ranking;
    }

    public void agregarJugador(Jugador j) {
        ranking.add(j);
        guardarRanking();
    }

    private void guardarRanking() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_RANKING))) {
            oos.writeObject(ranking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Jugador> cargarRanking() {
        File archivo = new File(ARCHIVO_RANKING);
        if (!archivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Jugador>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}