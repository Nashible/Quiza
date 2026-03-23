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
                String tema = (String) jsonPregunta.get("tema");

                if (tipo.equals("MULTIPLE")) {
                    JSONArray opcionesJSON = (JSONArray) jsonPregunta.get("opciones");
                    String[] opciones = new String[opcionesJSON.size()];
                    for (int i = 0; i < opcionesJSON.size(); i++) {
                        opciones[i] = (String) opcionesJSON.get(i);
                    }
                    String respuestaCorrecta = (String) jsonPregunta.get("respuestaCorrecta");
                    preguntas.add(new Pregunta(enunciado, opciones, respuestaCorrecta, imagen, tema));

                } else if (tipo.equals("TEXTO")) {
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
