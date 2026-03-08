package aplicacionsimple;

public class Materia {
    private String nombre;
    private int creditos;

    public Materia(String nombre, int creditos) {
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    @Override
    public String toString() {
        return nombre + " (" + creditos + " credits)";
    }
}
