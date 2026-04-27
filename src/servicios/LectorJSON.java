package servicios;

import modelo.Pregunta;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * Clase LectorJSON
 * Extrae las preguntas del archivo JSON y las convierte en objetos tipo
 * pregunta
 */
public class LectorJSON {

    private String rutaArchivo;
/**Constructor que guarda la ruta*/
    public LectorJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
/**Lee las preguntas y nos devuelve la lista de estas*/
    public List<Pregunta> leerPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {//Cargamos el archivo desde los recursos y no del disco
            InputStream is = getClass().getResourceAsStream(rutaArchivo);
            //Si no lo encuentra nos devuelve la lista vacia
            if (is == null) {
                System.err.println("No se encontró el archivo JSON: " + rutaArchivo);
                return preguntas;
            }
            //Convertimos el JSON en un array de objetos
            InputStreamReader reader = new InputStreamReader(is);
            JSONArray array = (JSONArray) parser.parse(reader);
      //Recorremos las preguntas donde cada elemento del JSON sera un JSONObject
            for (Object obj : array) {
                JSONObject jsonPregunta = (JSONObject) obj;
             //Se leen los campos comunes a ambos tipo de pregunta
                String tipo = (String) jsonPregunta.get("tipo");
                String enunciado = (String) jsonPregunta.get("enunciado");
                String imagen = (String) jsonPregunta.get("imagen");
                String tema = (String) jsonPregunta.get("tema");
              //Si la pregunta es de tipo múltiple
                if ("MULTIPLE".equals(tipo)) {
                    JSONArray opcionesJSON = (JSONArray) jsonPregunta.get("opciones");
                    String[] opciones = new String[opcionesJSON.size()];
//Convertimos las opciones de tipo JSONArray a String[] para usarlas con la clase Pregunta
                    for (int i = 0; i < opcionesJSON.size(); i++) {
                        opciones[i] = (String) opcionesJSON.get(i);
                    }
                    //Guardamos la respuesta correcta
                    String respuestaCorrecta = (String) jsonPregunta.get("respuestaCorrecta");
                    //Creamos un objeto Pregunta y los añadimos a la lista
                    preguntas.add(new Pregunta(enunciado, opciones, respuestaCorrecta, imagen, tema));
//Si es de texto guardamos la correcta y creamos la pregunta con su constructor correspondiente
                } else if ("TEXTO".equals(tipo)) {
                    String respuestaCorrecta = (String) jsonPregunta.get("respuestaCorrecta");
                    preguntas.add(new Pregunta(enunciado, respuestaCorrecta, imagen, tema));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return preguntas;
    }
}
