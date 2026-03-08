package aplicacionsimple;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class AlumnoDesarrolloSoftware extends Alumno {
    private List<Materia> materias;
    private Map<String, Double> calificaciones = new LinkedHashMap<>();

    public AlumnoDesarrolloSoftware(String nombre, String apellidos, Date fechaNacimiento, String programa, List<Materia> materias) {
        super(nombre, apellidos, fechaNacimiento, programa);
        this.materias = materias;
        // Inicializar calificaciones con valor -1 (no evaluado)
        for (Materia m : materias) calificaciones.put(m.getNombre(), -1.0);
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public Map<String, Double> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificacion(String materia, double cal) {
        if (calificaciones.containsKey(materia)) calificaciones.put(materia, cal);
    }
}
