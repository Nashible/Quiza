package main;

import java.util.List;
import modelo.Pregunta;
import servicios.LectorJSON;
import vista.VentanaPrincipal;
/**
 * Clase Main
 * 
 * La clase que inicia el programa carga las preguntas
 * y lanza la interfaz gráfica
 * 
 * @author Antonio Donoso
 * @version 1.0 
 */
public class Main {

    public static void main(String[] args) {

        LectorJSON lector = new LectorJSON("/recursos/preguntas.json");
        List<Pregunta> preguntas = lector.leerPreguntas();

        if (preguntas.isEmpty()) {
            System.out.println("No se han cargado preguntas. Revisa el JSON y la ruta.");
            return;
        }

        
        VentanaPrincipal v = new VentanaPrincipal(preguntas);
        v.setVisible(true);
    }
}
