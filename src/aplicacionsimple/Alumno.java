package aplicacionsimple; 
// Paquete que agrupa las clases de la aplicacion simple

import java.util.Date; 
// Importa la clase Date para manejar la fecha de nacimiento

public class Alumno {
    // Clase Alumno que representa a un estudiante

    private String nombre;
    // Atributo que almacena el nombre del alumno

    private String apellidos;
    // Atributo que almacena los apellidos del alumno

    private Date fechaNacimiento;
    // Atributo que almacena la fecha de nacimiento del alumno

    private String programa; 
    // Atributo que indica el programa academico (Licenciatura o Maestria)

    public Alumno(String nombre, String apellidos, Date fechaNacimiento, String programa) {
        // Constructor de la clase Alumno que inicializa todos los atributos

        this.nombre = nombre;
        // Asigna el nombre recibido al atributo nombre

        this.apellidos = apellidos;
        // Asigna los apellidos recibidos al atributo apellidos

        this.fechaNacimiento = fechaNacimiento;
        // Asigna la fecha de nacimiento al atributo correspondiente

        this.programa = programa;
        // Asigna el programa academico al alumno
    }

    public String getPrograma() {
        // Metodo que permite obtener el programa academico del alumno

        return programa;
        // Devuelve el programa del alumno
    }

    public Date getFechaNacimiento() {
        // Devuelve la fecha de nacimiento del alumno
        return fechaNacimiento;
    }

    @Override
    public String toString() {
        // Metodo que convierte el objeto Alumno a una cadena de texto

        return nombre + " " + apellidos + " - " + programa;
        // Retorna una representacion textual del alumno
    }

    // Metodo de prueba
    public static void main(String[] args) {
        // Metodo principal para probar la clase Alumno

        Alumno a = new Alumno("Luis", "Garcia", new Date(), "Licenciatura");
        // Instanciacion de un objeto Alumno con datos de ejemplo

        System.out.println(a);
        // Muestra en consola la informacion del alumno
    }
}
