package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Jugador implements Serializable {

    private String nombre;
    private String tema;
    private int puntuacion;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
        this.tema = "";
    }

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

    public void sumarPunto() {
        puntuacion++;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tema);
    }

    @Override
    public String toString() {
        return "Jugador{"
                + "nombre='" + nombre + '\''
                + ", tema='" + tema + '\''
                + ", puntuacion=" + puntuacion
                + '}';
    }
}
