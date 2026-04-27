package modelo;

import java.io.Serializable;
import java.util.Objects;
/**
 * Clase Jugador
 * Se encarga de la identidad de los jugadores, el tema que eligen
 * su puntuación y es Serializable para su guardado
 * @author Antonio Donoso
 * @version 1.0
 */
public class Jugador implements Serializable {

    private String nombre;
    private String tema;
    private int puntuacion;
/**Constructor básico*/
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.tema = "";
    }
/**Constructor para cargar el jugador de BD o archivo serializado*/
    public Jugador(String nombre, String tema, int puntuacion) {
        this.nombre = nombre;
        this.tema = tema;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public int getPuntuacion() {
        return puntuacion;
    }
/**Incrementa la puntuación cuando aciertas*/
    public void sumarPunto() {
        puntuacion++;
    }
/**Evitamos duplicados si el jugador comparte nombre y tema*/
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jugador)) {
            return false;
        }
        Jugador jugador = (Jugador) o;
        return Objects.equals(nombre, jugador.nombre)
                && Objects.equals(tema, jugador.tema);
    }
/**Necesario al sobrescribir equals*/
    @Override
    public int hashCode() {
        return Objects.hash(nombre, tema);
    }
/**Usado en depuración*/
    @Override
    public String toString() {
        return "Jugador{"
                + "nombre='" + nombre + '\''
                + ", tema='" + tema + '\''
                + ", puntuacion=" + puntuacion
                + '}';
    }
}
