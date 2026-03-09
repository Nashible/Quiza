package main;

import java.util.List;
import modelo.Pregunta;
import servicios.LectorJSON;
import vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

       //System.setProperty("sun.java2d.uiScale.enabled", "false");
        
        LectorJSON lector = new LectorJSON("src/recursos/preguntas.json");
        List<Pregunta> preguntas = lector.leerPreguntas();

        if (preguntas.isEmpty()) {
            System.out.println("No se han cargado preguntas. Revisa el JSON y la ruta.");
            return;
        }

        
        VentanaPrincipal v = new VentanaPrincipal(preguntas);
        v.setVisible(true);
    }
}
