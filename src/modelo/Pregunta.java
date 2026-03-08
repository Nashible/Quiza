package modelo;

import java.util.List;
import java.io.Serializable;

public class Pregunta implements Serializable {

    public enum Tipo {
        MULTIPLE, TEXTO
    }

    private String enunciado;
    private List<String> opciones;
    private int respuestaCorrecta;
    private String respuestaTexto;
    private String imagen;
    private Tipo tipo;

    public Pregunta(String enunciado, List<String> opciones, int respuestaCorrecta, String imagen) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.imagen = imagen;
        this.tipo = Tipo.MULTIPLE;
    }

    public Pregunta(String enunciado, String respuestaTexto, String imagen) {
        this.enunciado = enunciado;
        this.respuestaTexto = respuestaTexto;
        this.imagen = imagen;
        this.tipo = Tipo.TEXTO;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public String getImagen() {
        return imagen;
    }
}
