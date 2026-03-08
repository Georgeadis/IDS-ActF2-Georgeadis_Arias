package aplicacionsimple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaBusqueda extends JDialog {
    public VentanaBusqueda(Frame owner) {
        super(owner, "Buscar alumnos", true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8,8));

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Alumno a : BaseDatos.alumnos) model.addElement(a.toString());
        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JButton btnVerCalificaciones = new JButton("Ver calificaciones");
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        btnVerCalificaciones.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx < 0) { JOptionPane.showMessageDialog(this, "Selecciona un alumno"); return; }
            Alumno a = BaseDatos.alumnos.get(idx);
            if (a instanceof AlumnoDesarrolloSoftware) {
                new VentanaCalificaciones((Frame) getOwner(), (AlumnoDesarrolloSoftware) a).setVisible(true);
            } else if (a.getPrograma().contains("Ingenieria en Desarrollo de Software")) {
                int opt = JOptionPane.showConfirmDialog(this, "Convertir este alumno a Desarrollo de Software para ver/editar calificaciones?", "Convertir alumno", JOptionPane.YES_NO_OPTION);
                if (opt != JOptionPane.YES_OPTION) return;
                java.util.List<Materia> materias = java.util.Arrays.asList(
                        new Programacion(), new EstructurasDatos(), new BasesDeDatosMateria(), new IngenieriaSoftwareMateria(), new Redes()
                );
                AlumnoDesarrolloSoftware ads = new AlumnoDesarrolloSoftware(a.getNombre(), a.getApellidos(), a.getFechaNacimiento(), a.getPrograma(), materias);
                BaseDatos.alumnos.set(idx, ads);
                new VentanaCalificaciones((Frame) getOwner(), ads).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Este alumno no pertenece a Ingeniería en Desarrollo de Software");
            }
        });

        // doble clic en la lista abre las calificaciones
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) btnVerCalificaciones.doClick();
            }
        });

        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(btnVerCalificaciones);
        p.add(btnCerrar);
        add(p, BorderLayout.SOUTH);
    }
}
