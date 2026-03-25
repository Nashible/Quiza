package servicios;

import modelo.Pregunta;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        try {
            InputStream is = getClass().getResourceAsStream(rutaArchivo);

            if (is == null) {
                System.err.println("No se encontró el archivo JSON: " + rutaArchivo);
                return preguntas;
            }

            InputStreamReader reader = new InputStreamReader(is);
            JSONArray array = (JSONArray) parser.parse(reader);

            for (Object obj : array) {
                JSONObject jsonPregunta = (JSONObject) obj;

                String tipo = (String) jsonPregunta.get("tipo");
                String enunciado = (String) jsonPregunta.get("enunciado");
                String imagen = (String) jsonPregunta.get("imagen");
                String tema = (String) jsonPregunta.get("tema");

                if ("MULTIPLE".equals(tipo)) {
                    JSONArray opcionesJSON = (JSONArray) jsonPregunta.get("opciones");
                    String[] opciones = new String[opcionesJSON.size()];

                    for (int i = 0; i < opcionesJSON.size(); i++) {
                        opciones[i] = (String) opcionesJSON.get(i);
                    }

                    String respuestaCorrecta = (String) jsonPregunta.get("respuestaCorrecta");
                    preguntas.add(new Pregunta(enunciado, opciones, respuestaCorrecta, imagen, tema));

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
