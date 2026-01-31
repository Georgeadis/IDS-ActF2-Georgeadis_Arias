package aplicacionsimple;

import java.util.Date;

public class Alumno {

    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String programa; // Licenciatura o Maestría

    public Alumno(String nombre, String apellidos, Date fechaNacimiento, String programa) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.programa = programa;
    }

    public String getPrograma() {
        return programa;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " - " + programa;
    }

    // Método de prueba
    public static void main(String[] args) {
        Alumno a = new Alumno("Luis", "García", new Date(), "Licenciatura");
        System.out.println(a);
    }
}
