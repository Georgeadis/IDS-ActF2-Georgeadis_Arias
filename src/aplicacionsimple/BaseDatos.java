package aplicacionsimple;
// Paquete que agrupa las clases de la aplicacion

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Random;
import java.util.Arrays;
// Importacion de clases necesarias para listas dinamicas y fechas

public class BaseDatos {
    // Clase BaseDatos que simula una base de datos en memoria

    public static List<Alumno> alumnos = new ArrayList<>();
    // Lista estatica que almacena todos los alumnos registrados

    static {
        // Bloque estatico que se ejecuta al cargar la clase
        // Se utiliza para precargar alumnos en la "base de datos"

        // ===== LICENCIATURAS =====
        alumnos.add(new Alumno("Ana", "Lopez", new Date(), "Licenciatura en Administracion con acentuacion en Empresas"));
        alumnos.add(new Alumno("Carlos", "Perez", new Date(), "Licenciatura en Administracion con acentuacion en Empresas"));
        alumnos.add(new Alumno("Lucia", "Martinez", new Date(), "Licenciatura en Administracion con acentuacion en Empresas"));
        alumnos.add(new Alumno("Jorge", "Ramirez", new Date(), "Licenciatura en Administracion con acentuacion en Empresas"));
        alumnos.add(new Alumno("Martha", "Gonzalez", new Date(), "Licenciatura en Administracion con acentuacion en Empresas"));
        // Alumnos inscritos en la Licenciatura en Administracion

        alumnos.add(new Alumno("Luis", "Hernandez", new Date(), "Licenciatura en Ingenieria en Desarrollo de Software"));
        alumnos.add(new Alumno("Paola", "Torres", new Date(), "Licenciatura en Ingenieria en Desarrollo de Software"));
        alumnos.add(new Alumno("Daniel", "Cruz", new Date(), "Licenciatura en Ingenieria en Desarrollo de Software"));
        alumnos.add(new Alumno("Andrea", "Flores", new Date(), "Licenciatura en Ingenieria en Desarrollo de Software"));
        alumnos.add(new Alumno("Ricardo", "Vega", new Date(), "Licenciatura en Ingenieria en Desarrollo de Software"));
        // Alumnos inscritos en Ingenieria en Desarrollo de Software

        alumnos.add(new Alumno("Sofia", "Rios", new Date(), "Licenciatura en Logistica, Aduanas y Comercio"));
        alumnos.add(new Alumno("Hugo", "Navarro", new Date(), "Licenciatura en Logistica, Aduanas y Comercio"));
        alumnos.add(new Alumno("Claudia", "Morales", new Date(), "Licenciatura en Logistica, Aduanas y Comercio"));
        alumnos.add(new Alumno("Fernando", "Ortega", new Date(), "Licenciatura en Logistica, Aduanas y Comercio"));
        alumnos.add(new Alumno("Patricia", "Salinas", new Date(), "Licenciatura en Logistica, Aduanas y Comercio"));
        // Alumnos inscritos en Logistica, Aduanas y Comercio

        alumnos.add(new Alumno("Ivan", "Castillo", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Monserrat", "Aguilar", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Oscar", "Medina", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Veronica", "Campos", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Raul", "Silva", new Date(), "Licenciatura en Derecho"));
        // Alumnos inscritos en la Licenciatura en Derecho

        // ===== MAESTRiAS =====
        alumnos.add(new Alumno("Alberto", "Mendoza", new Date(), "Maestria en Educacion con acentuacion en Tecnologia Educativa"));
        alumnos.add(new Alumno("Norma", "Delgado", new Date(), "Maestria en Educacion con acentuacion en Tecnologia Educativa"));
        alumnos.add(new Alumno("Gabriel", "Fuentes", new Date(), "Maestria en Educacion con acentuacion en Tecnologia Educativa"));
        alumnos.add(new Alumno("Silvia", "Peña", new Date(), "Maestria en Educacion con acentuacion en Tecnologia Educativa"));
        // Alumnos inscritos en la Maestria en Educacion con acentuacion en Tecnologia Educativa

        // Fin de la inicializacion de la base de datos
    }

    // Tras la carga inicial, convertir alumnos de Desarrollo de Software a la subclase y asignar calificaciones ficticias
    static {
        List<Alumno> converted = new ArrayList<>();
        for (Alumno a : alumnos) {
            if (a.getPrograma().contains("Ingenieria en Desarrollo de Software")) {
                List<Materia> materias = Arrays.asList(
                        new Programacion(), new EstructurasDatos(), new BasesDeDatosMateria(), new IngenieriaSoftwareMateria(), new Redes()
                );
                AlumnoDesarrolloSoftware ads = new AlumnoDesarrolloSoftware(a.getNombre(), a.getApellidos(), a.getFechaNacimiento(), a.getPrograma(), materias);
                // semilla deterministica por nombre para reproducibilidad
                Random r = new Random((a.getNombre()+a.getApellidos()).hashCode());
                for (Materia m : materias) {
                    double cal = 6.0 + r.nextDouble() * 4.0; // rango 6.0 - 10.0
                    cal = Math.round(cal * 10.0) / 10.0; // una cifra decimal
                    ads.setCalificacion(m.getNombre(), cal);
                }
                converted.add(ads);
            } else {
                converted.add(a);
            }
        }
        alumnos.clear();
        alumnos.addAll(converted);
    }

    public static void agregarAlumno(Alumno a) {
        // Metodo que permite agregar un nuevo alumno a la lista

        alumnos.add(a);
        // Inserta el alumno recibido en la "base de datos"
    }
}
