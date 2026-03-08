package servicios;

import modelo.Pregunta;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LectorJSON {

    private String rutaArchivo;

    public LectorJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public List<Pregunta> leerPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(rutaArchivo)) {
            JSONArray array = (JSONArray) parser.parse(reader);

            for (Object obj : array) {
                JSONObject jsonPregunta = (JSONObject) obj;

                String tipo = (String) jsonPregunta.get("tipo");
                String enunciado = (String) jsonPregunta.get("enunciado");
                String imagen = (String) jsonPregunta.get("imagen");

                if (tipo.equals("MULTIPLE")) {
                    JSONArray opcionesJSON = (JSONArray) jsonPregunta.get("opciones");
                    List<String> opciones = new ArrayList<>();
                    for (Object o : opcionesJSON) {
                        opciones.add((String) o);
                    }
                    int correcta = ((Long) jsonPregunta.get("correcta")).intValue();
                    preguntas.add(new Pregunta(enunciado, opciones, correcta, imagen));
                } else if (tipo.equals("TEXTO")) {
                    String respuesta = (String) jsonPregunta.get("respuesta");
                    preguntas.add(new Pregunta(enunciado, respuesta, imagen));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return preguntas;
    }
}