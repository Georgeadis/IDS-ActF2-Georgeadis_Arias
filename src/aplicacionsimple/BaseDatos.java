package aplicacionsimple;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class BaseDatos {

    public static List<Alumno> alumnos = new ArrayList<>();

    static {
        // ===== LICENCIATURAS =====
        alumnos.add(new Alumno("Ana", "López", new Date(), "Administración con acentuación en Empresas"));
        alumnos.add(new Alumno("Carlos", "Pérez", new Date(), "Administración con acentuación en Empresas"));
        alumnos.add(new Alumno("Lucía", "Martínez", new Date(), "Administración con acentuación en Empresas"));
        alumnos.add(new Alumno("Jorge", "Ramírez", new Date(), "Administración con acentuación en Empresas"));
        alumnos.add(new Alumno("Martha", "González", new Date(), "Administración con acentuación en Empresas"));

        alumnos.add(new Alumno("Luis", "Hernández", new Date(), "Ingeniería en Desarrollo de Software"));
        alumnos.add(new Alumno("Paola", "Torres", new Date(), "Ingeniería en Desarrollo de Software"));
        alumnos.add(new Alumno("Daniel", "Cruz", new Date(), "Ingeniería en Desarrollo de Software"));
        alumnos.add(new Alumno("Andrea", "Flores", new Date(), "Ingeniería en Desarrollo de Software"));
        alumnos.add(new Alumno("Ricardo", "Vega", new Date(), "Ingeniería en Desarrollo de Software"));

        alumnos.add(new Alumno("Sofía", "Ríos", new Date(), "Logística, Aduanas y Comercio"));
        alumnos.add(new Alumno("Hugo", "Navarro", new Date(), "Logística, Aduanas y Comercio"));
        alumnos.add(new Alumno("Claudia", "Morales", new Date(), "Logística, Aduanas y Comercio"));
        alumnos.add(new Alumno("Fernando", "Ortega", new Date(), "Logística, Aduanas y Comercio"));
        alumnos.add(new Alumno("Patricia", "Salinas", new Date(), "Logística, Aduanas y Comercio"));

        alumnos.add(new Alumno("Iván", "Castillo", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Monserrat", "Aguilar", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Oscar", "Medina", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Verónica", "Campos", new Date(), "Licenciatura en Derecho"));
        alumnos.add(new Alumno("Raúl", "Silva", new Date(), "Licenciatura en Derecho"));

        // ===== MAESTRÍAS =====
        alumnos.add(new Alumno("Alberto", "Mendoza", new Date(), "Educación con acentuación en Tecnología Educativa"));
        alumnos.add(new Alumno("Norma", "Delgado", new Date(), "Educación con acentuación en Tecnología Educativa"));
        alumnos.add(new Alumno("Gabriel", "Fuentes", new Date(), "Educación con acentuación en Tecnología Educativa"));
        alumnos.add(new Alumno("Silvia", "Peña", new Date(), "Educación con acentuación en Tecnología Educativa"));

        // Fin de la inicialización
    }

    public static void agregarAlumno(Alumno a) {
        alumnos.add(a);
    }

}
