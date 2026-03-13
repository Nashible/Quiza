package modelo;

import java.io.Serializable;

public class Jugador implements Serializable {

    private String nombre;
    private String tema;
    private int puntuacion;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntuacion = 0;
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
}
