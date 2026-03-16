# Quizá - Quiz Interactivo Multitemático

##  Descripción
"Quizá" es una aplicación de escritorio desarrollada en **Java Swing** para el proyecto intermodular de DAM. Permite a los usuarios poner a prueba sus conocimientos en diferentes áreas (Historia, Ciencia, Literatura y Geografía) mediante un sistema de cuestionarios con temporizador y ranking online.

##  Características Principales
- **Carga Dinámica:** Las preguntas se cargan desde un archivo `JSON`, permitiendo añadir contenido sin tocar el código.
- **Temporizador:** Control de tiempo real por pregunta mediante `javax.swing.Timer`.
- **Persistencia Híbrida:** - Guardado de puntuaciones en **Base de Datos en la nube PostgreSQL** (vía Neon).
  - Respaldo de datos mediante **Serialización de objetos** Java.
- **Interfaz Intuitiva:** Sistema de paneles intercambiables en una ventana única.

##  Estructura del Proyecto
El código está organizado siguiendo una arquitectura limpia:
- `main`: Punto de entrada de la aplicación.
- `modelo`: Clases de datos (`Pregunta`, `Jugador`).
- `vista`: Componentes de la interfaz gráfica (Paneles y Ventana).
- `servicios`: Lógica de conexión a BD, lectura de JSON y gestión del juego.
- `recursos`: Archivos externos como `preguntas.json` e imágenes.

##  Requisitos e Instalación
1. Tener instalado **Java JDK 11** o superior.
2. Conexión a internet para los ranking en línea.


##  Autor
- **Antonio Donoso Rodríguez** - *Alumno de DAM - IES Augustóbriga*
