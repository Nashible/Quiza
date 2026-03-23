package modelo;

public class Pregunta {

    public enum Tipo {
        MULTIPLE, TEXTO
    }

    private Tipo tipo;
    private String enunciado;
    private String[] opciones; 
    private String respuestaCorrecta;
    private String imagen;
    private String tema;

    
    public Pregunta(String enunciado, String[] opciones, String respuestaCorrecta, String imagen, String tema) {
        this.tipo = Tipo.MULTIPLE;
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
        this.imagen = imagen;
        this.tema = tema;
    }

    
    public Pregunta(String enunciado, String respuestaCorrecta, String imagen, String tema) {
        this.tipo = Tipo.TEXTO;
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.imagen = imagen;
        this.tema = tema;
    }

    
    public Tipo getTipo() {
        return tipo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTema() {
        return tema;
    }
}
